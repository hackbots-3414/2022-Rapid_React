// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class StopBeltCommand extends CommandBase {
  final Belt m_belt;
  /** Creates a new StopBeltCommand. */
  public StopBeltCommand(Belt belt) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_belt = belt;
    m_belt.setStopBelt(true);
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_belt.isStopBelts();
  }
}
