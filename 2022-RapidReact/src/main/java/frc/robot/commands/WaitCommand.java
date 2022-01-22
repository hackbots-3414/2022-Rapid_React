// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitCommand extends CommandBase {
  private static final Logger LOG = LoggerFactory.getLogger(WaitCommand.class);
  public long prevMilliseconds;
  public long waitTime;
  
  static {
    SmartDashboard.putNumber("Auton Delay", 1000);
  }
  /** Creates a new WaitCommand. */
  public WaitCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    prevMilliseconds = System.currentTimeMillis();
    waitTime = (long) SmartDashboard.getNumber("Auton Delay", 0);
    
    LOG.info("wait command starting prevMilliseconds = {}, waitTime = {}", prevMilliseconds, waitTime);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    long currentMillis = System.currentTimeMillis();

    long newMilliseconds = currentMillis - prevMilliseconds;

    if (newMilliseconds >= waitTime) {
      LOG.info("wait command finished");
      return true;

    } else {
      return false;
    }

  }
}
