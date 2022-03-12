// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pathweaver;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.PathweaverConstants;
import frc.robot.TrajectoryFactory;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

/** Add your docs here. */
public class AutonomousFactory {
    private static final AutonomousFactory me = new AutonomousFactory();
    private static final Logger LOG = LoggerFactory.getLogger(AutonomousFactory.class);
    public Trajectory trajectory;
    private Drivetrain m_drivetrain;
    private Belt m_belt;
    private Shooter m_shooter;
    private LEDFeedback m_ledFeedback;

    private String team;
    private String tarmac;
    private String position;

    private AutonomousFactory() {

    }

    private RamseteCommand createRamseteCommand(Trajectory trajectory) {

        m_drivetrain.resetHeading();
        RamseteCommand ramseteCommand = new RamseteCommandProxy(trajectory,
                m_drivetrain::getPose, new RamseteController(AutoConstants.kRamseteB,
                        AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(PathweaverConstants.ksVolts,
                        PathweaverConstants.kvVoltSecondsPerMeter,
                        PathweaverConstants.kaVoltSecondsSquaredPerMeter),
                PathweaverConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds,
                new PIDController(PathweaverConstants.kPDriveVel, PathweaverConstants.kIDriveVel,
                        PathweaverConstants.kDDriveVel),
                new PIDController(PathweaverConstants.kPDriveVel,
                        PathweaverConstants.kIDriveVel, PathweaverConstants.kDDriveVel),
                m_drivetrain::tankDriveVolts, m_drivetrain);
        return ramseteCommand;
    }

    public SequentialCommandGroup create3BallAuton() {
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter, PathweaverConstants.kaVoltSecondsSquaredPerMeter), PathweaverConstants.kDriveKinematics, 10);
        TrajectoryConfig config = new TrajectoryConfig(1, 2.5).setKinematics(PathweaverConstants.kDriveKinematics).addConstraint(autoVoltageConstraint);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)), List.of(/*new Translation2d(1, 1), new Translation2d(2, -1)*/), new Pose2d(3, 0, new Rotation2d(0)), config);
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(createRamseteCommand(trajectory).andThen(() -> m_drivetrain.tankDriveVolts(0, 0)));
        // // group.addCommands(shoot);
        // group.addCommands(new ParallelCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonBall2")/* , intake */)));
        // group.addCommands(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonBall3For")));
        // group.addCommands(new ParallelCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonBall3Rev"))/* , intake */));
        // group.addCommands(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonShoot2")));
        // // group.addCommands(shoot);
        // group.addCommands(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonBackup")));
        
        return group;
    }

    public SequentialCommandGroup createShootBackupIntake(String team, String tarmac, String position) {

        // Path Chooser
        this.team = team;
        this.tarmac = tarmac;
        this.position = position;

        ArrayList<String> pathNames = getPathNames();

        // String forward = pathNames.get(0);
        // String reverse = pathNames.get(1);

        String forward = "BlueBottom2For";
        String reverse = "BlueBottom2Rev";

        SequentialCommandGroup scGroup = new SequentialCommandGroup();
        // scGroup.addCommands(createShootCommand(m_belt, m_shooter, false, 100));
        scGroup.addCommands(createRamseteCommand(TrajectoryFactory.getPath(reverse)/* , createIntakeCommand(m_belt) */));
        scGroup.addCommands(createRamseteCommand(TrajectoryFactory.getPath(forward)));
        // scGroup.addCommands(createShootCommand(m_belt, m_shooter, false, 100));
        m_drivetrain.tankDriveVolts(0, 0);

        return scGroup;
    }

    private ArrayList<String> getPathNames() {
        this.team.toLowerCase();
        this.tarmac.toLowerCase();
        this.position.toLowerCase();

        ArrayList<String> names = new ArrayList<>();

        if (this.team == "red") {
            if (this.tarmac == "top") {
                if (this.position == "1") {
                    names.add("RedTop1For");
                    names.add("RedTop1Rev");
                } else if (this.position == "2") {
                    names.add("RedTop2For");
                    names.add("RedTop2Rev");
                } else if (this.position == "3") {
                    names.add("RedTop3For");
                    names.add("RedTop3Rev");
                } else {
                    return null;
                }
            } else if (this.tarmac == "bottom") {
                if (this.position == "1") {
                    names.add("RedBottom1For");
                    names.add("RedBottom1Rev");
                } else if (this.position == "2") {
                    names.add("RedBottom2For");
                    names.add("RedBottom2Rev");
                } else if (this.position == "3") {
                    names.add("RedBottom3For");
                    names.add("RedBottom3Rev");
                } else {
                    return null;
                }
            }
        } else if (this.team == "blue") {
            if (this.tarmac == "top") {
                if (this.position == "1") {
                    names.add("BlueTop1For");
                    names.add("BlueTop1Rev");
                } else if (this.position == "2") {
                    names.add("BlueTop2For");
                    names.add("BlueTop2Rev");
                } else if (this.position == "3") {
                    names.add("BlueTop3For");
                    names.add("BlueTop3Rev");
                } else {
                    return null;
                }
            } else if (this.tarmac == "bottom") {
                if (this.position == "1") {
                    names.add("BlueBottom1For");
                    names.add("BlueBottom1Rev");
                } else if (this.position == "2") {
                    names.add("BlueBottom2For");
                    names.add("BlueBottom2Rev");
                } else if (this.position == "3") {
                    names.add("BlueBottom3For");
                    names.add("BlueBottom3Rev");
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }

        return names;
    }

    public static AutonomousFactory getInstance() {
        return me;
    }

    public static AutonomousFactory getInstance(Drivetrain drivetrain, Belt belt, LEDFeedback ledFeedback, Shooter shooter) {
        me.m_drivetrain = drivetrain;
        me.m_belt = belt;
        me.m_ledFeedback = ledFeedback;
        me.m_shooter = shooter;
        return me;
    }

    public class RamseteCommandProxy extends RamseteCommand {
        private Trajectory trajectory;

        public RamseteCommandProxy(
                Trajectory trajectory,
                Supplier<Pose2d> pose,
                RamseteController controller,
                SimpleMotorFeedforward feedforward,
                DifferentialDriveKinematics kinematics,
                Supplier<DifferentialDriveWheelSpeeds> wheelSpeeds,
                PIDController leftController,
                PIDController rightController,
                BiConsumer<Double, Double> outputVolts,
                Subsystem... requirements) {
            super(trajectory, pose, controller, feedforward, kinematics, wheelSpeeds, leftController, rightController,
                    outputVolts, requirements);
            this.trajectory = trajectory;
        }

        @Override
        public void initialize() {
            m_drivetrain.resetOdometry(trajectory.getInitialPose());
            LOG.trace("Distance Travelled at Start: {}, {}", m_drivetrain.getLeftEncoderDistance(),
                    m_drivetrain.getRightEncoderDistance());
            super.initialize();
        }

        @Override
        public void execute() {
            super.execute();
        }

        @Override
        public void end(boolean interrupted) {
            super.end(interrupted);
            LOG.trace("Distance Travelled at End: {}, {}", m_drivetrain.getLeftEncoderDistance(),
                    m_drivetrain.getRightEncoderDistance());
        }
    }
}
