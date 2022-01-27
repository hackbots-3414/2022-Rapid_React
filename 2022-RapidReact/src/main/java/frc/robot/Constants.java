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

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {

    private static final Logger LOG = LoggerFactory.getLogger("Constants.java");
    
             public static final class DriveConstants {
              public static final int kLeftMotor1Port = 10;
              public static final int kLeftMotor2Port = 11;
             public static final int kRightMotor1Port = 13;
            public static final int kRightMotor2Port = 14; 
 }

            public static final class Transport {
            
                public static final int transportMotor1 = 20;
              public static final int transportMotor2 = 21;
              public static final int transportMotor3 = 22;


            }
            public static final class Intake {
                public static final int intakeMotor1= 30;
              
            }
            public static final class Shooter { 
                public static final int shooterMotor1 = 40;
                public static final int shooterMotor2 = 41;
            }
}

