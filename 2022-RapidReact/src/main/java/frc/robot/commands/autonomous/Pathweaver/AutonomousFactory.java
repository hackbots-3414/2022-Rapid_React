// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.Pathweaver;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.PathweaverConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.TrajectoryFactory;
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
        m_drivetrain.resetHeading();
        RamseteCommand ramseteCommand = new RamseteCommandProxy(trajectory, m_drivetrain::getPose, new RamseteController(PathweaverConstants.kRamseteB, PathweaverConstants.kRamseteZeta), new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter, PathweaverConstants.kaVoltSecondsSquaredPerMeter), RobotConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds, new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel, PathweaverConstants.kdDriveVel), new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel, PathweaverConstants.kdDriveVel), m_drivetrain::tankDriveVolts, m_drivetrain);
        return ramseteCommand;
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
        ShootCommand shooterCommand = new ShootCommand(m_belt, m_shooter, 1, 400);
        return shooterCommand;
    }

    // CREATING GROUPS

    public SequentialCommandGroup create3BallAuton() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart1", true)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart2", false)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart3", true)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart4", false)))));
        group.addCommands(createIntakeCommand(false));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create3BallAutonWierd() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("2BallAutonT1Part1", true)), createRamseteCommand(TrajectoryFactory.getPath("2BallAutonT1Part2", false)))));
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(false)));
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonWierdPart3", true)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonWierdPart4", false)))));
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(false)));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create2BallAutonT2() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("2BallAutonT2Part1", true)), createRamseteCommand(TrajectoryFactory.getPath("2BallAutonT2Part2", false)))));
        group.addCommands(createIntakeCommand(false));
        group.addCommands(createShooterCommand());
        return group;
    }
    
    public SequentialCommandGroup create2BallAutonT1() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("2BallAutonT1Part1", true)), createRamseteCommand(TrajectoryFactory.getPath("2BallAutonT1Part2", false)))));
        group.addCommands(createIntakeCommand(false));
        group.addCommands(createShooterCommand());
        return group;
    }

    public SequentialCommandGroup create5BallAuton() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart1", true)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart2", false)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart3", true)), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart4", false)))));
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(false)));
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("5BallAutonPart4", true)), createRamseteCommand(TrajectoryFactory.getPath("5BallAutonPart5", false)))));
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(false)));
        group.addCommands(createShooterCommand());
        return group;
    }

    public class RamseteCommandProxy extends RamseteCommand {
        private Trajectory trajectory;

        public RamseteCommandProxy(Trajectory trajectory, Supplier<Pose2d> pose, RamseteController controller, SimpleMotorFeedforward feedforward, DifferentialDriveKinematics kinematics, Supplier<DifferentialDriveWheelSpeeds> wheelSpeeds, PIDController leftController, PIDController rightController, BiConsumer<Double, Double> outputVolts, Subsystem... requirements) {
            super(trajectory, pose, controller, feedforward, kinematics, wheelSpeeds, leftController, rightController, outputVolts, requirements);
            this.trajectory = trajectory;
        }

        @Override
        public void initialize() {
            m_drivetrain.resetOdometry(trajectory.getInitialPose());
            super.initialize();
        }

        @Override
        public void execute() {
            super.execute();
        }

        @Override
        public void end(boolean interrupted) {
            m_drivetrain.tankDriveVolts(0, 0);
            super.end(interrupted);
        }
    }
}
