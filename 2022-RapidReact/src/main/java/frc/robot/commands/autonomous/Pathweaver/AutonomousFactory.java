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
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.TrajectoryFactory;
import frc.robot.Constants.PathweaverConstants;
import frc.robot.Constants.RobotConstants;
import frc.robot.subsystems.Drivetrain;

/** Add your docs here. */
public class AutonomousFactory {
    private static final AutonomousFactory me = new AutonomousFactory();

    private static Drivetrain m_drivetrain;

    private AutonomousFactory() {
    }

    public static AutonomousFactory getInstance(Drivetrain drivetrain) {
        m_drivetrain = drivetrain;
        return me;
    }

    private RamseteCommand createRamseteCommand(Trajectory trajectory) {
        m_drivetrain.resetHeading();
        RamseteCommand ramseteCommand = new RamseteCommandProxy(trajectory, m_drivetrain::getPose, new RamseteController(PathweaverConstants.kRamseteB, PathweaverConstants.kRamseteZeta), new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter, PathweaverConstants.kaVoltSecondsSquaredPerMeter), RobotConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds, new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel, PathweaverConstants.kdDriveVel), new PIDController(PathweaverConstants.kpDriveVel, PathweaverConstants.kiDriveVel, PathweaverConstants.kdDriveVel), m_drivetrain::tankDriveVolts, m_drivetrain);
        return ramseteCommand;
    }

    public SequentialCommandGroup createTestCommand() {
        SequentialCommandGroup group = new SequentialCommandGroup();
        group.addCommands(createRamseteCommand(TrajectoryFactory.getPath("TestPath")));
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
            super.end(interrupted);
        }
    }
}
