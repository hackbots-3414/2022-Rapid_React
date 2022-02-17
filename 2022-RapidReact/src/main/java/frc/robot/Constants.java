package frc.robot;

import edu.wpi.first.wpilibj2.command.button.POVButton;

public class Constants {


    public static final class DriveConstants {
        public static final int kLeftMotorFrontPort = 10;
        public static final int kLeftMotorRearPort = 11;
        public static final int kRightMotorFrontPort = 13;
        public static final int kRightMotorRearPort = 14;
    }

    public static final class TransportConstants {
        public static final int transportMotor1 = 20;
        public static final int transportMotor2 = 21;
        public static final int transportMotor3 = 22;
    }

    public static final class IntakeConstants {
        public static final int intakeMotor = 30;
        public static final double intakeMotorSpeed = 0.35;
        public static final int solenoidChannel = 0;

    }

    public static final class ShooterConstants {
        public static final int shooterMotor1 = 40;
        public static final int shooterMotor2 = 41;

        public static final double highShootVelocity = 6500;
        public static final double lowShootVelocity = 5000;

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
        public static final double Motorspeed = 100.0;
    }

    public static final class LED_Constants {
        public static final double defaultFlash = 1.0;
        public static final double defaultFastFlash = 0.5;
        public static final double defaultSlowFlash = 2;
    }

    public static final class ClimberConstants {

        public static final int climbUpAngle = 0;
        public static final int climbDownAngle = 180;

        public static final int climberSolenoidChannel = 1;

        
        //Should be fixed later. Need instance of XboxController in Contstants to work
        // final POVButton climberUpButton = new POVButton(operatorPad, 0); 
        // final POVButton climberDownButton = new POVButton(operatorPad, 180);
    }


}
