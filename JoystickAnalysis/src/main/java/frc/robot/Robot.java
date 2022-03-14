// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.strykeforce.thirdcoast.util.ExpoScale;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Constants.CompControllerConstants;
import frc.robot.Constants.DriveConstants;
 

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final PWMSparkMax m_leftDrive = new PWMSparkMax(0);
  private final PWMSparkMax m_rightDrive = new PWMSparkMax(1);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive, m_rightDrive);
  
  public final static Joystick joystick = new Joystick(0);
  private final Timer m_timer = new Timer();
  // create FileWriter object with file as parameter
 public static FileWriter outputfile;
  
  // create CSVWriter object filewriter object as parameter
  public static PrintWriter writer;
  private int Counter;
  private int Limit = 3000;

  private static final double RIGHTX_DEADBAND = CompControllerConstants.right_x_offset;
  private static final double LEFTY_DEADBAND = CompControllerConstants.left_y_offset;

  private static final double RIGHTX_XPOSCALE = CompControllerConstants.right_x_max;
  private static final double LEFTY_XPOSCALE = CompControllerConstants.left_y_max;

  private static ExpoScale leftHaloScale;
  private static ExpoScale rightHaloScale;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightDrive.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(joystick.getY(), joystick.getX());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
    leftHaloScale = new ExpoScale(LEFTY_DEADBAND, LEFTY_XPOSCALE);
    rightHaloScale = new ExpoScale(RIGHTX_DEADBAND, RIGHTX_XPOSCALE);
    File file = new File("C:/Users/Hackbots/code/2022-Rapid_React/JoystickAnalysis/src/main/deploy/JoystickLog.csv");
    try {
        // create FileWriter object with file as parameter
        outputfile = new FileWriter(file);
  
        // create CSVWriter object filewriter object as parameter
        writer = new PrintWriter(outputfile);
  
        // adding header to csv
        String header = "Counter,YLeftRaw,YLeft^2,YLeft^3,lefthalo, vlefthalo,vlefthalo^2,Deadband,Exoscale";
        writer.println(header);
  
        
    }
    catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    
    Counter++;

    final double x_leftOutput = joystick.getRawAxis(0);
    final double y_leftOutput = joystick.getRawAxis(1);
    final double x_rightOutput = joystick.getRawAxis(2);
    final double y_rightOutput = joystick.getRawAxis(3);

    //double leftHalo = leftHaloScale.apply(joystick.getLeftVerticalRaw());
    //double rightHalo = rightHaloScale.apply(joystick.getRightLateralRaw());
    double leftHalo = leftHaloScale.apply(y_leftOutput);
    double vleftHalo = leftHalo * DriveConstants.kMaxSpeed;
   // double vrightHalo = rightHalo * DriveConstants.kMaxSpeed;
   double cubeAndRawHigh = Math.abs(leftHalo) < - 0.4 ? leftHalo : y_leftOutput / 0.6;
        
    // 
    // specified by filepath
    //try {
        
      if (Counter <= Limit){

         // add data to csv
        // String data = x_leftOutput + ", " + x_rightOutput + ", " + y_leftOutput + ", " + y_rightOutput;
        String data = Counter + "," + 
                      y_leftOutput + "," + 
                      Math.pow(y_leftOutput,2) + "," + 
                      Math.pow(y_leftOutput,3) + "," + 
                      leftHalo + "," +
                      vleftHalo + "," +
                      Math.pow(vleftHalo,2) + "," +
                      LEFTY_DEADBAND + "," +
                      LEFTY_XPOSCALE;
         writer.println(data);

      }

      if(Counter == Limit+1){

        // closing writer connection
        writer.close();

      }
       
      
  
        
  //  }
  //  catch (IOException e) {
     // catch {
        // TODO Auto-generated catch block
        //e.printStackTrace();
   // }
}



  }

