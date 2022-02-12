// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Constants.BeltConstants;
import frc.robot.Constants.DriveConstants;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DigitalInput;

public class Belt extends SubsystemBase {
  /** Creates a new Belt. */
  private static final Logger LOG = LoggerFactory.getLogger(Belt.class);

  private WPI_TalonFX topMotor;
  private WPI_TalonFX middleMotor;
  private WPI_TalonFX bottomMotor;

  public DigitalInput irsfront = new DigitalInput(1);
  public DigitalInput irsback = new DigitalInput(0);

  private boolean conveyorSensorFront = false;
  private boolean conveyorSensorBack = false;

  public Belt() {

    WPI_TalonFX topMotor = new WPI_TalonFX(BeltConstants.topMotor);
    middleMotor = new WPI_TalonFX(BeltConstants.middleMotor);
    bottomMotor = new WPI_TalonFX(BeltConstants.bottomMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setBeltSpeed(double speed) {
    topMotor.set(speed / 3);// divide by 4
    middleMotor.set(speed / 2);
    bottomMotor.set(-speed);
  }

  public int getConveyorState() {
    if (conveyorSensorFront == false && conveyorSensorBack == false) {
      return 0;
    } else if (conveyorSensorBack == true && conveyorSensorFront == false) {
      return 1;
    }
    return 2;
  }

  public void setconveyorSensorfront(boolean conveyorSensorFront) {
    this.conveyorSensorFront = conveyorSensorFront;
  }

  public void setconveyorSensorback(boolean conveyorSensorback) {
    this.conveyorSensorBack = conveyorSensorBack;
  }

}