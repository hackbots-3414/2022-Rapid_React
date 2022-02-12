package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Drivetrain.class);

    private AHRS ahrs = new AHRS(Port.kMXP);

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

        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
        addChild("DifferentialDrive", differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        frontLeft.setInverted(TalonFXInvertType.Clockwise);
        backLeft.setInverted(TalonFXInvertType.Clockwise);

        backRight.follow(frontRight);
        backLeft.follow(frontLeft);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation
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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void arcadeDrive(double throttle, double steering) {
        differentialDrive.arcadeDrive(throttle, steering);
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
