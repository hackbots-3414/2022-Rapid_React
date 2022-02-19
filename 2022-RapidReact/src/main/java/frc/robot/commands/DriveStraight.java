package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveStraight extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(DriveStraight.class);

    private double speed;
    private double distance;
    private final double requestedDistance;

    private final Drivetrain m_drivetrain;

    public DriveStraight(Drivetrain subsystem, double distance) {
        this.distance = distance;
        this.requestedDistance = distance;
        LOG.info("distance: {}", distance);

        m_drivetrain = subsystem;
        addRequirements(m_drivetrain);
    }

    @Override
    public void initialize() {
        LOG.info("driveStraigt initialized");
        this.distance = requestedDistance;
        this.speed = 0;
        m_drivetrain.arcadeDrive(0, 0);
        m_drivetrain.resetEncoders();

        LOG.info("distance, inches: {}", this.distance);

        this.speed = Math.copySign(0.3, this.distance);
        LOG.info("speed: {}", this.speed);
        this.distance = Math.abs(this.distance / 0.001198047515388888); // converts from inches to motor ticks (wheel diameter 6.432 inches)
        LOG.info("distance, ticks: {}", this.distance);
    }

    @Override
    public void execute() {
        LOG.trace("driveStraigt executed");
        m_drivetrain.arcadeDrive(speed, 0);
        LOG.info("Average Encoder: {}\nLeft Encoder: {}\nRight Encoder: {}", m_drivetrain.getAverageEncoderPosition(), m_drivetrain.getLeftEncoderPosition(), m_drivetrain.getRightEncoderPosition());
        if (m_drivetrain.getAverageEncoderPosition() > this.distance){
            isFinished();
        }
        LOG.trace("driveStraigt executed 2");
    }

    @Override
    public void end(boolean interrupted) {
        LOG.info("driveStraigt ended");
        m_drivetrain.arcadeDrive(0, 0);
        m_drivetrain.resetEncoders();
    }

    @Override
    public boolean isFinished() {
        LOG.trace("Average Encoder: {}\nLeft Encoder: {}\nRight Encoder: {}", m_drivetrain.getAverageEncoderPosition(), m_drivetrain.getLeftEncoderPosition(), m_drivetrain.getRightEncoderPosition());
        if (Math.abs(m_drivetrain.getAverageEncoderPosition()) <= this.distance) {
            LOG.trace("driveStraigt not finished");
            return false;
        } else {
            LOG.trace("driveStraigt finished");
            return true;
        }
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
