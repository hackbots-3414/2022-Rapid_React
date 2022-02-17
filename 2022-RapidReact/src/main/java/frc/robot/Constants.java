package frc.robot;

public class Constants {

    public static final class DriveConstants {
        public static final int kLeftMotorFrontPort = 10;
        public static final int kLeftMotorRearPort = 11;
        public static final int kRightMotorFrontPort = 13;
        public static final int kRightMotorRearPort = 14;
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

    public static final class BeltConstants {
        public static final int topMotor = 22;
        public static final int middleMotor = 21;
        public static final int bottomMotor = 20;
        public static final int intakeMotor = 30;
        public static final int solenoidChannel = 9;
        public static final double Motorspeed = 0.1;
    }
}
