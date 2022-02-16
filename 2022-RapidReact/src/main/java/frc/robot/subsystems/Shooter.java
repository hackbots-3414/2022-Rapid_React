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
        // 6500 works well for high shot on 2/5/2022
        // shooter degree at 4.5 degrees on 2/5/2022
        leftMotor.set(ControlMode.Velocity, 6500);

    }

    public void shootLow() {
        // 5000 works well for low shot on 2/5/2022
        // shooter degree at 4.5 degrees on 2/5/2022
        leftMotor.set(ControlMode.Velocity, 5000);

    }

    public void stop() {
        leftMotor.set(0.0);
    }
}