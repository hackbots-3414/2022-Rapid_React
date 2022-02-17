package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.BeltConstants;

public class Belt extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Belt.class);

    private WPI_TalonFX topMotor;
    private WPI_TalonFX middleMotor;
    private WPI_TalonFX bottomMotor;

    public DigitalInput irsfront = new DigitalInput(1);
    public DigitalInput irsback = new DigitalInput(0);

    private boolean conveyorSensorFront = false;
    private boolean conveyorSensorBack = false;

    public Belt() {
        topMotor = new WPI_TalonFX(BeltConstants.topMotor);
        middleMotor = new WPI_TalonFX(BeltConstants.middleMotor);
        bottomMotor = new WPI_TalonFX(BeltConstants.bottomMotor);
    }

    @Override
    public void periodic() {
    }

    public void go() {
        topMotor.set(Constants.BeltConstants.motorSpeed / 3);
        middleMotor.set(Constants.BeltConstants.motorSpeed / 2);
        bottomMotor.set(-Constants.BeltConstants.motorSpeed);
        setConveyorSensorback(false);
        setConveyorSensorfront(false);
    }

    public void stop() {
        topMotor.set(0);
        middleMotor.set(0);
        bottomMotor.set(0);
    }

    public boolean atSpeed() {
        return (Math.abs(((topMotor.getSelectedSensorVelocity() + middleMotor.getSelectedSensorVelocity() + bottomMotor.getSelectedSensorVelocity()) / 3) - Constants.BeltConstants.motorSpeed) <= 5);
    }

    public int getConveyorState() {
        if (conveyorSensorFront == false && conveyorSensorBack == false) {
            return 0;
        } else if (conveyorSensorBack == true && conveyorSensorFront == false) {
            return 1;
        }
        return 2;
    }

    public void setConveyorSensorfront(boolean conveyorSensorFront) {
        this.conveyorSensorFront = conveyorSensorFront;
    }

    public void setConveyorSensorback(boolean conveyorSensorBack) {
        this.conveyorSensorBack = conveyorSensorBack;
    }
}