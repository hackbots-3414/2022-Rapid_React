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
    addRequirements(shooter);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {





















































































































































































































    
    SmartDashboard.putNumber("Shooter Velocity", shooter.getAverageVelocity());

    if (RobotContainer.getInstance().getoperatorPad().getRawAxis(3) >= 0.50) {
    shooter.shootHigh();
    }
    else {
      shooter.stop();
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
      return false;
    }
}
