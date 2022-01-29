package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OI {

    private static final Logger LOG = LoggerFactory.getLogger(OI.class);
    private static Joystick joystick = new Joystick(0);
    


    static {

    SmartDashboard.putNumber("Left_X_Max", 0.7);
    SmartDashboard.putNumber("Left_Y_Max", 0.6);
    SmartDashboard.putNumber("Right_X_Max", 0.8);
    SmartDashboard.putNumber("Right_Y_Max", 0.8);




    }

    public static double getThrottle() {
        return joystick.getRawAxis(1) / SmartDashboard.getNumber("Left_Y_Max", 1.0);

    }

    public static double getSteering() { 

        return joystick.getRawAxis(3) / SmartDashboard.getNumber("Right_X_Max", 1.0);

    }



}
