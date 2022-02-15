// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class BeltCommand extends CommandBase {
  /** Creates a new BeltCommand. */

  final Belt m_belt;
  boolean isRunning;
  double output;


  public BeltCommand(Belt belt, double output) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(belt);
    m_belt = belt;
    this.output = output;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isRunning = true;
    m_belt.setBeltSpeed(output);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isRunning = false;
    m_belt.setBeltSpeed(0.0);
    m_belt.setconveyorSensorfront(false);
    m_belt.setconveyorSensorback(false);
    super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
