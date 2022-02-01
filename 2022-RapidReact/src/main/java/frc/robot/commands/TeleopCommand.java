// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Util;
import frc.robot.subsystems.Drivetrain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeleopCommand extends CommandBase {

  private static final Logger LOG = LoggerFactory.getLogger(TeleopCommand.class);

  private Drivetrain drivetrain; 
  

  /** Creates a new TeleopCommand. */
  public TeleopCommand(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
 
    this.drivetrain = drivetrain;
 
  }

  public TeleopCommand() {
}

// Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // LOG.debug(String.valueOf(OI.getButtonB()));

    // LOG.warn(String.valueOf(OI.getRKnobRaw()));

    if (OI.getButtonB() == 0) {
      drivetrain.arcadeDrive(OI.getLeftVertical(), OI.getRightLateral());
    }
    else if (OI.getButtonB() == 1) {
      drivetrain.arcadeDrive(OI.getLeftVertical(), OI.getLeftLateral());
    }
    else if (OI.getButtonB() == 2) {
      drivetrain.tankDrive(OI.getLeftVertical(), OI.getRightVertical());
    }
    // if (OI.getButtonB() == 0) {
    //   drivetrain.arcadeDrive(Util.reduceToOne(OI.getLeftVertical() * OI.getRKnob()), Util.reduceToOne(OI.getRightLateral() * OI.getRKnob()));
    // }
    // else if (OI.getButtonB() == 1) {
    //   drivetrain.arcadeDrive(Util.reduceToOne(OI.getLeftVertical() * OI.getRKnob()), Util.reduceToOne(OI.getLeftLateral() * OI.getRKnob()));
    // }
    // else if (OI.getButtonB() == 2) {
    //   drivetrain.tankDrive(Util.reduceToOne(OI.getLeftVertical() * OI.getRKnob()), Util.reduceToOne(OI.getRightVertical() * OI.getRKnob()));
    // }
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
