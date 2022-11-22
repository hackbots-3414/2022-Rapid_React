// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnSpandan extends CommandBase {

  private static final Logger LOG = LoggerFactory.getLogger(Turn.class);
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

public TurnSpandan(Drivetrain subsystem, double degrees, double turnSpeed) {
    this.turnSpeed = turnSpeed;

    m_degrees = degrees;

    m_drivetrain = subsystem;
  }

    public void turnLeft() {
        LOG.debug("In turnLeft");
        m_drivetrain.tankDrive(-turnSpeed, turnSpeed);
        //what does the LOG Debug thing say, does it just print does statement.
        // why is the leftmost parameter -, does the left motors go backwards and the right go forwards?//
    } 
    
    public void turnRight() {
        LOG.debug("In turnRight");
        m_drivetrain.tankDrive(turnSpeed, -turnSpeed);
      
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_drivetrain.resetHeading();
      //this resets heading and encoders? 0,0 point
      startHeading = m_drivetrain.getHeading();
      LOG.debug("StartHeading = {}", startHeading);
      //does the heading appear in the brackets?
      targetHeading = m_degrees;
      LOG.debug("TargetHeading = {}", targetHeading);
      
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    LOG.debug("Turn.execute() running");
    if (m_degrees > 0) {
      LOG.debug("Turning Right");
      turnRight();
    } else if (m_degrees < 0) {
      LOG.debug("Turning Left");
      turnLeft();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}
0
  //a Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
