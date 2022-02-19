// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

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
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.RobotContainer;
import frc.robot.TrajectoryFactory;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.Shooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LEDFeedback;

/** Add your docs here. */
public class AutonomousFactory {
    private static final AutonomousFactory me = new AutonomousFactory();
    private static final Logger LOG = LoggerFactory.getLogger(AutonomousFactory.class);
    public final Drivetrain m_drivetrain = new Drivetrain();
    public Trajectory trajectory;
    private static final LEDFeedback m_ledfeedback = RobotContainer.getInstance().m_lEDFeedback;

    private AutonomousFactory() {

    }

    private RamseteCommand createRamseteCommand(String name, Trajectory trajectory) {
        // m_drivetrain.resetHeading();
        // RamseteCommand ramseteCommand = new RamseteCommandProxy(name, trajectory, m_drivetrain::getPose, new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta), new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter, DriveConstants.kaVoltSecondsSquaredPerMeter), DriveConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds, new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel, DriveConstants.kDDriveVel), new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel, DriveConstants.kDDriveVel), m_drivetrain::tankDriveVolts, m_drivetrain);
        // return ramseteCommand;
        m_drivetrain.resetHeading();
        var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter, DriveConstants.kaVoltSecondsSquaredPerMeter), DriveConstants.kDriveKinematics, DriveConstants.maxDriveVoltage);
        TrajectoryConfig config = new TrajectoryConfig(AutoConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared).setKinematics(DriveConstants.kDriveKinematics).addConstraint(autoVoltageConstraint);
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)), List.of(), new Pose2d(45, 0, new Rotation2d(0)), config);
        RamseteCommand ramseteCommand = new RamseteCommandProxy(name, exampleTrajectory, m_drivetrain::getPose, new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta), new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter, DriveConstants.kaVoltSecondsSquaredPerMeter), DriveConstants.kDriveKinematics, m_drivetrain::getWheelSpeeds, new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel, DriveConstants.kDDriveVel), new PIDController(DriveConstants.kPDriveVel, DriveConstants.kIDriveVel, DriveConstants.kDDriveVel), m_drivetrain::tankDriveVolts, m_drivetrain);
        return ramseteCommand;
    }

    private DefaultLEDCommand createLEDCommand(Color colour) {
        DefaultLEDCommand ledCommand = new DefaultLEDCommand(m_ledfeedback, colour);
        return ledCommand;
    }

    public Command createShootBackupIntake() {
        SequentialCommandGroup scGroup = new SequentialCommandGroup();
        // // scGroup.addCommands(shoot);
        // scGroup.addCommands(new ParallelCommandGroup(createRamseteCommand("BlueBottom2Rev", TrajectoryFactory.getBlueBottom2Rev())));
        // scGroup.addCommands(new WaitCommand());
        // // scGroup.addCommands(intake); // ADD TO PARALLEL 
        // scGroup.addCommands(createRamseteCommand("BlueBottom2For", TrajectoryFactory.getBlueBottom2For()));
        // // scGroup.addCommands(shoot);
        scGroup.addCommands(createRamseteCommand("exampleTrajectory", TrajectoryFactory.getTestPathSmooth()));
        m_drivetrain.tankDriveVolts(0, 0);
        
        return scGroup;
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
            super(trajectory, pose, controller, feedforward, kinematics, wheelSpeeds, leftController, rightController, outputVolts, requirements);
            this.myName = myName;
            this.trajectory = trajectory;
        }

        @Override
        public void initialize() {
            m_drivetrain.resetOdometry(trajectory.getInitialPose());
            LOG.trace("Distance Travelled at Start: {}, {}", m_drivetrain.getLeftEncoderDistance(), m_drivetrain.getRightEncoderDistance());
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
            LOG.trace("Distance Travelled at End: {}, {}", m_drivetrain.getLeftEncoderDistance(), m_drivetrain.getRightEncoderDistance());
        }
    }
}
