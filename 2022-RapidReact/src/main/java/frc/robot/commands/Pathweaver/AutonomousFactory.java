// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Pathweaver;

import java.lang.reflect.Array;
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
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.TrajectoryFactory;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

/** Add your docs here. */
public class AutonomousFactory {
    private static final AutonomousFactory me = new AutonomousFactory();
    private static final Logger LOG = LoggerFactory.getLogger(AutonomousFactory.class);
    public final static Drivetrain m_drivetrain = new Drivetrain();
    public final Belt m_belt = new Belt();
    public final Shooter m_shooter = new Shooter();
    public final LEDFeedback m_ledFeedback = new LEDFeedback();
    public Trajectory trajectory;

    private String team;
    private String tarmac;
    private String position;

    private AutonomousFactory() {

    }

    private RamseteCommand createRamseteCommand(String name, Trajectory trajectory) {
        m_drivetrain.resetHeading();
        RamseteCommand ramseteCommand = new RamseteCommandProxy(name, trajectory,
                m_drivetrain::getPose, new RamseteController(AutoConstants.kRamseteB,
                        AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(DriveConstants.ksVolts,
                        DriveConstants.kvVoltSecondsPerMeter,
                        DriveConstants.kaVoltSecondsSquaredPerMeter),
                DriveConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds,
                new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel,
                        DriveConstants.kDDriveVel),
                new PIDController(DriveConstants.kPDriveVel,
                        DriveConstants.kIDriveVel, DriveConstants.kDDriveVel),
                m_drivetrain::tankDriveVolts, m_drivetrain);
        return ramseteCommand;
    }

    private ShootCommand createShootCommand(Belt belt, Shooter shooter, boolean isHigh, int shooterTimer) {
        return new ShootCommand(belt, shooter, isHigh, shooterTimer);
    }

    private BeltCommand createIntakeCommand(Belt belt) {
        return new BeltCommand(belt);
    }

    public Command createShootBackupIntake(String team, String tarmac, String position) {
        // Path Chooser
        this.team = team;
        this.tarmac = tarmac;
        this.position = position;

        ArrayList<String> pathNames = getPathNames();

        String forward = pathNames.get(0);
        String reverse = pathNames.get(1);

        SequentialCommandGroup scGroup = new SequentialCommandGroup();
        // scGroup.addCommands(createShootCommand(m_belt, m_shooter, false, 100));
        scGroup.addCommands(new ParallelCommandGroup(createRamseteCommand(reverse,
                TrajectoryFactory.getPath(reverse))/* , createIntakeCommand(m_belt) */));
        scGroup.addCommands(createRamseteCommand(forward, TrajectoryFactory.getPath(forward)));
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

    public class RamseteCommandProxy extends RamseteCommand {
        private String myName;
        private Trajectory trajectory;

        public RamseteCommandProxy(
                String myName,
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
            this.myName = myName;
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
            LOG.trace("{}.execute()", myName);
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
