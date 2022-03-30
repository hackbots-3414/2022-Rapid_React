package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;



public class Climber extends SubsystemBase {

    public static final int CLIMBER_ARRAY_LENGTH = 10;
    private double Climber_Array[] = new double[CLIMBER_ARRAY_LENGTH]; 
    private static int Array_Slot = 0;

    Solenoid climber_1 = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel_1);
    Solenoid climber_2 = new Solenoid(PneumaticsModuleType.REVPH, ClimberConstants.climberSolenoidChannel_2);
    Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);

    public Climber() {
        phCompressor.enableAnalog(95, 120);
        SmartDashboard.putBoolean("Climber Two", false);
        SmartDashboard.putBoolean("Climber Three", false);

    }

    public void climberUp() {
        climber_1.set(true);
        climber_2.set(true);
    }

    public void climberDown() {
        climber_1.set(false);
        climber_2.set(false);
    }

    public double getAveragePressure() {
        double Array_Total = 0;
        for (int i = 0; i < CLIMBER_ARRAY_LENGTH; i++) {
            Array_Total = Array_Total+Climber_Array[i];
        }
        return Array_Total/CLIMBER_ARRAY_LENGTH;
    }


    @Override
    public void periodic() {

        if (Array_Slot <=   CLIMBER_ARRAY_LENGTH-1){

            Climber_Array[Array_Slot] = phCompressor.getPressure();
            Array_Slot++;

        }
        else {

            Array_Slot = 0;

            SmartDashboard.putNumber("Air Pressure", getAveragePressure());
            if (getAveragePressure()>Constants.ClimberConstants.minLevelTwoClimb){
                SmartDashboard.putBoolean("Climber Two", true);
            } else{
                SmartDashboard.putBoolean("Climber Two", false);
            }

            if (getAveragePressure()>Constants.ClimberConstants.minLevelThreeClimb){
                SmartDashboard.putBoolean("Climber Three", true);
            } else{
                SmartDashboard.putBoolean("Climber Three", false);
            }
        }
       
        
    }
}
