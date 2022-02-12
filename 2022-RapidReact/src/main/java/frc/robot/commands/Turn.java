
// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Turn extends CommandBase {
    private static final Logger LOG = LoggerFactory.getLogger(Turn.class);
    private static double TURNSPEED = 0.65;
    private double startHeading;
    private double targetHeading;
    private double currentHeading;

    private final Drivetrain m_drivetrain;
    private double m_degrees;

    public Turn(double degrees, Drivetrain subsystem) {
        m_degrees = degrees;

        m_drivetrain = subsystem;
        addRequirements(m_drivetrain);

    }

    public void turnLeft() {
        LOG.debug("In turnLeft");
        m_drivetrain.tankDrive(-TURNSPEED, -TURNSPEED);
    }

    public void turnRight() {
        LOG.debug("In turnRight");
        m_drivetrain.tankDrive(TURNSPEED, TURNSPEED);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_drivetrain.resetHeading();
        startHeading = m_drivetrain.getHeading();
        LOG.debug("StartHeading = {}", startHeading);
        targetHeading = m_degrees;
        LOG.debug("TargetHeading = {}", targetHeading);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        LOG.debug("Turn.execute() running");
        if (m_degrees > 0) {
            LOG.debug("Turning Right");
            turnRight();
        } else if (m_degrees < 0) {
            LOG.debug("Turning Left");
            turnLeft();
        } else {
            LOG.debug("0 Degrees Passed");
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        LOG.debug("Turn Stopped, interrupted: {}", interrupted);
        m_drivetrain.stopDriving();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        currentHeading = m_drivetrain.getHeading();
        LOG.debug("In IsFinished");
        LOG.debug("m_degrees = {}, StartHeading = {}, TargetHeading = {}, CurrentHeading = {}", m_degrees, startHeading,
                targetHeading, m_drivetrain.getHeading());
        if (m_degrees > 0) {
            LOG.debug("In Turn/Isfinished/first if statement");
            if (currentHeading > targetHeading) {
                LOG.debug("Done Turning Right");
                return true;
            } else {
                return false;
            }
        } else if (m_degrees < 0) {
            if (currentHeading < targetHeading) {
                LOG.debug("Done Turning Left");
                return true;
            } else {
                return false;
            }
        } else {
            LOG.debug("0 Degrees Passed");
            return true;
        }
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
