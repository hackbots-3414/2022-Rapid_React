package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
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

    public void startMotorTop(double speed) {
        topMotor.set(speed);
    }

    public void stopMotorTop() {
        topMotor.set(0.0);
    }

    public void startMotorMiddle(double speed) {
        middleMotor.set(speed);
    }

    public void stopMotorMiddle() {
        middleMotor.set(0.0);
    }

    public void startMotorBottom(double speed) {
        bottomMotor.set(speed);
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

    public void startIntakeMotor(double speed) {
        intakeMotor.set(-speed);
    }

    public void stopIntakeMotor() {
        intakeMotor.set(0.0);
    }

    public void eject(double speed) {
        topMotor.set((-speed));
        middleMotor.set((-speed));
        bottomMotor.set((-speed));
    }

    public void startAllMotors(double lower, double top, double intake) {
        bottomMotor.set(lower);
        middleMotor.set(lower);
        topMotor.set(top);
        intakeMotor.set(-intake);
    }

    @Override
    public void periodic() {
    }

}