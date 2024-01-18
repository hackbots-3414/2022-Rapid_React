package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.controls.DifferentialFollower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private TalonFX rightMotor;
    private TalonFX leftMotor;

    //private TalonFXConfiguration motorConfig = new TalonFXConfiguration();

    public Shooter() {

        leftMotor = new TalonFX(Constants.ShooterConstants.shooterMotor2);
        rightMotor = new TalonFX(Constants.ShooterConstants.shooterMotor1);
        configTalon(leftMotor, InvertedValue.Clockwise_Positive);
        // configTalon(rightMotor, InvertedValue.CounterClockwise_Positive);
        rightMotor.setControl(new DifferentialFollower(leftMotor.getDeviceID(), true));
    }

    private void configTalon(TalonFX motor, InvertedValue invertValue) {
        var motorConfigs = new MotorOutputConfigs();
        motorConfigs.Inverted = invertValue;
        motor.getConfigurator().apply(motorConfigs);
        SlotConfigs pIDConfig = new SlotConfigs();
        pIDConfig.kD = Constants.ShooterConstants.kD;
        pIDConfig.kV = Constants.ShooterConstants.kF;
        pIDConfig.kI = Constants.ShooterConstants.kI;
        pIDConfig.kP = Constants.ShooterConstants.kP;
        leftMotor.getConfigurator().apply(pIDConfig);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Velocity", getAverageVelocity());
        SmartDashboard.putBoolean("Low at speed", lowAtSpeed());
        SmartDashboard.putBoolean("High at speed", highAtSpeed());
    }

    @Override
    public void simulationPeriodic() {
    }

    public void shootHigh() {
        final VelocityVoltage m_request = new VelocityVoltage(Constants.ShooterConstants.highShootVelocity);
        leftMotor.setControl(m_request);
        //leftMotor.set(ControlMode.Velocity, Constants.ShooterConstants.highShootVelocity);
    }

    public void shootLow() {
        final VelocityVoltage m_request = new VelocityVoltage(Constants.ShooterConstants.lowShootVelocity);
        leftMotor.setControl(m_request);
        //leftMotor.set(ControlMode.Velocity, Constants.ShooterConstants.lowShootVelocity);
    }
    
    public void shootFar() {
        final VelocityVoltage m_request = new VelocityVoltage(Constants.ShooterConstants.shootFarVelocity);
        leftMotor.setControl(m_request);
        //leftMotor.set(ControlMode.Velocity, Constants.ShooterConstants.shootFarVelocity);
    } 

    public boolean highAtSpeed() {
        return (Math.abs(((leftMotor.getRotorVelocity().getValueAsDouble() + rightMotor.getRotorVelocity().getValueAsDouble()) / 2) - Constants.ShooterConstants.highShootVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
        //return (Math.abs(((leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2) - Constants.ShooterConstants.highShootVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
    }

    public boolean lowAtSpeed() {
        return (Math.abs(((leftMotor.getRotorVelocity().getValueAsDouble() + rightMotor.getRotorVelocity().getValueAsDouble()) / 2) - Constants.ShooterConstants.lowShootVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
    }

    public boolean farAtSpeed() {
        return (Math.abs(((leftMotor.getRotorVelocity().getValueAsDouble() + rightMotor.getRotorVelocity().getValueAsDouble()) / 2) - Constants.ShooterConstants.shootFarVelocity) <= Constants.ShooterConstants.shootVelocityTolerance);
    }

    public double getAverageVelocity() {
        return (leftMotor.getRotorVelocity().getValueAsDouble() + rightMotor.getRotorVelocity().getValueAsDouble()) / 2;
    }

    public void stop() {
        leftMotor.set(0.0);
    }
}