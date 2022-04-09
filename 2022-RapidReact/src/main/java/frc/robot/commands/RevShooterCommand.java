package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.OI;

public class RevShooterCommand extends CommandBase {
  Shooter shooter;
  public RevShooterCommand(Shooter shooter) {
    this.shooter = shooter;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    System.out.print(OI.getRightTriggerRaw());
    if (OI.getRightTriggerRaw() >= 0.50) {
    shooter.shootHigh();
    }
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  @Override
  public boolean isFinished() {
    if (OI.getRightTriggerRaw() <= 0.49) {
      return true;
    }
    else {
      return false;
    }
  }
}
