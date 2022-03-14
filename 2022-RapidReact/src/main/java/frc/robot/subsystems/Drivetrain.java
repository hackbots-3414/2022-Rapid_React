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

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.RobotPartsConstants;

public class Drivetrain extends SubsystemBase {
    private static final Logger LOG = LoggerFactory.getLogger(Drivetrain.class);

    private boolean controlsReversed = false;

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
        configureTalonFX(frontLeft);
        configureTalonFX(backLeft);
        configureTalonFX(frontRight);
        configureTalonFX(backRight);
        frontLeft.setInverted(TalonFXInvertType.CounterClockwise);
        backLeft.setInverted(TalonFXInvertType.CounterClockwise);
        frontRight.setInverted(TalonFXInvertType.Clockwise);
        backRight.setInverted(TalonFXInvertType.Clockwise);
        // frontLeft.setSensorPhase(false);
        // backLeft.setSensorPhase(false);
        // frontRight.setSensorPhase(true);
        // backRight.setSensorPhase(true);
        backRight.follow(frontRight);
        backLeft.follow(frontLeft);

        resetEncoders();
        m_odometry = new DifferentialDriveOdometry(ahrs.getRotation2d());

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0); 
    }

    /**
     * Set the basic settings we want across all drivetrain motors
     * @param motor motor to configure
     * @return motor
     */
    private WPI_TalonFX configureTalonFX(WPI_TalonFX motor) {
        motor.configFactoryDefault();
        motor.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_10Ms);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
        motor.configOpenloopRamp(DriveConstants.voltageRampRate);
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, DriveConstants.driveCurrentLimit,DriveConstants.driveCurrentLimit, DriveConstants.triggerThresholdTime));
        return motor;
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

    public void setControlsReversed(boolean controlsReversed) {
        this.controlsReversed = controlsReversed;
    }

    public boolean isControlsReversed() {
        return controlsReversed;
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
        LOG.trace("Throttle = {}, Steering = {}, ControlsReversed = {}", throttle, steering, controlsReversed);
        if(controlsReversed){
            differentialDrive.arcadeDrive(throttle, steering);
        }
        else {
            differentialDrive.arcadeDrive(-throttle, steering);
        }
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        SmartDashboard.putNumber("Right Front", getRightEncoderPosition());
        // SmartDashboard.putNumber("Right Back", backRight.getSelectedSensorPosition(1));
        SmartDashboard.putNumber("Left Front", getLeftEncoderPosition());
        // SmartDashboard.putNumber("Left Back", backLeft.getSelectedSensorPosition());
        frontLeft.setVoltage(leftVolts);
        frontRight.setVoltage(rightVolts);
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
        LOG.trace("LeftEncoderDistance: {}", getLeftEncoderPosition() * RobotPartsConstants.distancePerTick);
        return getLeftEncoderPosition() * RobotPartsConstants.distancePerTick;
    }

    public double getLeftMetersPerSecond() {
        LOG.trace("LeftMetersPerSecond: {}", frontLeft.getSelectedSensorVelocity() * RobotPartsConstants.distancePerTick * 10);
        return frontLeft.getSelectedSensorVelocity() * RobotPartsConstants.distancePerTick * 10 /* ticks / 100ms convert to m/s*/;
    }

    public double getRightEncoderPosition() {
        LOG.trace("RightEncoderPosition: {}", frontRight.getSelectedSensorPosition());
        return frontRight.getSelectedSensorPosition();
    }

    public double getRightEncoderDistance() {
        LOG.trace("RightEncoderDistance: {}", getRightEncoderPosition() * RobotPartsConstants.distancePerTick);
        return getRightEncoderPosition() * RobotPartsConstants.distancePerTick;
    }

    public double getRightMetersPerSecond() {
        LOG.trace("RightMetersPerSecond: {}", frontRight.getSelectedSensorVelocity() * RobotPartsConstants.distancePerTick * 10);
        return frontRight.getSelectedSensorVelocity() * RobotPartsConstants.distancePerTick * 10 /* ticks / 100ms convert to m/s */;
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
}
