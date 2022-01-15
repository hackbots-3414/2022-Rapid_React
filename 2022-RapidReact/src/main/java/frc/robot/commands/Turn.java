// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.subsystems.Drivetrain;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Turn extends CommandBase {
    private static final Logger LOG = LoggerFactory.getLogger(Turn.class);
    private double startHeading;
    private double targetHeading;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
        private final Drivetrain m_drivetrain;
    private double m_degrees;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS


    public Turn(double degrees, Drivetrain subsystem) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_degrees = degrees;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        m_drivetrain = subsystem;
        addRequirements(m_drivetrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    public void turnLeft (double speed) {
        m_drivetrain.tankDrive(-speed, speed);
    }

    public void turnRight (double speed) {
        m_drivetrain.tankDrive(speed, -speed);
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        startHeading = m_drivetrain.getHeading();
        LOG.info("StartHeading = {}", startHeading);
        targetHeading = startHeading+m_degrees;
        LOG.info("TargetHeading = {}", targetHeading);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_degrees > 0) {
            turnRight(0.20);
        } else if (m_degrees < 0) {
            turnLeft(0.20);
        } else {
            LOG.info("0 Degrees Passed");
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        LOG.debug("Turn Stopped");
        m_drivetrain.stopDriving();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        LOG.debug("In IsFinished");
        LOG.debug("m_degrees = {}, StartHeading = {}, TargetHeading = {}", m_degrees, startHeading, targetHeading);
        if (m_degrees > 0) {
            
            if (startHeading > targetHeading) {
                LOG.debug("Done Turning Right");
                return true;
            }
        } else if (m_degrees < 0) {
            if (startHeading < targetHeading) {
                LOG.debug("Done Turning Left");
                return true;
            }
        } else {
            LOG.debug("0 Degrees Passed");
            return true;
        }
        LOG.debug("impossible - in isFinished");
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
