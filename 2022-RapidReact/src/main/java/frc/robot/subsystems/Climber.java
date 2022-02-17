// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */

  Solenoid climber = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel);

  public Climber() {
    
    

  }

  public void climberUp(){
      climber.set(false);
  }

  public void climberDown(){
    climber.set(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
