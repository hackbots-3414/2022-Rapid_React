package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants {
    public static final class RobotConstants {
        public static final double kTrackWidthMeters = 0.6096;
        public static final double kWheelDiameter = 0.16;
        public static final double kTicks = 2048;
        public static final double kGearRatio = 12; // : 1
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);
        public static final double kDistancePerTick = kWheelDiameter * Math.PI / kTicks / kGearRatio;
    }
    
    public static final class CompControllerConstants {
        public static final double left_x_offset = -0.13379;
        public static final double left_x_max = 0.81982;
        public static final double left_y_offset = 0.02;
        public static final double left_y_max = 0.62793;
        public static final double right_x_offset = 0.05;
        public static final double right_x_max = 0.80713;
        public static final double right_y_offset = 0.03418;
        public static final double right_y_max = 0.85005;
        public static final double r_knob_offset = 0.03371;
    }

    public static final class DevControllerConstants {
        public static final double left_x_offset = -0.05518;
        public static final double left_x_max = 0.83401;
        public static final double left_y_offset = -0.01953;
        public static final double left_y_max = 0.64453;
        public static final double right_x_offset = 0.03711;
        public static final double right_x_max = 0.73144;
        public static final double right_y_offset = 0.01367;
        public static final double right_y_max = 0.87256;
        public static final double r_knob_offset = 0.03371;
    }

    public static final class DriveConstants {
        public static final int kLeftMotorFrontPort = 10; // 10
        public static final int kLeftMotorRearPort = 11;
        public static final int kRightMotorFrontPort = 13;
        public static final int kRightMotorRearPort = 14;
        public static final double voltageRampRate = 0.63;
        public static final double kMaxSpeed = 2.5; // 2
        public static final double driveCurrentLimit = 70.0;
        public static final double triggerThresholdTime = .05;

        public static final double driveLowCurrentLimit = 5.0;

    }

    public static final class PathweaverConstants {
        public static final double ksVolts = 0.61355; // 0.61355 - test chassis, 0.63458 - comp chassis
        public static final double kvVoltSecondsPerMeter = 2.5341; // 2.5341 - test chassis, 2.4941 - comp chassis
        public static final double kaVoltSecondsSquaredPerMeter = 0.1884; // 0.1884 - test chassis, 0.3322 - comp chassis
        public static final double kpDriveVel = 2; // 2.0 - test chassis, 3.3673 - comp chassis
        public static final double kiDriveVel = 0;
        public static final double kdDriveVel = 0;
        public static final double kMaxSpeedMetersPerSecond = 0.5;
        public static final double kMaxAccelerationMetersPerSecondSquared = 1.25;
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

    }

    public static final class TransportConstants {
        public static final int transportMotor1 = 20;
        public static final int transportMotor2 = 21;
        public static final int transportMotor3 = 22;
    }

    public static final class ShooterConstants {
        public static final int shooterMotor1 = 40;
        public static final int shooterMotor2 = 41;
        public static final double highShootVelocity = 6400;
        public static final double shootFarVelocity = 8500;
        public static final double lowShootVelocity = 4100;
        public static final double shootVelocityTolerance = 350;
        public static final double integralZone = 201;
        public static final double kD = 8;
        public static final double kF = 0.045;
        public static final double kI = 0.0005;
        public static final double kP = 0.15;
        public static final int shooterTimer = 100000;
    }

    public static final class BeltConstants {
        public static final int topMotor = 22;
        public static final int middleMotor = 21;
        public static final int bottomMotor = 20;
        public static final int intakeMotor = 30;
        public static final int solenoidChannel = 8;
        public static final double motorSpeedShooter = 1.0; //used for lower two motors (Bottom and Middle)
        public static final double intakeSpeed = 0.8;
        public static final double topMotorSpeedShooter = 0.75;
        public static final double motorSpeedTransfer = 0.75;
        public static final double topMotorSpeedTransfer = 0.3;
        public static final double ejectSpeed = 1.0;

    }

    public static final class LEDConstants {
        public static final double defaultFlash = 500;
        public static final double defaultFastFlash = 250;
        public static final double defaultSlowFlash = 1000;
    }

    public static final class ClimberConstants {
        public static final int climbUpAngle = 0;
        public static final int climbDownAngle = 180;
        public static final int climberSolenoidChannel_1 = 9;
        public static final int climberSolenoidChannel_2 = 10;
        public static final int minLevelTwoClimb = 70;
        public static final int minLevelThreeClimb = 105;
    }

    public static final class PressureConstants {
        public static final double pressureValue = ClimberConstants.minLevelTwoClimb;
    }

    public static final class PowerDistribution {
        public static final int CanID = 2;
    }

}
