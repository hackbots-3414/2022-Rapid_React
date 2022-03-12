package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends CommandBase {

    private final Belt m_belt;
    private final Shooter m_shooter;
    private final boolean isHigh;
    private final int shooterTimer;
    private long topIRTimer;

    public ShootCommand(Belt subsystem, Shooter shooter, boolean isHigh, int shooterTimer) {
        m_belt = subsystem;
        m_shooter = shooter;
        this.isHigh = isHigh;
        this.shooterTimer = shooterTimer;
    }

    @Override
    public void initialize() {
        topIRTimer = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        if (m_belt.getIRTop()) {
            topIRTimer = System.currentTimeMillis();
        }
        if (isHigh) {
            m_shooter.shootHigh();
            if (m_shooter.highAtSpeed()) {
                m_belt.startMotorTop(Constants.BeltConstants.topMotorSpeedShooter);
                m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedShooter);
                m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedShooter);
            } else {
                m_belt.stopAllMotors();
            }
        } else {
            m_shooter.shootLow();
            if (m_shooter.lowAtSpeed()) {
                m_belt.startMotorTop(Constants.BeltConstants.topMotorSpeedShooter);
                m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedShooter);
                m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedShooter);
            } else {
                m_belt.stopAllMotors();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_belt.stopAllMotors();
        m_shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - topIRTimer > shooterTimer);
    }
}
