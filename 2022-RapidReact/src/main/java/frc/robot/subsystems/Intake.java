// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import javax.xml.namespace.QName;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.fasterxml.jackson.databind.KeyDeserializer.None;
import com.revrobotics.ColorMatch;

import frc.robot.subsystems.LEDFeedback;


// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Intake extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Intake.class);

    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
//red .45
//blue .24

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    Solenoid solenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.Intake.solenoidChannel);
    WPI_TalonFX intakeMotor = new WPI_TalonFX(Constants.Intake.intakeMotor);


    private static final Logger LOG = LoggerFactory.getLogger(Intake.class);

    public Intake() {} 

    public void goDown() {
        solenoid.set(true);
    }
    

    /**
    *
    */
    public Intake() {
        // m_colorMatcher.addColorMatch(kBlueTarget);
        // m_colorMatcher.addColorMatch(kGreenTarget);
        // m_colorMatcher.addColorMatch(kRedTarget);
        // m_colorMatcher.addColorMatch(kYellowTarget);  
        //BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    public void goUp() {
        solenoid.set(false);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }


    public void stop() {
        setSpeed(0.0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        


    
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

 