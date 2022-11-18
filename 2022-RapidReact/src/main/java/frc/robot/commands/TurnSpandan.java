// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnSpandan extends CommandBase {
  private double turnSpeed;
  private double startHeading;
  private double targetHeading;
  private double  currentHeading;
  private final Drivetrain m_drivetrain;
  private double m_degrees;
  /** Creates a new TurnSpandan. */
  public TurnSpandan(Drivetrain subsystem, double degrees,double turnSpeed) {
    this.turnSpeed=0.4;
    m_degrees=degrees;
    m_drivetrain=subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
