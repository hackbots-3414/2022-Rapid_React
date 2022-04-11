package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.OI;
import frc.robot.RobotContainer;

public class RevShooterCommand extends CommandBase {
  Shooter shooter;
  public RevShooterCommand(Shooter shooter) {
    this.shooter = shooter;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    
    SmartDashboard.putNumber("Shooter Velocity", shooter.getAverageVelocity());
    SmartDashboard.putBoolean("Low at speed", shooter.lowAtSpeed());
    SmartDashboard.putBoolean("High at speed", shooter.highAtSpeed());

    shooter.shootHigh();
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  @Override
  public boolean isFinished() {
      return false;
    }
}
