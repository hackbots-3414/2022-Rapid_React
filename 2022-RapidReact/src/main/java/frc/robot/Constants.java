// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be
 * declared globally (i.e. public static). Do not put anything functional in
 * this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {

    public static final Logger LOG = LoggerFactory.getLogger(Constants.class);

    public static final double kTrackWidthMeters = 0.6096;

    public static final double kMotorToWheelGearRatio = 12; // 12:1 m:w

    public static final class DriveConstants {
        public static final int kLeftMotorFrontPort = 10;
        public static final int kLeftMotorRearPort = 11;
        public static final int kRightMotorFrontPort = 13;
        public static final int kRightMotorRearPort = 14;

        public static final double testChassisWheelDiameterInCentimeters = 16;

        public static final double maxDriveVoltage = 10;

        public static final double ksVolts = 0.59223;
        public static final double kvVoltSecondsPerMeter = 2.5071; //0.65842;
        public static final double kaVoltSecondsSquaredPerMeter = 0.16821; //0.042965;

        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);

        public static final double kPDriveVel = 0.39333; 
        public static final double kIDriveVel = 0;
        public static final double kDDriveVel = 0;

        public static final double testChassisDistancePerTick = ((testChassisWheelDiameterInCentimeters / 100) * Math.PI) / 2048 / Constants.kMotorToWheelGearRatio;
    }

    public static final class Transport {
        public static final int transportMotor1 = 20;
        public static final int transportMotor2 = 21;
        public static final int transportMotor3 = 22;
    }

    public static final class Intake {
        public static final int intakeMotor = 30;
        public static final double intakeMotorSpeed = 0.35;
        public static final int solenoidChannel = 0;

    }

    public static final class Shooter {
        public static final int shooterMotor1 = 40;
        public static final int shooterMotor2 = 41;

        public static final double integralZone = 201;
        public static final double kD = 8;
        public static final double kF = 0.045;
        public static final double kI = 0.0005;
        public static final double kP = 0.15;
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedMetersPerSecond = 1;
        public static final double kMaxAccelerationMetersPerSecondSquared = 2.5; //2.5

        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }

    public static final class BeltConstants {
        public static final int topMotor = 50;
        public static final int middleMotor = 51;
        public static final int bottomMotor = 52;
    }

}
