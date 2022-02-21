package frc.robot.subsystems;
import java.lang.invoke.ConstantBootstraps;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Constants.BeltConstants;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
public class Belt extends SubsystemBase {

  private static final Logger LOG = LoggerFactory.getLogger(Belt.class);

  DigitalInput irBottom = new DigitalInput(0);
  DigitalInput irTop = new DigitalInput(1);
  WPI_TalonFX topMotor = new WPI_TalonFX(Constants.BeltConstants.topMotor);
  WPI_TalonFX middleMotor = new WPI_TalonFX(Constants.BeltConstants.middleMotor);
  WPI_TalonFX bottomMotor = new WPI_TalonFX(Constants.BeltConstants.bottomMotor);
  WPI_TalonFX intakeMotor = new WPI_TalonFX(Constants.BeltConstants.intakeMotor);
  Solenoid solenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.BeltConstants.solenoidChannel);


    public boolean getIRBottom() {
        return !irBottom.get();
    }

    public boolean getIRTop() {
        return !irTop.get();
    }

    public void startMotorTop() {
        topMotor.set(Constants.BeltConstants.topMotorSpeed);
    }

    public void stopMotorTop() {
        topMotor.set(0.0);
    }

    public void startMotorMiddle() {
        middleMotor.set(Constants.BeltConstants.motorSpeed);
    }

    public void stopMotorMiddle() {
        middleMotor.set(0.0);
    }

    public void startMotorBottom() {
        bottomMotor.set(Constants.BeltConstants.motorSpeed);
    }

    public void stopMotorBottom() {
        bottomMotor.set(0.0);
    }

    public void stopAllMotors() {
        bottomMotor.set(0.0);
        middleMotor.set(0.0);
        topMotor.set(0.0);
        intakeMotor.set(0.0);
    }

    public void goUp() {
        solenoid.set(false);
    }

    public void goDown() {
        solenoid.set(true);
    }

    public void startIntakeMotor() {
        intakeMotor.set(-Constants.BeltConstants.intakeSpeed);
    }

    public void stopIntakeMotor() {
        intakeMotor.set(0.0);
    }

    public void eject() {
        topMotor.set(2*(-Constants.BeltConstants.motorSpeed));
        middleMotor.set(2*(-Constants.BeltConstants.motorSpeed));
        bottomMotor.set(2*(-Constants.BeltConstants.motorSpeed));
    }

    public void startAllMotors() {
        bottomMotor.set(Constants.BeltConstants.motorSpeed);
        middleMotor.set(Constants.BeltConstants.motorSpeed);
        topMotor.set(Constants.BeltConstants.topMotorSpeed);
        intakeMotor.set(-Constants.BeltConstants.motorSpeed);
    }

    @Override
    public void periodic() {
    }

    public boolean atSpeed() {
        return (Math.abs(((bottomMotor.getSelectedSensorVelocity() + middleMotor.getSelectedSensorVelocity() + topMotor.getSelectedSensorVelocity()) / 3) - Constants.BeltConstants.motorSpeed) <= 50);
    }
}