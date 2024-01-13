package frc.robot.subsystems;

import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
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
            this.frontLeft = frontLeft.getRotorPosition().getValue();
            this.backLeft = backLeft.getRotorPosition().getValue();
            this.frontRight = frontRight.getRotorPosition().getValue();
            this.backRight = backRight.getRotorPosition().getValue();
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
    private CurrentLimitsConfigs frontSupplyLimit;
    private CurrentLimitsConfigs highSupplyLimit;

    public Drivetrain() {
        frontLeft = createTalonFX(DriveConstants.kLeftMotorFrontPort, InvertedValue.CounterClockwise_Positive);
        backLeft = createTalonFX(DriveConstants.kLeftMotorRearPort, InvertedValue.CounterClockwise_Positive);
        frontRight = createTalonFX(DriveConstants.kRightMotorFrontPort, InvertedValue.Clockwise_Positive);
        backRight = createTalonFX(DriveConstants.kRightMotorRearPort, InvertedValue.Clockwise_Positive);
    
        frontSupplyLimit = new CurrentLimitsConfigs();
        frontSupplyLimit.SupplyCurrentLimitEnable = false;
        frontSupplyLimit.SupplyCurrentLimit = DriveConstants.driveLowCurrentLimit;
        frontSupplyLimit.SupplyCurrentThreshold = DriveConstants.driveLowCurrentLimit;
        frontSupplyLimit.SupplyTimeThreshold =  DriveConstants.triggerThresholdTime;

        highSupplyLimit = new CurrentLimitsConfigs();
        highSupplyLimit.StatorCurrentLimitEnable = true;
        highSupplyLimit.StatorCurrentLimit = DriveConstants.driveLowCurrentLimit;
        highSupplyLimit.SupplyCurrentThreshold = DriveConstants.driveLowCurrentLimit;
        highSupplyLimit.SupplyTimeThreshold =  DriveConstants.triggerThresholdTime;


        backRight.setControl(new Follower(frontRight.getDeviceID(), false));
        backLeft.setControl(new Follower(frontLeft.getDeviceID(), false));
        // backRight.follow(frontRight);
        // backLeft.follow(frontLeft);
        setHighCurrentLimit();
        setLowCurrentLimit();

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        m_odometry = new DifferentialDriveOdometry(ahrs.getRotation2d());
    }
    
    private TalonFX createTalonFX(int deviceID, InvertedValue m_invert) {
        TalonFX motor = new TalonFX(deviceID);
        OpenLoopRampsConfigs openLoopConf = new OpenLoopRampsConfigs();
        CurrentLimitsConfigs currentLimitConf = new CurrentLimitsConfigs();
        FeedbackConfigs feedconf = new FeedbackConfigs();
        MotorOutputConfigs motoroutconf = new MotorOutputConfigs();
        
    
        // motor.configFactoryDefault();
        motor.getConfigurator().apply(new TalonFXConfiguration());

        openLoopConf.VoltageOpenLoopRampPeriod = DriveConstants.voltageRampRate;
        motor.getConfigurator().apply(openLoopConf);
        //motor.configOpenloopRamp(DriveConstants.voltageRampRate);

        currentLimitConf.SupplyCurrentLimitEnable = true;
        currentLimitConf.SupplyCurrentLimit = DriveConstants.driveCurrentLimit;
        currentLimitConf.SupplyCurrentThreshold = DriveConstants.driveCurrentLimit;
        currentLimitConf.SupplyTimeThreshold =  DriveConstants.triggerThresholdTime;
        motor.getConfigurator().apply(currentLimitConf);
        //motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        
        feedconf.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
        feedconf.FeedbackRotorOffset = 0; 
        motor.getConfigurator().apply(feedconf);
        //motor.configSelectedFeedbackSensor(FeedbackSensorSourceValue.RotorSensor, 0, 10);
        //motor.setSelectedSensorPosition(0, 0, 10);

        motoroutconf.Inverted = m_invert;
        motor.getConfigurator().apply(motoroutconf);
        // motor.setInverted(isInverted);

        return motor;
    }

    public void setLowCurrentLimit(){
         frontLeft.getConfigurator().apply(frontSupplyLimit);
         frontRight.getConfigurator().apply(frontSupplyLimit);
        //frontLeft.configSupplyCurrentLimit(frontSupplyLimit);
        //frontRight.configSupplyCurrentLimit(frontSupplyLimit);
       }

    public void setHighCurrentLimit() {
        // frontRight.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        // backRight.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        // backLeft.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        // frontLeft.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit, DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        frontRight.getConfigurator().apply(highSupplyLimit);
        backRight.getConfigurator().apply(highSupplyLimit);
        backLeft.getConfigurator().apply(highSupplyLimit);
        frontLeft.getConfigurator().apply(highSupplyLimit);

    }
    
    public boolean isLowLimitEnabled() {
        return frontSupplyLimit.SupplyCurrentLimitEnable;
    }

    public void LowLimitEnable(boolean enable) {
        frontSupplyLimit.SupplyCurrentLimitEnable = enable;
        setLowCurrentLimit();
    }

    public void requestCurrentLimit(boolean wantLow) {
    this.wantLow = wantLow;

    }

    public boolean isLowCurrentRequested() {
        return wantLow;

    }


    public void setBrakeMode() {
        frontLeft.setNeutralMode(NeutralModeValue.Brake);
        frontRight.setNeutralMode(NeutralModeValue.Brake);
        backLeft.setNeutralMode(NeutralModeValue.Brake);
        backRight.setNeutralMode(NeutralModeValue.Brake);
    }

    public void setCoastMode() {
        frontLeft.setNeutralMode(NeutralModeValue.Coast);
        frontRight.setNeutralMode(NeutralModeValue.Coast);
        backLeft.setNeutralMode(NeutralModeValue.Coast);
        backRight.setNeutralMode(NeutralModeValue.Coast);
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
        return ((frontLeft.getRotorPosition().getValue() + backLeft.getRotorPosition().getValue()) / 2D) - encoderOffsets.frontLeft;
        //return ((frontLeft.getSelectedSensorPosition() + backLeft.getSelectedSensorPosition()) / 2D) - encoderOffsets.frontLeft;
    }

    public double getLeftEncoderDistance() {
        return getLeftEncoderPosition() * RobotConstants.kDistancePerTick;
    }

    public double getLeftEncoderVelocity() {
        // return frontLeft.getSelectedSensorVelocity();
        return frontLeft.getRotorVelocity().getValue();
    }

    public double getLeftMetersPerSecond() {
        SmartDashboard.putNumber("left speed", getLeftEncoderVelocity() * RobotConstants.kDistancePerTick * 10);
        return getLeftEncoderVelocity() * RobotConstants.kDistancePerTick * 10;
    }

    public double getRightEncoderPosition() {
        return ((frontRight.getRotorPosition().getValue() + backRight.getRotorPosition().getValue()) / 2D) - encoderOffsets.frontRight;
    }

    public double getRightEncoderDistance() {
        return getRightEncoderPosition() * RobotConstants.kDistancePerTick;
    }

    public double getRightEncoderVelocity() {
        // return frontRight.getSelectedSensorVelocity();
        return frontRight.getRotorVelocity().getValue();
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
