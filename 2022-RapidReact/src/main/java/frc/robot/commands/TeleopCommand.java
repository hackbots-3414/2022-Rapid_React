// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeleopCommand extends CommandBase {

  private static final Logger LOG = LoggerFactory.getLogger("Constants.java");

  private Drivetrain driveTrain; 
  

  /** Creates a new TeleopCommand. */
  public TeleopCommand(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
 
    this.driveTrain = driveTrain;
 
  }

  public TeleopCommand() {
}

// Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.arcadeDrive(OI.getThrottle(), OI.getSteering());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}