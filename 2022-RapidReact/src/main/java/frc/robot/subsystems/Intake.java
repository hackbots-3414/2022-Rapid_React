package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Intake.class);

    Solenoid solenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.IntakeConstants.solenoidChannel);
    WPI_TalonFX intakeMotor = new WPI_TalonFX(Constants.IntakeConstants.intakeMotor);

    public void goDown() {
        solenoid.set(true);
    }

    public Intake() {
    }

    public void goUp() {
        solenoid.set(false);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }

    public void stop() {
        setSpeed(0.0);
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }
}
