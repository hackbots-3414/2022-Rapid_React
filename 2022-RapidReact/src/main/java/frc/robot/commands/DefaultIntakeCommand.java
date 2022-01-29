// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.sql.Time;

import javax.swing.text.AbstractDocument.LeafElement;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDFeedback;

public class DefaultIntakeCommand extends CommandBase {
  /** Creates a new DefaultIntakeCommand. */

  private LEDFeedback ledFeedback;
  private Intake intake;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  public DefaultIntakeCommand(LEDFeedback ledFeedback, Intake intake) {
    addRequirements(ledFeedback);
    addRequirements(intake);

    this.intake = intake;
    this.ledFeedback = ledFeedback;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        Color detectedColor = m_colorSensor.getColor();
        double IR = m_colorSensor.getIR();
        int proximity = m_colorSensor.getProximity();
    
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("IR", IR);
        SmartDashboard.putNumber("Proximity", proximity);

        //detects color
        if (detectedColor.red >= .33){
          ledFeedback.setColor(Color.kRed);
        } else if (detectedColor.blue >= .25){
          ledFeedback.setColor(Color.kBlue);
        } else {
          ledFeedback.setColor(Color.kBlack);
        }
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
