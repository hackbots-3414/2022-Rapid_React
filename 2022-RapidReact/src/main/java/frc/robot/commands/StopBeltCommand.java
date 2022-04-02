// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class StopBeltCommand extends CommandBase {
  final Belt m_belt;

  public StopBeltCommand(Belt belt) {
    m_belt = belt;
  }

  @Override
  public void initialize() {
    m_belt.setStopBelt(true);
  }
  
  @Override
  public boolean isFinished() {
    return m_belt.isStopBelts();
  }
}
