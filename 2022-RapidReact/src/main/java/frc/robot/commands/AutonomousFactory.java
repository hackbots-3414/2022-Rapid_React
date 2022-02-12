// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TrajectoryFactory;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

/** Add your docs here. */
public class AutonomousFactory {
    private static final AutonomousFactory me = new AutonomousFactory();
    private static final Logger LOG = LoggerFactory.getLogger(AutonomousFactory.class);
    public final Drivetrain m_drivetrain = new Drivetrain();

    private AutonomousFactory() {

    }

    private RamseteCommand createRamseteCommand(Trajectory trajectory) {
        m_drivetrain.resetHeading();

        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter, DriveConstants.kaVoltSecondsSquaredPerMeter), DriveConstants.kDriveKinematics, DriveConstants.maxDriveVoltage);

        TrajectoryConfig config = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared).setKinematics(DriveConstants.kDriveKinematics).addConstraint(autoVoltageConstraint);

        TrajectoryFactory trajectoryFactory = TrajectoryFactory.getInstance();

        m_drivetrain.resetOdometry(trajectory.getInitialPose());

        RamseteCommand ramseteCommand = new RamseteCommand(trajectory, m_drivetrain::getPose, new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta), new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter, DriveConstants.kaVoltSecondsSquaredPerMeter), DriveConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds, new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel, DriveConstants.kDDriveVel), new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel, DriveConstants.kDDriveVel), m_drivetrain::tankDriveVolts, m_drivetrain);

        m_drivetrain.resetOdometry(trajectory.getInitialPose());

        return ramseteCommand;

    }

    public Command createShootBackupIntake() {
        SequentialCommandGroup scGroup = new SequentialCommandGroup();
        // TODO Shoot & Wait Command
        // TODO Add intake and shoot
        scGroup.addCommands(createRamseteCommand(TrajectoryFactory.getTaxi())); // .andThen(() -> m_drivetrain.tankDriveVolts(0, 0))

        return scGroup;
    }  

    public static AutonomousFactory getInstance() {
        return me;
    }
}
