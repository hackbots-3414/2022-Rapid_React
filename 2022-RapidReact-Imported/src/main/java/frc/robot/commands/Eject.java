package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Belt;

public class Eject extends Command {

  final Belt m_belt;

  public Eject(Belt belt) {
      addRequirements(belt);
      m_belt = belt;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_belt.eject(Constants.BeltConstants.ejectSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    m_belt.stopAllMotors();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
