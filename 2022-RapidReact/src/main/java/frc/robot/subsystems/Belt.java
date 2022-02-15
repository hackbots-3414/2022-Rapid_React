package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Constants;
import frc.robot.Constants.BeltConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.Transport;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BeltConstants;

public class Belt extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Belt.class);

  private DigitalInput irBottom = new DigitalInput(0);
  private DigitalInput irMiddle = new DigitalInput(1);
  private DigitalInput irTop = new DigitalInput(2);
  private double beltSpeed = 100.0;

    WPI_TalonFX topMotor = new WPI_TalonFX(BeltConstants.topMotor);
    WPI_TalonFX middleMotor = new WPI_TalonFX(BeltConstants.middleMotor);
    WPI_TalonFX bottomMotor = new WPI_TalonFX(BeltConstants.bottomMotor);

  public boolean getIRBottom () {
    return irBottom.get();
  }

  public boolean getIRMiddle () {
    return irMiddle.get();
  }

  public boolean getIRTop () {
    return irTop.get();
  }

  public void startMotorTop () {
    topMotor.set(beltSpeed);
  }

  public void stopMotorTop () {
    topMotor.set(0.0);
  }

  public void startMotorMiddle () {
    middleMotor.set(beltSpeed);
  }

  public void stopMotorMiddle () {
    middleMotor.set(0.0);
  }
  
  public void startMotorBottom () {
    bottomMotor.set(beltSpeed);
  }

  public void stopMotorBottom () {
    bottomMotor.set(0.0);
  }

  public void stopAllMotors () {
    bottomMotor.set(0.0);
    middleMotor.set(0.0);
    topMotor.set(0.0);
  }

  public void startAllMotors () {
    bottomMotor.set(beltSpeed);
    middleMotor.set(beltSpeed);
    topMotor.set(beltSpeed);
  }

  public void startAll () {
    bottomMotor.set(Constants.BeltConstants.Motorspeed);
    middleMotor.set(Constants.BeltConstants.Motorspeed);
    topMotor.set(Constants.BeltConstants.Motorspeed);
  }

  @Override
  public void periodic() {
    
  }
}