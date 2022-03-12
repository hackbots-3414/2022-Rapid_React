package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Turn extends CommandBase {
    private static final Logger LOG = LoggerFactory.getLogger(Turn.class);

    private double turnSpeed = 0.4;
    private double startHeading;
    private double targetHeading;
    private double currentHeading;

    private final Drivetrain m_drivetrain;
    private double m_degrees;

    public Turn(Drivetrain subsystem, double degrees) {
        m_degrees = degrees;
        
        m_drivetrain = subsystem;
    }

    public Turn(Drivetrain subsystem, double degrees, double turnSpeed) {
        this.turnSpeed = turnSpeed;
        m_degrees = degrees;
        m_degrees = degrees;
        
        m_drivetrain = subsystem;
    }

    public void turnLeft() {
        LOG.debug("In turnLeft");
        m_drivetrain.tankDrive(-turnSpeed, turnSpeed);
    }

    public void turnRight() {
        LOG.debug("In turnRight");
        m_drivetrain.tankDrive(turnSpeed, -turnSpeed);
    }

    @Override
    public void initialize() {
        m_drivetrain.resetHeading();
        startHeading = m_drivetrain.getHeading();
        LOG.debug("StartHeading = {}", startHeading);
        targetHeading = m_degrees;
        LOG.debug("TargetHeading = {}", targetHeading);
    }

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

    @Override
    public void end(boolean interrupted) {
        LOG.debug("Turn Stopped, interrupted: {}", interrupted);
        m_drivetrain.stopDriving();
    }

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