package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

    Solenoid climber_1 = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel_1);
    Solenoid climber_2 = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel_2);

    public Climber() {}

    public void climberUp() {
        climber_1.set(true);
        climber_2.set(true);
    }

    public void climberDown() {
        climber_1.set(false);
        climber_2.set(false);
    }

    @Override
    public void periodic() {
    }
}
