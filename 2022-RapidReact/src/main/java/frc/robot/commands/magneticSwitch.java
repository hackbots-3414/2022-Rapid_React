
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;



public class magneticSwitch extends CommandBase {
  DigitalInput limitSwitch = new DigitalInput(ClimberConstants.climbMagneticLimitPort);

  private final Climber m_climber;

    public magneticSwitch(Climber subsystem) {
        m_climber = subsystem;
        addRequirements(m_climber);
    }
  
  

  public void initialize() {
    public void setMotorSpeed(double speed) {
      if (speed > 0) {
          if (limitSwitch.get()) {
              // We are going up and top limit is tripped so stop
          } else {
              // We are going up but top limit is not tripped so go at commanded speed
              m_climber.climberUp();
          }
         }
}
  }
}

  
