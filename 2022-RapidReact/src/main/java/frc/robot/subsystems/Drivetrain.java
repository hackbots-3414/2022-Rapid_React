package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Drivetrain.class);

    private boolean controlsReversed = false;

    private AHRS ahrs = new AHRS(Port.kMXP);

    private WPI_TalonFX backLeft;
    private WPI_TalonFX backRight;
    private WPI_TalonFX frontLeft;
    private WPI_TalonFX frontRight;
    private DifferentialDrive differentialDrive;

    public Drivetrain() {
        backLeft = createTalonFX(DriveConstants.kLeftMotorRearPort, TalonFXInvertType.Clockwise);
        backRight = createTalonFX(DriveConstants.kRightMotorRearPort, TalonFXInvertType.CounterClockwise);
        frontLeft = createTalonFX(DriveConstants.kLeftMotorFrontPort, TalonFXInvertType.Clockwise);
        frontRight = createTalonFX(DriveConstants.kRightMotorFrontPort, TalonFXInvertType.CounterClockwise);

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        backLeft.follow(frontLeft);
        backRight.follow(frontRight);
    }

    private WPI_TalonFX createTalonFX(int deviceID, TalonFXInvertType direction) {
        WPI_TalonFX motor = new WPI_TalonFX(deviceID);
        motor.configFactoryDefault();
        motor.configOpenloopRamp(DriveConstants.voltageRampRate);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        motor.setInverted(direction);

        return motor;
    }

    public void setControlsReversed(boolean controlsReversed) {
        this.controlsReversed = controlsReversed;
    }

    public boolean isControlsReversed() {
        return controlsReversed;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("EncoderFrontLeft", frontLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("EncoderBackLeft", backLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("EncoderFrontRight", frontRight.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("EncoderBackRight", backRight.getSelectedSensorPosition(0));
    }

    @Override
    public void simulationPeriodic() {
    }

    public double getLeftEncoderPosition() {
        return backLeft.getSelectedSensorPosition();
    }

    public double getRightEncoderPosition() {
        return frontRight.getSelectedSensorPosition();
    }

    public double getAverageEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }

    public void resetEncoders() {
        frontLeft.setSelectedSensorPosition(0);
        frontRight.setSelectedSensorPosition(0);
        backRight.setSelectedSensorPosition(0);
        backLeft.setSelectedSensorPosition(0);
    }

    public void arcadeDrive(double throttle, double steering) {
        LOG.trace("Throttle = {}, Steering = {}, ControlsReversed = {}", throttle, steering, controlsReversed);
        if (controlsReversed) {
            differentialDrive.arcadeDrive(throttle, steering);
        } else {
            differentialDrive.arcadeDrive(-throttle, steering);
        }
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
