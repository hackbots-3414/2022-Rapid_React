// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.pathweaver;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import javax.swing.text.html.ParagraphView;

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
import frc.robot.Constants.BeltConstants;
import frc.robot.Constants.PathweaverConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.StopBeltCommand;
import frc.robot.TrajectoryFactory;
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
        ShootCommand shooterCommand = new ShootCommand(m_belt, m_shooter, 1, 100);
        return shooterCommand;
    }

    public SequentialCommandGroup createTestCommand() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(createShooterCommand());
        group.addCommands(new ParallelCommandGroup(createIntakeCommand(true), new SequentialCommandGroup(createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart1")), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart2")), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart3")), createRamseteCommand(TrajectoryFactory.getPath("3BallAutonPart4")))));
        group.addCommands(createIntakeCommand(false));
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
