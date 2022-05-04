// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.Pathweaver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.spline.Spline;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.PathweaverConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.StopBeltCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

/** Add your docs here. */
public class AutonomousFactory {
    private static final AutonomousFactory me = new AutonomousFactory();

    private static Drivetrain m_drivetrain;
    private static Belt m_belt;
    private static Shooter m_shooter;

    private static final double PATHWEAVER_Y_OFFSET = 8.2296;

    private AutonomousFactory() {
    }

    public static AutonomousFactory getInstance(Drivetrain drivetrain, Belt belt, Shooter shooter) {
        m_drivetrain = drivetrain;
        m_belt = belt;
        m_shooter = shooter;
        return me;
    }

    // CREATING COMMANDS

    private RamseteCommand createRamseteCommand(Trajectory trajectory) {
        RamseteCommand ramseteCommand = new RamseteCommandProxy(trajectory, m_drivetrain::getPose,
                new RamseteController(PathweaverConstants.kRamseteB, PathweaverConstants.kRamseteZeta),
                new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter,
                        PathweaverConstants.kaVoltSecondsSquaredPerMeter),
                RobotConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds,
                new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel,
                        PathweaverConstants.kdDriveVel),
                new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel,
                        PathweaverConstants.kdDriveVel),
                m_drivetrain::tankDriveVolts, m_drivetrain);
        return ramseteCommand;
    }

    private ArrayList<RamseteCommand> createRamseteCommandArray(ArrayList<Trajectory> trajectory) {
        ArrayList<RamseteCommand> ramseteCommands = new ArrayList<>();
        for (int i = 0; i < trajectory.size(); i++) {
            RamseteCommand ramseteCommand = new RamseteCommandProxy(trajectory.get(i), m_drivetrain::getPose,
                    new RamseteController(PathweaverConstants.kRamseteB, PathweaverConstants.kRamseteZeta),
                    new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter,
                            PathweaverConstants.kaVoltSecondsSquaredPerMeter),
                    RobotConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds,
                    new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel,
                            PathweaverConstants.kdDriveVel),
                    new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel,
                            PathweaverConstants.kdDriveVel),
                    m_drivetrain::tankDriveVolts, m_drivetrain);
            ramseteCommands.add(ramseteCommand);
        }
        return ramseteCommands;
    }

    private Command createIntakeCommand(Boolean offOrOn) {
        if (offOrOn) {
            BeltCommand beltCommand = new BeltCommand(m_belt);
            return beltCommand;
        } else {
            StopBeltCommand stopBeltCommand = new StopBeltCommand(m_belt);
            return stopBeltCommand;
        }
    }

    private Command createShooterCommand() {
        ShootCommand shooterCommand = new ShootCommand(m_belt, m_shooter, 1, 600); // 500
        return shooterCommand;
    }

    // CREATING GROUPS

    public SequentialCommandGroup createPathPlannerTest() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        ArrayList<RamseteCommand> commands = createRamseteCommandArray(
                getTrajectoriesFromPathPlanner("Test 3 Ball", true));
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(
                commands.get(0),
                commands.get(1),
                createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(
                commands.get(2),
                commands.get(3),
                createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create3BallAuton() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true),
                new SequentialCommandGroup(
                        createRamseteCommand(getTrajectoriesFromPathweaver("3BallAutonPart1", true)),
                        createRamseteCommand(getTrajectoriesFromPathweaver("3BallAutonPart2", false)),
                        createRamseteCommand(getTrajectoriesFromPathweaver("3BallAutonPart3", true)),
                        createRamseteCommand(getTrajectoriesFromPathweaver("3BallAutonPart4", false)),
                        createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create3BallAutonWierd() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(
                createRamseteCommand(getTrajectoriesFromPathweaver("2BallAutonT1Part1", true)),
                createRamseteCommand(getTrajectoriesFromPathweaver("2BallAutonT1Part2", false)),
                createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(
                createRamseteCommand(getTrajectoriesFromPathweaver("3BallAutonWierdPart3", true)),
                createRamseteCommand(getTrajectoriesFromPathweaver("3BallAutonWierdPart4", false)),
                createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create2BallAutonT1() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(
                createRamseteCommand(getTrajectoriesFromPathweaver("2BallAutonT1Part1", true)),
                createRamseteCommand(getTrajectoriesFromPathweaver("2BallAutonT1Part2", false)),
                createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create4BallAuton() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(
                createRamseteCommand(getTrajectoriesFromPathweaver("2BallAutonT1Part1", true)),
                createRamseteCommand(getTrajectoriesFromPathweaver("2BallAutonT1Part2", false)),
                createIntakeCommand(false))));
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true),
                new SequentialCommandGroup(
                        createRamseteCommand(getTrajectoriesFromPathweaver("5BallAutonPart5", true)),
                        createRamseteCommand(getTrajectoriesFromPathweaver("5BallAutonPart6", false)),
                        createIntakeCommand(false))));
        group.addCommands(createShooterCommand());

        return group;
    }

    // CREATING TRAJECTORIES

    public static Trajectory getTrajectoriesFromPathweaver(String pathName, boolean isReversed) {
        try {
            String relativePath = "waypoints/" + pathName + ".path";
            Path path = Filesystem.getDeployDirectory().toPath().resolve(relativePath);

            TrajectoryGenerator.ControlVectorList controlVectors = new TrajectoryGenerator.ControlVectorList();

            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                boolean skippedFirst = false;
                String line = reader.readLine();
                while (line != null) {
                    if (!skippedFirst || !line.contains(",")) {
                        skippedFirst = true;
                        line = reader.readLine();
                        continue;
                    }
                    String[] split = line.split(",");
                    controlVectors.add(new Spline.ControlVector(
                            new double[] { Double.parseDouble(split[0]), Double.parseDouble(split[2]), 0 },
                            new double[] { Double.parseDouble(split[1]) + PATHWEAVER_Y_OFFSET,
                                    Double.parseDouble(split[3]), 0 }));

                    line = reader.readLine();
                }
            }

            TrajectoryConfig config = new TrajectoryConfig(PathweaverConstants.kMaxSpeed,
                    PathweaverConstants.kMaxAcceleration);
            DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                    new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter,
                            PathweaverConstants.kaVoltSecondsSquaredPerMeter),
                    RobotConstants.kDriveKinematics, 11.5);
            config.addConstraint(new CentripetalAccelerationConstraint(PathweaverConstants.kMaxSpinAcceleration));
            config.setEndVelocity(PathweaverConstants.kMaxEndSpeed);
            config.setKinematics(RobotConstants.kDriveKinematics);
            config.setReversed(isReversed);
            config.addConstraint(autoVoltageConstraint);
            try {
                Trajectory trajectory = TrajectoryGenerator.generateTrajectory(controlVectors, config);
                return trajectory;
            } catch (Exception e) {
                System.out.println("Couldn't load trajectory of: " + pathName);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Cannot load Pathweaver Path of " + pathName);
            return null;
        }
    }

    public static ArrayList<Trajectory> getTrajectoriesFromPathPlanner(String name, boolean startReversed) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(new File(Filesystem.getDeployDirectory(), "pathplanner/" + name + ".path")))) {
            // GENERATE STRING
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                fileContentBuilder.append(line);
            }

            String fileContent = fileContentBuilder.toString();

            JSONObject json = (JSONObject) new JSONParser().parse(fileContent);
            JSONArray jsonWaypoints = (JSONArray) json.get("waypoints");

            ArrayList<AutonomousFactory.Waypoint> waypoints = new ArrayList<>();

            // GENERATE WAYPOINTS
            for (Object waypoint : jsonWaypoints) {
                JSONObject jsonWaypoint = (JSONObject) waypoint;

                JSONObject jsonAnchor = (JSONObject) jsonWaypoint.get("anchorPoint");
                JSONObject jsonPrevControl = (JSONObject) jsonWaypoint.get("prevControl");
                JSONObject jsonNextControl = (JSONObject) jsonWaypoint.get("nextControl");

                Translation2d anchorPoint = new Translation2d((double) jsonAnchor.get("x"),
                        (double) jsonAnchor.get("y"));
                Translation2d prevControl = null;
                Translation2d nextControl = null;

                if (jsonPrevControl != null) {
                    prevControl = new Translation2d(((double) jsonPrevControl.get("x") - (double) jsonAnchor.get("x")),
                            ((double) jsonPrevControl.get("y") - (double) jsonAnchor.get("y")));
                }

                if (jsonNextControl != null) {
                    nextControl = new Translation2d(((double) jsonNextControl.get("x") - (double) jsonAnchor.get("x")),
                            ((double) jsonNextControl.get("y") - (double) jsonAnchor.get("y")));
                }

                Rotation2d holonomicAngle = Rotation2d.fromDegrees((double) jsonWaypoint.get("holonomicAngle"));
                boolean isReversal = (boolean) jsonWaypoint.get("isReversal");

                waypoints.add(new AutonomousFactory.Waypoint(anchorPoint, prevControl, nextControl,
                        holonomicAngle, isReversal));
            }

            ArrayList<ArrayList<AutonomousFactory.Waypoint>> splitPaths = new ArrayList<>();
            ArrayList<AutonomousFactory.Waypoint> currentPath = new ArrayList<>();

            for (int i = 0; i < waypoints.size(); i++) {
                if (i != waypoints.size() - 1) {
                    AutonomousFactory.Waypoint w1 = waypoints.get(i);
                    AutonomousFactory.Waypoint w2 = waypoints.get(i + 1);
                    currentPath.add(w1);
                    currentPath.add(w2);
                    splitPaths.add(currentPath);
                    currentPath = new ArrayList<>();
                }
            }

            // GENERATE CONTROL VECTORS

            ArrayList<TrajectoryGenerator.ControlVectorList> controlVectors = new ArrayList<>();

            for (int i = 0; i < splitPaths.size(); i++) {
                TrajectoryGenerator.ControlVectorList controlVectorList = new TrajectoryGenerator.ControlVectorList();
                if (i == 0 && startReversed) {
                    double x = splitPaths.get(i).get(0).nextControl.getX();
                    double y = splitPaths.get(i).get(0).nextControl.getY();
                    x *= -1;
                    y *= -1;
                    controlVectorList.add(
                            new Spline.ControlVector(new double[] { splitPaths.get(i).get(0).anchorPoint.getX(), x },
                                    new double[] { splitPaths.get(i).get(0).anchorPoint.getY(), y }));
                    if (splitPaths.get(i).get(1).prevControl.getX() != splitPaths.get(i).get(1).nextControl.getX()
                            && splitPaths.get(i).get(1).prevControl.getY() != splitPaths.get(i).get(1).nextControl
                                    .getY()) {
                        double prevX = splitPaths.get(i).get(1).prevControl.getX();
                        double prevY = splitPaths.get(i).get(i).nextControl.getY();
                        prevX *= -1;
                        prevY *= -1;
                        controlVectorList.add(new Spline.ControlVector(
                                new double[] { splitPaths.get(i).get(1).anchorPoint.getX(), prevX },
                                new double[] { splitPaths.get(i).get(1).anchorPoint.getY(), prevY }));
                    }
                    controlVectorList.add(new Spline.ControlVector(
                            new double[] { splitPaths.get(i).get(1).anchorPoint.getX(),
                                    splitPaths.get(i).get(1).nextControl.getX() },
                            new double[] { splitPaths.get(i).get(1).anchorPoint.getY(),
                                    splitPaths.get(i).get(1).nextControl.getY() }));
                    controlVectors.add(controlVectorList);
                    continue;
                }
                if (i == splitPaths.size() - 1) {
                    double x = splitPaths.get(i).get(1).nextControl.getX();
                    double y = splitPaths.get(i).get(1).nextControl.getY();
                    x *= -1;
                    y *= -1;
                    controlVectorList.add(new Spline.ControlVector(
                            new double[] { splitPaths.get(i).get(0).anchorPoint.getX(),
                                    splitPaths.get(i).get(0).nextControl.getX() },
                            new double[] { splitPaths.get(i).get(0).anchorPoint.getY(),
                                    splitPaths.get(i).get(0).nextControl.getY() }));
                    controlVectorList.add(
                            new Spline.ControlVector(new double[] { splitPaths.get(i).get(1).anchorPoint.getX(), x },
                                    new double[] { splitPaths.get(i).get(1).anchorPoint.getY(), y }));
                    controlVectors.add(controlVectorList);
                    continue;
                }
                controlVectorList.add(new Spline.ControlVector(
                        new double[] { splitPaths.get(i).get(0).anchorPoint.getX(),
                                splitPaths.get(i).get(0).nextControl.getX() },
                        new double[] { splitPaths.get(i).get(0).anchorPoint.getY(),
                                splitPaths.get(i).get(0).nextControl.getY() }));
                if (splitPaths.get(i).get(1).prevControl.getX() != splitPaths.get(i).get(1).nextControl.getX()
                        && splitPaths.get(i).get(1).prevControl.getY() != splitPaths.get(i).get(1).nextControl.getY()) {
                    double prevX = splitPaths.get(i).get(1).prevControl.getX();
                    double prevY = splitPaths.get(i).get(i).nextControl.getY();
                    prevX *= -1;
                    prevY *= -1;
                    controlVectorList.add(new Spline.ControlVector(
                            new double[] { splitPaths.get(i).get(1).anchorPoint.getX(), prevX },
                            new double[] { splitPaths.get(i).get(1).anchorPoint.getY(), prevY }));
                }
                controlVectorList.add(new Spline.ControlVector(
                        new double[] { splitPaths.get(i).get(1).anchorPoint.getX(),
                                splitPaths.get(i).get(1).nextControl.getX() },
                        new double[] { splitPaths.get(i).get(1).anchorPoint.getY(),
                                splitPaths.get(i).get(1).nextControl.getY() }));
                controlVectors.add(controlVectorList);
            }

            // GENERATE TRAJECTORIES
            ArrayList<Trajectory> trajectories = new ArrayList<>();
            boolean reversed = startReversed;
            TrajectoryConfig config = new TrajectoryConfig(PathweaverConstants.kMaxSpeed,
                    PathweaverConstants.kMaxAcceleration);
            DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                    new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter,
                            PathweaverConstants.kaVoltSecondsSquaredPerMeter),
                    RobotConstants.kDriveKinematics, 11.5);
            config.addConstraint(autoVoltageConstraint);
            config.addConstraint(new CentripetalAccelerationConstraint(PathweaverConstants.kMaxSpinAcceleration));
            config.setEndVelocity(PathweaverConstants.kMaxEndSpeed);
            config.setKinematics(RobotConstants.kDriveKinematics);

            for (int i = 0; i < controlVectors.size(); i++) {
                config.setReversed(reversed);
                trajectories.add(TrajectoryGenerator.generateTrajectory(controlVectors.get(i), config));
                if (splitPaths.get(i).get(1).isReversal) {
                    reversed = !reversed;
                }
            }

            return trajectories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static class Waypoint {
        private final Translation2d anchorPoint;
        private final Translation2d prevControl;
        private final Translation2d nextControl;
        private final Rotation2d holonomicRotation;
        protected final boolean isReversal;

        protected Waypoint(Translation2d anchorPoint, Translation2d prevControl, Translation2d nextControl,
                Rotation2d holonomicRotation, boolean isReversal) {
            this.anchorPoint = anchorPoint;
            this.prevControl = prevControl;
            this.nextControl = nextControl;
            this.holonomicRotation = holonomicRotation;
            this.isReversal = isReversal;
        }
    }

    public class RamseteCommandProxy extends RamseteCommand {
        private Trajectory trajectory;

        public RamseteCommandProxy(Trajectory trajectory, Supplier<Pose2d> pose, RamseteController controller,
                SimpleMotorFeedforward feedforward, DifferentialDriveKinematics kinematics,
                Supplier<DifferentialDriveWheelSpeeds> wheelSpeeds, PIDController leftController,
                PIDController rightController, BiConsumer<Double, Double> outputVolts, Subsystem... requirements) {
            super(trajectory, pose, controller, feedforward, kinematics, wheelSpeeds, leftController, rightController,
                    outputVolts, requirements);
            this.trajectory = trajectory;
        }

        @Override
        public void initialize() {
            m_drivetrain.resetHeading();
            m_drivetrain.resetOdometry(trajectory.getInitialPose());
            super.initialize();
        }

        @Override
        public void end(boolean interrupted) {
            m_drivetrain.tankDriveVolts(0, 0);
            super.end(interrupted);
        }
    }
}
