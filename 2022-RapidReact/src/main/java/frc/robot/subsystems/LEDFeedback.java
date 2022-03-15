package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class LEDFeedback extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(LEDFeedback.class);

    public static final int LED_ARRAY_LENGTH = 20;
    private AddressableLED ledString;
    private AddressableLED ledString2;
    private AddressableLEDBuffer ledBuffer;
    private AnalogInput gauge;

    private final I2C.Port i2cPort = I2C.Port.kMXP;

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    private final ColorMatch m_colorMatcher = new ColorMatch();
    private final Color kBlackTarget = new Color(0, 0, 0);
    private boolean climbingActivated = false;
    private double flashTimer = 200.0;
    private boolean isLEDOn = false;

    public LEDFeedback() {
        ledString = new AddressableLED(8);
        ledString.setLength(LED_ARRAY_LENGTH);
        ledBuffer = new AddressableLEDBuffer(LED_ARRAY_LENGTH);
        ledString.setData(ledBuffer);
        ledString.start();
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }

    public void setColor(Color color) {
        for (int i = 0; i < LED_ARRAY_LENGTH; i++) {
            ledBuffer.setLED(i, color);
        }
        ledString.setData(ledBuffer);
        ledString.start();
    }

    public void setFlash(Color color, double flashSpeed) {
        // Do not use Timer.delay here as it will interrupt default commands
        if (System.currentTimeMillis() - flashSpeed > flashTimer) {
            
            flashTimer = System.currentTimeMillis(); //Reset the flash Timer
            //Toggle flash boolean and set LEDs
            if (isLEDOn) {
                isLEDOn = false;
                setColor(Color.kBlack);

            } else {
                isLEDOn = true;
                setColor(color);
            }
        }
    }

    public boolean isClimbLineDetected(){
        Color detectedColor = m_colorSensor.getColor();
        return detectedColor.equals(Color.kBlack);
    }

    public boolean isClimbingActivated() {
        return climbingActivated;
    }

    public void setClimbingActivated(boolean climbingActivated) {
        this.climbingActivated = climbingActivated;
    }
    public double checkPressure() {
        final PowerDistribution pdp = RobotContainer.getInstance().m_powerdistribution;
        //final double outputVoltage = gauge.getAverageVoltage();
        final double supplyVoltage = pdp.getVoltage();

        //double pressure = 250 * (outputVoltage / supplyVoltage) - 25;

        
        //return pressure;
        return -1.0;
    }
}
