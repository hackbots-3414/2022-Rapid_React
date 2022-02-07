package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.wpi.first.wpilibj.XboxController;


public class OI {

    private static final Logger LOG = LoggerFactory.getLogger(OI.class);
    private static Joystick joystick = new Joystick(0);
    

    static double left_x_offset = -0.13379;
    static double left_x_max = 0.81982;
    static double left_y_offset = 0.01758;
    static double left_y_max = 0.62793;
    static double right_x_offset = 0.03418;
    static double right_x_max = 0.80713;
    static double right_y_offset = 0.03418;
    static double right_y_max = 0.85005;
    static double r_knob_offset = 0.03371;

    static {
        SmartDashboard.putBoolean("Controller (false = dev, true = comp)", false);
    }

    private static void updateController() {
        if (SmartDashboard.getBoolean("Controller (false = dev, true = comp)", false)) {
            left_x_offset = -0.05518;
            left_x_max = 0.83401;
            left_y_offset = -0.01953;
            left_y_max = 0.64453;
            right_x_offset = 0.03711;
            right_x_max = 0.73144;
            right_y_offset = 0.01367;
            right_y_max = 0.87256;
            r_knob_offset = 0.03371;
        }
        else {
            left_x_offset = -0.13379;
            left_x_max = 0.81982;
            left_y_offset = 0.01758;
            left_y_max = 0.62793;
            right_x_offset = 0.03418;
            right_x_max = 0.80713;
            right_y_offset = 0.03418;
            right_y_max = 0.85005;
            r_knob_offset = 0.03613;
        }
    }

    public static double getLeftLateral() {
        updateController();
        return (joystick.getRawAxis(0) - left_x_offset) / left_x_max;
    }

    public static double getLeftVertical() {
        updateController();
        return (joystick.getRawAxis(1) - left_y_offset) / left_y_max;
    }

    public static double getRightLateral() {
        updateController();
        return (joystick.getRawAxis(3) - right_x_offset) / right_x_max;
    }

    public static double getRightVertical() {
        updateController();
        return (joystick.getRawAxis(4) - right_y_offset) / right_y_max;
    }

    public static double getRKnob() {
        updateController();
        return (joystick.getRawAxis(6) + r_knob_offset + 1) / 2;
    }

    public static double getRKnobRaw() {
        updateController();
        return joystick.getRawAxis(6);
    }

    public static boolean getButtonA() {
        updateController();
        return joystick.getRawButton(1);
    }

    public static int getButtonB() {
        updateController();
        // returns the value of switch B as labled on the controller (down == 0, middle == 1, up == 2)

        if (joystick.getRawButton(2)) {
            return 0;
        }
        else if (joystick.getRawButton(3)) {
            return 2;
        }
        else {
            return 1;
        }
    }

}
