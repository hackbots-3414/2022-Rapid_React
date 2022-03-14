package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Belt;

public class RunShoot extends CommandBase {
  private final Belt m_belt;
  private final Shooter m_shooter;
  private final boolean isHigh;
  private final int shooterTimer;
  private long topIRTimer;

  public RunShoot(Belt subsystem, Shooter shooter, boolean isHigh, int shooterTimer) {
    m_belt = subsystem;
    m_shooter = shooter;
    this.isHigh = isHigh;
    this.shooterTimer = shooterTimer;
    addRequirements(m_shooter);
    addRequirements(m_belt);

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

      } else {
        m_belt.stopAllMotors();
      }
    } else {
      m_shooter.shootLow();
      if (m_shooter.lowAtSpeed()) {

        m_belt.startMotorTop(Constants.BeltConstants.topMotorSpeedShooter);

      } else {
        m_belt.stopAllMotors();
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
  }

  @Override
  public boolean isFinished() {
    return (System.currentTimeMillis() - topIRTimer > shooterTimer);
  }
}
