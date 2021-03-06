package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveStraight extends CommandBase {

    private double speed;
    private double distance;
    private final double requestedDistance;

    private final Drivetrain m_drivetrain;

    public DriveStraight(Drivetrain subsystem, double distance) {
        this.distance = distance;
        this.speed = 0.6;
        this.requestedDistance = distance;

        m_drivetrain = subsystem;
    }

    public DriveStraight(Drivetrain subsystem, double distance, double speed) {
        this.distance = distance;
        this.speed = speed;
        this.requestedDistance = distance;

        m_drivetrain = subsystem;
    }

    @Override
    public void initialize() {
        this.distance = requestedDistance;
        m_drivetrain.arcadeDrive(0, 0);
        m_drivetrain.resetEncoders();

        this.speed = Math.copySign(this.speed, this.distance);
        this.distance = Math.abs(this.distance / Constants.RobotConstants.kInchesPerTick); // converts from inches to motor ticks (wheel diameter 6.432 inches) (old 0.00083101561761)
    }

    @Override
    public void execute() {
        m_drivetrain.arcadeDrive(speed, 0);
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrain.arcadeDrive(speed * -1, 0);
        Timer.delay(0.01);
        m_drivetrain.arcadeDrive(0, 0);
        m_drivetrain.resetEncoders();
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(m_drivetrain.getAverageEncoderPosition()) <= this.distance) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
