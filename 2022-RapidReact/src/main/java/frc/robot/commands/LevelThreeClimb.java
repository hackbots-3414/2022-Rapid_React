
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;


public class LevelThreeClimb extends CommandBase {
  DigitalInput limitSwitch = new DigitalInput(ClimberConstants.climbMagneticLimitPort);

  private final Climber m_climber;
  private long startThirdLevelClimb;
  private boolean reachedLimit;
  private double climberDelay = 3000;

    public LevelThreeClimb(Climber subsystem) {
        m_climber = subsystem;
        //addRequirements(m_climber);
    }
  
  

  public void initialize(){
    startThirdLevelClimb = System.currentTimeMillis();
    reachedLimit = false;
  }

  public void execute(){
  }

  public void end(boolean interrupted) {
    m_climber.pinsPush();
  }

  public boolean isFinished() {
    if (!limitSwitch.get() && !reachedLimit) {
      m_climber.pinsRetract();
      reachedLimit = true;
      startThirdLevelClimb = System.currentTimeMillis();
    }
    if (reachedLimit && (System.currentTimeMillis() - climberDelay > startThirdLevelClimb)) {
      return true;
    }
    return false;
  }

  
}

  

  
