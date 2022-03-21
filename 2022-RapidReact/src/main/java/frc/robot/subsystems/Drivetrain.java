package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;
import com.kauailabs.navx.frc.AHRS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Drivetrain.class);

    private boolean controlsReversed = false;

    private boolean wantHigh = true;

    private class EncoderOffsets {
        public double frontLeft;
        public double backLeft;
        public double frontRight;
        public double backRight;

        EncoderOffsets() {
            frontLeft = 0;
            backLeft = 0;
            frontRight = 0;
            backRight = 0;
        }

        public void setOffsets(WPI_TalonFX frontLeft, WPI_TalonFX backLeft, WPI_TalonFX frontRight, WPI_TalonFX backRight) {
            this.frontLeft = frontLeft.getSelectedSensorPosition();
            this.backLeft = backLeft.getSelectedSensorPosition();
            this.frontRight = frontRight.getSelectedSensorPosition();
            this.backRight = backRight.getSelectedSensorPosition();
        }
    };

    private EncoderOffsets encoderOffsets = new EncoderOffsets();

    private AHRS ahrs = new AHRS(Port.kMXP);

    private WPI_TalonFX backLeft;
    private WPI_TalonFX backRight;
    private WPI_TalonFX frontLeft;
    private WPI_TalonFX frontRight;
    private DifferentialDrive differentialDrive;

    public Drivetrain() {
        frontLeft = createTalonFX(DriveConstants.kLeftMotorFrontPort, TalonFXInvertType.Clockwise);
        backLeft = createTalonFX(DriveConstants.kLeftMotorRearPort, TalonFXInvertType.Clockwise);
        frontRight = createTalonFX(DriveConstants.kRightMotorFrontPort, TalonFXInvertType.CounterClockwise);
        backRight = createTalonFX(DriveConstants.kRightMotorRearPort, TalonFXInvertType.CounterClockwise);

        backRight.follow(frontRight);
        backLeft.follow(frontLeft);

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);
    }
    
    private WPI_TalonFX createTalonFX(int deviceID, TalonFXInvertType direction) {
        WPI_TalonFX motor = new WPI_TalonFX(deviceID);
        motor.configFactoryDefault();
        motor.configOpenloopRamp(DriveConstants.voltageRampRate);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        motor.setSelectedSensorPosition(0, 0, 10);
        motor.setInverted(direction);



        return motor;
    }

    public void setLowCurrentLimit(){
        frontLeft.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveLowCurrentLimit, DriveConstants.driveLowCurrentLimit, DriveConstants.triggerThresholdTime));
        frontRight.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveLowCurrentLimit, DriveConstants.driveLowCurrentLimit, DriveConstants.triggerThresholdTime));
        backLeft.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveLowCurrentLimit, DriveConstants.driveLowCurrentLimit, DriveConstants.triggerThresholdTime));
        backRight.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveLowCurrentLimit, DriveConstants.driveLowCurrentLimit, DriveConstants.triggerThresholdTime));
    }

    public void setHighCurrentLimit() {
        frontLeft.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        frontRight.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        backLeft.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        backRight.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));

    }

    public void requestCurrentLimit(boolean wantHigh) {
    this.wantHigh = wantHigh;

    }

    public boolean isHighLimitEnabled() {
        return wantHigh;
    }


    public void setBrakeMode() {
        frontLeft.setNeutralMode(NeutralMode.Brake);
        frontRight.setNeutralMode(NeutralMode.Brake);
        backLeft.setNeutralMode(NeutralMode.Brake);
        backRight.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoastMode() {
        frontLeft.setNeutralMode(NeutralMode.Coast);
        frontRight.setNeutralMode(NeutralMode.Coast);
        backLeft.setNeutralMode(NeutralMode.Coast);
        backRight.setNeutralMode(NeutralMode.Coast);
    }

    public boolean isControlsReversed() {
        return controlsReversed;
    }

    @Override
    public void periodic() {
        differentialDrive.feed();
        super.periodic();
    }

    @Override
    public void simulationPeriodic() {
        super.simulationPeriodic();
    }

    public double getLeftEncoderPosition() {
        return ((frontLeft.getSelectedSensorPosition() + backLeft.getSelectedSensorPosition()) / 2D) - encoderOffsets.frontLeft;
    }

    public double getRightEncoderPosition() {
        return ((frontRight.getSelectedSensorPosition() + backRight.getSelectedSensorPosition()) / 2D) - encoderOffsets.frontRight;
    }

    public double getAverageEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }

    public void resetEncoders() {
        encoderOffsets.setOffsets(frontLeft, backLeft, frontRight, backRight);
    }

    public void arcadeDrive(double throttle, double steering) {
        LOG.trace("Throttle = {}, Steering = {}, ControlsReversed = {}", throttle, steering, controlsReversed);
        differentialDrive.arcadeDrive(throttle, steering);
    }

    public void curvatureDrive(double throttle, double rotation, boolean turnInPlace) {
        DifferentialDrive.curvatureDriveIK(throttle, rotation, turnInPlace);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void resetHeading() {
        LOG.info("Reseting Heading...");
        ahrs.reset();
    }

    public double getHeading() {
        double angle = ahrs.getYaw();
        LOG.info("NavX Heading: {}", angle);
        return angle;
    }

    public void stopDriving() {
        tankDrive(0, 0);
    }
}
