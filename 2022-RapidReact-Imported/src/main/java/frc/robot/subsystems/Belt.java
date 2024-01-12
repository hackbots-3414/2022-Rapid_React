package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Belt extends SubsystemBase {

  private boolean stopBelt = false;

  public int thisManyBalls;
  DigitalInput irBottom = new DigitalInput(0);
  DigitalInput irTop = new DigitalInput(1);
  TalonFX topMotor = new TalonFX(Constants.BeltConstants.topMotor);
  TalonFX middleMotor = new TalonFX(Constants.BeltConstants.middleMotor);
  TalonFX bottomMotor = new TalonFX(Constants.BeltConstants.bottomMotor);
  TalonFX intakeMotor = new TalonFX(Constants.BeltConstants.intakeMotor);
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

    public void startBeltMotors(double lower, double top) {
        bottomMotor.set(lower);
        middleMotor.set(lower);
        topMotor.set(top);
    }

    public Boolean isStopBelts() {
        return stopBelt;
    }

    public void setStopBelt(Boolean stopBelts) {
        stopBelt = stopBelts;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("IR top", getIRTop());
        SmartDashboard.putBoolean("IR bottom", getIRBottom());
    }


}