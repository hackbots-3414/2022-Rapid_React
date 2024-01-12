package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.robot.OI;
import frc.robot.RobotContainer;

public class RevShooterCommand extends Command {
  Shooter shooter;
  public RevShooterCommand(Shooter shooter) {
    this.shooter = shooter;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
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
