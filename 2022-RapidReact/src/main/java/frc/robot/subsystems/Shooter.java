// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import frc.robot.Constants;
//import frc.robot.Constants.Shooter;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Shooter extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(Shooter.class);

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonFX rightMotor;
    private WPI_TalonFX leftMotor;

    private TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    private SlotConfiguration PIDConfig = new SlotConfiguration();
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
    *
    */
    public Shooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS


        rightMotor = new WPI_TalonFX(Constants.Shooter.shooterMotor1);
        leftMotor = new WPI_TalonFX(Constants.Shooter.shooterMotor2);

 


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftMotor.setInverted(TalonFXInvertType.Clockwise);
        rightMotor.setInverted(TalonFXInvertType.CounterClockwise);

        PIDConfig.integralZone = Constants.Shooter.integralZone;
        PIDConfig.kD = Constants.Shooter.kD;
        PIDConfig.kF = Constants.Shooter.kF;
        PIDConfig.kI = Constants.Shooter.kI;
        PIDConfig.kP = Constants.Shooter.kP;
        motorConfig.slot0 = PIDConfig;

        leftMotor.configAllSettings(motorConfig);
        rightMotor.follow(leftMotor);
        LOG.error("got here");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shoot() {
        //6500 works well for high shot on 02/05/2022
        //5000 works well for low shot on 02/05/2022
        //shooter degree at 4.5 degrees on 2/5/22
        leftMotor.set(ControlMode.Velocity, 6500);
        
    }

    public void shootLow() {
        //6500 works well for high shot on 02/05/2022
        //5000 works well for low shot on 02/05/2022
        //shooter degree at 4.5 degrees on 2/5/22
        leftMotor.set(ControlMode.Velocity, 5000);
        
    }

    public void stop() {
        leftMotor.set(0.0);
    }
}