package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Shooter.class);

    private WPI_TalonFX rightMotor;
    private WPI_TalonFX leftMotor;

    private TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    private SlotConfiguration PIDConfig = new SlotConfiguration();

    public Shooter() {

        rightMotor = new WPI_TalonFX(Constants.Shooter.shooterMotor1);
        leftMotor = new WPI_TalonFX(Constants.Shooter.shooterMotor2);

        leftMotor.setInverted(TalonFXInvertType.Clockwise);
        rightMotor.setInverted(TalonFXInvertType.CounterClockwise);

        PIDConfig.integralZone = Constants.Shooter.integralZone;
        PIDConfig.kD = Constants.Shooter.kD;
        PIDConfig.kF = Constants.Shooter.kF;
        PIDConfig.kI = Constants.Shooter.kI;
        PIDConfig.kP = Constants.Shooter.kP;
        motorConfig.slot0 = PIDConfig;

        leftMotor.configAllSettings(motorConfig);
        rightMotor.follow(leftMotor);
        LOG.error("got here");
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }

    public void shootHigh() {
        leftMotor.set(ControlMode.Velocity, Constants.Shooter.highShootVelocity);
    }

    public void shootLow() {
        leftMotor.set(ControlMode.Velocity, Constants.Shooter.lowShootVelocity);
    }

    public boolean highAtSpeed() {
        return (Math.abs(((leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2) - Constants.Shooter.highShootVelocity) <= 50);
    }

    public boolean lowAtSpeed() {
        return (Math.abs(((leftMotor.getSelectedSensorVelocity() + rightMotor.getSelectedSensorVelocity()) / 2) - Constants.Shooter.lowShootVelocity) <= 50);
    }

    public void stop() {
        leftMotor.set(0.0);
    }
}