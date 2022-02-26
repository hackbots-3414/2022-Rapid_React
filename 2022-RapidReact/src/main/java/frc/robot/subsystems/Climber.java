package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

    // Solenoid climber = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel);

    public Climber() {}

    public void climberUp() {
        // climber.set(true);
    }

    public void climberDown() {
        // climber.set(false);
    }

    @Override
    public void periodic() {
    }
}
