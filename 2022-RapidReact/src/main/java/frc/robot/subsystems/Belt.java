package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.BeltConstants;

public class Belt extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Belt.class);

    private DigitalInput irBottom = new DigitalInput(0);
    private DigitalInput irMiddle = new DigitalInput(1);
    private DigitalInput irTop = new DigitalInput(2);

    WPI_TalonFX topMotor = new WPI_TalonFX(BeltConstants.topMotor);
    WPI_TalonFX middleMotor = new WPI_TalonFX(BeltConstants.middleMotor);
    WPI_TalonFX bottomMotor = new WPI_TalonFX(BeltConstants.bottomMotor);

    public boolean getIRBottom() {
        return irBottom.get();
    }

    public boolean getIRMiddle() {
        return irMiddle.get();
    }

    public boolean getIRTop() {
        return irTop.get();
    }

    public void startMotorTop() {
        topMotor.set(Constants.BeltConstants.motorSpeed);
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
    }

    public void startAllMotors() {
        bottomMotor.set(Constants.BeltConstants.motorSpeed);
        middleMotor.set(Constants.BeltConstants.motorSpeed);
        topMotor.set(Constants.BeltConstants.motorSpeed);
    }

    @Override
    public void periodic() {
    }

    public boolean atSpeed() {
        return (Math.abs(((bottomMotor.getSelectedSensorVelocity() + middleMotor.getSelectedSensorVelocity() + topMotor.getSelectedSensorVelocity()) / 3) - Constants.BeltConstants.motorSpeed) <= 50);
    }
}