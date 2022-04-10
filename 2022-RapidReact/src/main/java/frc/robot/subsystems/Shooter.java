package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private WPI_TalonFX rightMotor;
    private WPI_TalonFX leftMotor;

    private TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    private SlotConfiguration PIDConfig = new SlotConfiguration();

    public Shooter() {

        rightMotor = new WPI_TalonFX(Constants.ShooterConstants.shooterMotor1);
        leftMotor = new WPI_TalonFX(Constants.ShooterConstants.shooterMotor2);

        leftMotor.setInverted(TalonFXInvertType.Clockwise);
        rightMotor.setInverted(TalonFXInvertType.CounterClockwise);

        PIDConfig.integralZone = Constants.ShooterConstants.integralZone;
        PIDConfig.kD = Constants.ShooterConstants.kD;
        PIDConfig.kF = Constants.ShooterConstants.kF;
        PIDConfig.kI = Constants.ShooterConstants.kI;
        PIDConfig.kP = Constants.ShooterConstants.kP;
        motorConfig.slot0 = PIDConfig;

        leftMotor.configAllSettings(motorConfig);
        rightMotor.follow(leftMotor);
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }

    public void shootHigh() {
        leftMotor.set(ControlMode.Velocity, Constants.ShooterConstants.highShootVelocity);
    }

    public void shootLow() {
        leftMotor.set(ControlMode.Velocity, Constants.ShooterConstants.lowShootVelocity);
    }

    public void shootFar() {
        leftMotor.set(ControlMode.Velocity, Constants.ShooterConstants.shootFarVelocity);
    } 

    public boolean highAtSpeed() {
        return (Math.abs(((leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2) - Constants.ShooterConstants.highShootVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
    }

    public boolean lowAtSpeed() {
        return (Math.abs(((leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2) - Constants.ShooterConstants.lowShootVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
    }

    public boolean farAtSpeed() {
        return (Math.abs(((leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2) - Constants.ShooterConstants.shootFarVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
    }

    public double getAverageVelocity() {
        return (leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2;
    }

    public void stop() {
        leftMotor.set(0.0);
    }
}