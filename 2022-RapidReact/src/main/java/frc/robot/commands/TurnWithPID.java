// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TurnWithPID extends PIDCommand {
  private Drivetrain m_drivetrain;
  /** Creates a new TurnWithPID. */
  public TurnWithPID(double turnDegrees, Drivetrain drivetrain) {
    super(
        // The controller that the command will use
        new PIDController(0.03, 0, 0),
        // This should return the measurement
        drivetrain::getHeading,
        // This should return the setpoint (can also be a constant)
        turnDegrees,
        // This uses the output
        output -> {
          drivetrain.arcadeDrive(0, output/2);
          // Use the output here
        });

        m_drivetrain = drivetrain;
        addRequirements(m_drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(2.0);
    getController().enableContinuousInput(-180, 180);
    SmartDashboard.putData("PID", getController());
  }
  @Override
  public void initialize() {
    m_drivetrain.resetHeading();
    super.initialize();
    SmartDashboard.putNumber("Original Heading", m_drivetrain.getHeading());
  }

  @Override
  public void end(boolean interrupted) {
    m_drivetrain.stopDriving();
    super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    SmartDashboard.putNumber("Current Heading", m_drivetrain.getHeading());
    SmartDashboard.putBoolean("PIDController", getController().atSetpoint());

    return super.getController().atSetpoint();
  }
}
