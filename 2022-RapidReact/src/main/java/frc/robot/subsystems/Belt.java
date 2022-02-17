package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Constants;
import frc.robot.Constants.BeltConstants;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BeltConstants;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
public class Belt extends SubsystemBase {

  private static final Logger LOG = LoggerFactory.getLogger(Belt.class);

  DigitalInput irBottom = new DigitalInput(0);
  DigitalInput irTop = new DigitalInput(1);
  WPI_TalonFX topMotor = new WPI_TalonFX(BeltConstants.topMotor);
  WPI_TalonFX middleMotor = new WPI_TalonFX(BeltConstants.middleMotor);
  WPI_TalonFX bottomMotor = new WPI_TalonFX(BeltConstants.bottomMotor);
  WPI_TalonFX intakeMotor = new WPI_TalonFX(Constants.BeltConstants.intakeMotor);
  Solenoid solenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.BeltConstants.solenoidChannel);


  public void goDown() {solenoid.set(true);}

  public void goUp() {solenoid.set(false);}

  public boolean getIRBottom () {return !irBottom.get();}

  public boolean getIRTop () {return !irTop.get();}

  public void startIntakeMotor () {intakeMotor.set(Constants.BeltConstants.Motorspeed);}

  public void stopIntakeMotor () {intakeMotor.set(0.0);}

  public void startMotorTop () {topMotor.set(Constants.BeltConstants.Motorspeed);}

  public void stopMotorTop () {topMotor.set(0.0);}

  public void startMotorMiddle () {middleMotor.set(Constants.BeltConstants.Motorspeed);}

  public void stopMotorMiddle () {middleMotor.set(0.0);}
  
  public void startMotorBottom () {bottomMotor.set(Constants.BeltConstants.Motorspeed);}

  public void stopMotorBottom () {bottomMotor.set(0.0);}

  public void stopAllMotors () {
    bottomMotor.set(0.0);
    middleMotor.set(0.0);
    topMotor.set(0.0);
    intakeMotor.set(0.0);
  }

  public void startAllMotors () {
    bottomMotor.set(Constants.BeltConstants.Motorspeed);
    middleMotor.set(Constants.BeltConstants.Motorspeed);
    topMotor.set(Constants.BeltConstants.Motorspeed);
    intakeMotor.set(Constants.BeltConstants.Motorspeed);
  }

  @Override
  public void periodic() {}
}