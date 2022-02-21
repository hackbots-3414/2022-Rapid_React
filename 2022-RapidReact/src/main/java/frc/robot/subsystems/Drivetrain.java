package frc.robot.subsystems;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI.Port;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public class Drivetrain extends SubsystemBase {
    private static final Logger LOG = LoggerFactory.getLogger(Drivetrain.class);

    private AHRS ahrs = new AHRS(Port.kMXP);
    private final DifferentialDriveOdometry m_odometry;

    private WPI_TalonFX backLeft;
    private WPI_TalonFX backRight;
    private WPI_TalonFX frontLeft;
    private WPI_TalonFX frontRight;
    private DifferentialDrive differentialDrive;

    public Drivetrain() {
        backLeft = new WPI_TalonFX(DriveConstants.kLeftMotorRearPort);

        backRight = new WPI_TalonFX(DriveConstants.kRightMotorRearPort);

        frontLeft = new WPI_TalonFX(DriveConstants.kLeftMotorFrontPort);

        frontRight = new WPI_TalonFX(DriveConstants.kRightMotorFrontPort);

        backLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        
        backRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0); 

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        resetEncoders();
        m_odometry = new DifferentialDriveOdometry(ahrs.getRotation2d());
        frontLeft.setInverted(TalonFXInvertType.Clockwise);
        backLeft.setInverted(TalonFXInvertType.Clockwise);
        frontRight.setInverted(TalonFXInvertType.CounterClockwise);
        backRight.setInverted(TalonFXInvertType.CounterClockwise);
        frontRight.setSensorPhase(true);
        backRight.setSensorPhase(true);
        backRight.follow(frontRight);
        backLeft.follow(frontLeft);
        configureTalonFX(frontLeft);
        configureTalonFX(backLeft);
        configureTalonFX(frontRight);
        configureTalonFX(backRight);
    }

    /**
     * Set the basic settings we want across all drivetrain motors
     * @param motor motor to configure
     * @return motor
     */
    private WPI_TalonFX configureTalonFX(WPI_TalonFX motor) {
        motor.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_10Ms);
        return motor;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        m_odometry.update(ahrs.getRotation2d(), getLeftEncoderDistance(), getRightEncoderDistance());

    }

    public DifferentialDriveOdometry getOdometry() {
        return this.m_odometry;
    }

    public Pose2d getPose() {
        LOG.debug("Pose - Translation: {}, X: {}, Y: {}, Rotation: {}, Pose: {}", m_odometry.getPoseMeters().getTranslation(), m_odometry.getPoseMeters().getX(), m_odometry.getPoseMeters().getY(), m_odometry.getPoseMeters().getRotation(), m_odometry.getPoseMeters().toString());
        return m_odometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        LOG.trace("Pre-Reset Encoders - Backleft: {}, Backright: {}, Frontleft: {}, Frontright: {}", backLeft.getSelectedSensorPosition(), backRight.getSelectedSensorPosition(), frontLeft.getSelectedSensorPosition(), frontRight.getSelectedSensorPosition());
        resetEncoders();
        LOG.trace("Post-Reset Encoders - Backleft: {}, Backright: {}, Frontleft: {}, Frontright: {}", backLeft.getSelectedSensorPosition(), backRight.getSelectedSensorPosition(), frontLeft.getSelectedSensorPosition(), frontRight.getSelectedSensorPosition());
        m_odometry.resetPosition(pose, ahrs.getRotation2d());
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftMetersPerSecond(), getRightMetersPerSecond());
    }

    @Override
    public void simulationPeriodic() {
    }

    public double getAverageEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void arcadeDrive(double throttle, double steering) {
        differentialDrive.arcadeDrive(throttle, steering);
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        frontLeft.setVoltage(leftVolts);
        frontRight.setVoltage(rightVolts);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void resetHeading() {
        LOG.info("Reseting Heading...");
        ahrs.reset();
    }

    public double getHeading() {
        double angle = -ahrs.getYaw();
        // FIXME - NavX on Cookie Monster reports reversed angles - CHECK ON OSCAR!
        LOG.info("NavX Heading: {}", angle);
        return angle;
    }

    public void stopDriving() {
        tankDrive(0, 0);
    }

    public double getLeftEncoderPosition() {
        LOG.trace("LeftEncoderPosition: {}", frontLeft.getSelectedSensorPosition());
        return frontLeft.getSelectedSensorPosition();
    }

    public double getLeftEncoderDistance() {
        LOG.trace("LeftEncoderDistance: {}", getLeftEncoderPosition() * DriveConstants.testChassisDistancePerTick);
        return getLeftEncoderPosition() * DriveConstants.testChassisDistancePerTick;
    }

    public double getLeftMetersPerSecond() {
        LOG.trace("LeftMetersPerSecond: {}", frontLeft.getSelectedSensorVelocity());
        return frontLeft.getSelectedSensorVelocity() * DriveConstants.testChassisDistancePerTick * 100 /* ticks / 100ms convert to m/s*/;
    }

    public double getRightEncoderPosition() {
        LOG.trace("RightEncoderPosition: {}", frontRight.getSelectedSensorPosition());
        return frontRight.getSelectedSensorPosition();
    }

    public double getRightEncoderDistance() {
        LOG.trace("RightEncoderDistance: {}", getRightEncoderPosition() * DriveConstants.testChassisDistancePerTick);
        return getRightEncoderPosition() * DriveConstants.testChassisDistancePerTick;
    }

    public double getRightMetersPerSecond() {
        LOG.trace("RightMetersPerSecond: {}", frontRight.getSelectedSensorVelocity());
        return frontRight.getSelectedSensorVelocity() * DriveConstants.testChassisDistancePerTick * 100 /* ticks / 100ms convert to m/s */;
    }

    public void resetEncoders() {
        frontLeft.setSelectedSensorPosition(0);
        frontRight.setSelectedSensorPosition(0);
        backRight.setSelectedSensorPosition(0);
        backLeft.setSelectedSensorPosition(0);
    }

    public void setMaxOutput(double maxOutput) {
        differentialDrive.setMaxOutput(maxOutput);
    }

    public double getTurnRate() {
        return -ahrs.getRate();
    }
}
