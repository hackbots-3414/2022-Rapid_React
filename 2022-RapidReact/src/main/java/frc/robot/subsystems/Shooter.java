package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Shooter.class);

    private WPI_TalonFX rightMotor;
    private WPI_TalonFX leftMotor;

    public Shooter() {
        rightMotor = new WPI_TalonFX(20);

        leftMotor = new WPI_TalonFX(21);

        leftMotor.setInverted(TalonFXInvertType.Clockwise);
        rightMotor.setInverted(TalonFXInvertType.CounterClockwise);
        rightMotor.follow(leftMotor);
        LOG.error("got here");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shoot() {
        leftMotor.set(ControlMode.Velocity, 5665);
    }

    public void stop() {
        leftMotor.set(0.0);
    }
}
