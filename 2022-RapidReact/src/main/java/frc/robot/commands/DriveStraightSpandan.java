// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class DriveStraightSpandan extends CommandBase {
  private double speed;
  private double distance;
  private final double requestedDistance; 
  private final Drivetrain m_drivetrain;
 
  /** Creates a new DriveStraightSpandan. */
  public DriveStraightSpandan(double distance, Drivetrain subsystem, double speed) {
    this.distance=distance;
    this.speed=0.6;
    this.requestedDistance=distance;
    m_drivetrain=subsystem;
    addRequirements(m_drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.distance=requestedDistance;
    m_drivetrain.arcadeDrive(0, 0);
    m_drivetrain.resetEncoders();
    this.speed= Math.copySign(this.speed, this.distance);
    this.distance=Math.abs(this.distance/Constants.RobotConstants.kInchesPerTick);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(speed,0 );

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_drivetrain.arcadeDrive(speed*-1, 0);
    //Timer.delay(0.01);
    m_drivetrain.arcadeDrive(0, 0);
    m_drivetrain.resetEncoders();
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(m_drivetrain.getAverageEncoderPosition())<=this.distance){
      return false;
    } else{return true;}
    
  }
}
