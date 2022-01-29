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

    private static final Logger LOG = LoggerFactory.getLogger("Constants.java");
    private static Joystick joystick = new Joystick(0);
    


    static {

        SmartDashboard.putNumber("Left_X_Offset", -0.13379);
        SmartDashboard.putNumber("Left_X_Max", 0.81982);
        SmartDashboard.putNumber("Left_Y_Offset", 0.01758);
        SmartDashboard.putNumber("Left_Y_Max", 0.62793);
        SmartDashboard.putNumber("Right_X_Offset", 0.03418);
        SmartDashboard.putNumber("Right_X_Max", 0.80713);
        SmartDashboard.putNumber("Right_Y_Offset", 0.03418);
        SmartDashboard.putNumber("Right_Y_Max", 0.85005);

    }

    public static double getLeftLateral() {
        return (joystick.getRawAxis(0) - SmartDashboard.getNumber("Left_X_Offset", 0.0)) / SmartDashboard.getNumber("Left_X_Max", 1.0);
    }

    public static double getLeftVertical() {
        return (joystick.getRawAxis(1) - SmartDashboard.getNumber("Left_Y_Offset", 0.0)) / SmartDashboard.getNumber("Left_Y_Max", 1.0);
    }

    public static double getRightLateral() {
        return (joystick.getRawAxis(3) - SmartDashboard.getNumber("Right_X_Offset", 0.0)) / SmartDashboard.getNumber("Right_X_Max", 1.0);
    }

    public static double getRightVertical() {
        return (joystick.getRawAxis(4) - SmartDashboard.getNumber("Right_Y_Offset", 0.0)) / SmartDashboard.getNumber("Right_Y_Max", 1.0);
    }

    public static int getButtonB() {
        // returns the value of switch B as labled on the controller (down == 0, middle == 1, up == 2)

        if (joystick.getRawButton(1)) {
            return 0;
        }
        else if (joystick.getRawButton(2)) {
            return 2;
        }
        else {
            return 1;
        }
    }

}
