package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DifferentialFollower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.hardware.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotConstants;

public class Drivetrain extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Drivetrain.class);

    private boolean controlsReversed = false;


    private boolean wantLow = true;


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

        public void setOffsets(TalonFX frontLeft, TalonFX backLeft, TalonFX frontRight, TalonFX backRight) {
            this.frontLeft = frontLeft.getRotorPosition().getValueAsDouble();
            this.backLeft = backLeft.getRotorPosition().getValueAsDouble();
            this.frontRight = frontRight.getRotorPosition().getValueAsDouble();
            this.backRight = backRight.getRotorPosition().getValueAsDouble();
        }
    };

    private EncoderOffsets encoderOffsets = new EncoderOffsets();

    private AHRS ahrs = new AHRS(Port.kMXP);

    private TalonFX backLeft;
    private TalonFX backRight;
    private TalonFX frontLeft;
    private TalonFX frontRight;
    private DifferentialDrive differentialDrive;
    private DifferentialDriveOdometry m_odometry;

    private SupplyCurrentLimitConfiguration frontSupplyLimit = new SupplyCurrentLimitConfiguration(false, DriveConstants.driveLowCurrentLimit, DriveConstants.driveLowCurrentLimit, DriveConstants.triggerThresholdTime);

    public Drivetrain() {
        frontLeft = createTalonFX(DriveConstants.kLeftMotorFrontPort, InvertedValue.Clockwise_Positive);
        backLeft = createTalonFX(DriveConstants.kLeftMotorRearPort, InvertedValue.Clockwise_Positive);
        frontRight = createTalonFX(DriveConstants.kRightMotorFrontPort, InvertedValue.CounterClockwise_Positive);
        backRight = createTalonFX(DriveConstants.kRightMotorRearPort, InvertedValue.CounterClockwise_Positive);

        backRight.setControl(new DifferentialFollower(frontRight.getDeviceID(), false));
        backLeft.setControl(new DifferentialFollower(frontLeft.getDeviceID(), false));

        setHighCurrentLimit();
        setLowCurrentLimit();

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        m_odometry = new DifferentialDriveOdometry(ahrs.getRotation2d());
    }
    
    private TalonFX createTalonFX(int deviceID, InvertedValue direction) {
        TalonFX motor = new TalonFX(deviceID);
        motor.getConfigurator().apply(new TalonFXConfiguration());
        OpenLoopRampsConfigs openLoopRampsConfigs = new OpenLoopRampsConfigs();
        motor.getConfigurator().apply(openLoopRampsConfigs, 0.2);
        motor.configOpenloopRamp(DriveConstants.voltageRampRate);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        motor.setSelectedSensorPosition(0, 0, 10);
        motor.InvertedValue(direction);



        return motor;
    }

    public void setLowCurrentLimit(){

        frontLeft.configSupplyCurrentLimit(frontSupplyLimit);
        frontRight.configSupplyCurrentLimit(frontSupplyLimit);
       }

    public void setHighCurrentLimit() {
        frontRight.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        backRight.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        backLeft.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        frontLeft.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
    }

    public boolean isLowLimitEnabled() {
        return frontSupplyLimit.enable;
    }

    public void LowLimitEnable(boolean enable) {
        frontSupplyLimit.enable = enable;
        setLowCurrentLimit();
    }

    public void requestCurrentLimit(boolean wantLow) {
    this.wantLow = wantLow;

    }

    public boolean isLowCurrentRequested() {
        return wantLow;

    }


    public void setBrakeMode() {
        frontLeft.setControl(new StaticBrake());
        frontRight.setControl(new StaticBrake());
        backLeft.setControl(new StaticBrake());
        backRight.setControl(new StaticBrake());
    }

    public void setCoastMode() {
        frontLeft.setControl(new NeutralOut());
        frontRight.setControl(new NeutralOut());
        backLeft.setControl(new NeutralOut());
        backRight.setControl(new NeutralOut());
    }

    public boolean isControlsReversed() {
        return controlsReversed;
    }

    @Override
    public void periodic() {
        differentialDrive.feed();
        m_odometry.update(ahrs.getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance());
        super.periodic();
    }

    @Override
    public void simulationPeriodic() {
        super.simulationPeriodic();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftMetersPerSecond(), getRightMetersPerSecond());
    }

    public double getLeftEncoderPosition() {
        return ((frontLeft.getRotorPosition().getValueAsDouble() + backLeft.getRotorPosition().getValueAsDouble()) / 2D) - encoderOffsets.frontLeft;
    }

    public double getLeftEncoderDistance() {
        return getLeftEncoderPosition() * RobotConstants.kDistancePerTick;
    }

    public double getLeftEncoderVelocity() {
        return frontLeft.getRotorVelocity().getValueAsDouble();
    }

    public double getLeftMetersPerSecond() {
        SmartDashboard.putNumber("left speed", getLeftEncoderVelocity() * RobotConstants.kDistancePerTick * 10);
        return getLeftEncoderVelocity() * RobotConstants.kDistancePerTick * 10;
    }

    public double getRightEncoderPosition() {
        return ((frontRight.getRotorPosition().getValueAsDouble() + backRight.getRotorPosition().getValueAsDouble()) / 2D) - encoderOffsets.frontRight;
    }

    public double getRightEncoderDistance() {
        return getRightEncoderPosition() * RobotConstants.kDistancePerTick;
    }

    public double getRightEncoderVelocity() {
        return frontRight.getRotorVelocity().getValueAsDouble();
    }

    public double getRightMetersPerSecond() {
        SmartDashboard.putNumber("right speed", getRightEncoderVelocity() * RobotConstants.kDistancePerTick * 10);
        return getRightEncoderVelocity() * RobotConstants.kDistancePerTick * 10;
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

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        frontLeft.setVoltage(leftVolts);    
        frontRight.setVoltage(rightVolts);
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

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, ahrs.getRotation2d());
    }
}
