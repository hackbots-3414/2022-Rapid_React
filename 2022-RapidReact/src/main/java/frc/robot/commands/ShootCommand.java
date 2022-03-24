package frc.robot.commands;

import org.ejml.dense.block.MatrixOps_MT_DDRB;

import ch.qos.logback.classic.pattern.RootCauseFirstThrowableProxyConverter;
import edu.wpi.first.hal.simulation.RoboRioDataJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends CommandBase {

    private final Belt m_belt;
    private final Shooter m_shooter;
    //private final boolean isHigh;
    private final int shooterTimer;
    private long topIRTimer;
    private int shootTypes;
    public ShootCommand(Belt subsystem, Shooter shooter, int shootTypes, int shooterTimer) {
        m_belt = subsystem;
        m_shooter = shooter;
        this.shootTypes = shootTypes;
        this.shooterTimer = shooterTimer;
    }

    @Override
    public void initialize() {
        topIRTimer = System.currentTimeMillis();
        RobotContainer.getInstance().m_drivetrain.requestCurrentLimit(true);
    }

    @Override
    public void execute() {

        switch (shootTypes) {
            case 1:

                m_shooter.shootHigh();
                if (m_shooter.highAtSpeed()) {
                    m_belt.startMotorTop(Constants.BeltConstants.topMotorSpeedShooter);
                    m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedShooter);
                    m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedShooter);
                } else {
                    m_belt.stopAllMotors();
                }
            break;  
            case 2:
            m_shooter.shootLow();
            if (m_shooter.lowAtSpeed()) {
                m_belt.startMotorTop(Constants.BeltConstants.topMotorSpeedShooter);
                m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedShooter);
                m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedShooter);
            } else {
                m_belt.stopAllMotors();
            }
            break;
            case 3:
            m_shooter.shootFar();
            if (m_shooter.farAtSpeed()) {
                m_belt.startMotorTop(Constants.BeltConstants.topMotorSpeedShooter);
                m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedShooter);
                m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedShooter);
            } else {
                m_belt.stopAllMotors();
            }


        }
        if (m_belt.getIRTop()) {
            topIRTimer = System.currentTimeMillis();
        }

    }

    @Override
    public void end(boolean interrupted) {
        m_belt.stopAllMotors();
        m_shooter.stop();
        RobotContainer.getInstance().m_drivetrain.requestCurrentLimit(false);
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - topIRTimer > shooterTimer);
        // return !m_belt.getIRBottom() && !m_belt.getIRTop();
    }
}
