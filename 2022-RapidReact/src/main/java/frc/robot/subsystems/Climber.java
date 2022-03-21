package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

    Solenoid climber_1 = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel_1);
    Solenoid climber_2 = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel_2);
    Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);

    public Climber() {
        phCompressor.enableAnalog(95, 120);
    }

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
       
        SmartDashboard.putNumber("Air Pressure", phCompressor.getPressure());
    }
}
