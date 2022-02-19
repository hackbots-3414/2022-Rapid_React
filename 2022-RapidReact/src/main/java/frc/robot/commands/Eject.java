package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class Eject extends CommandBase {

  final Belt m_belt;

  public Eject(Belt belt) {
      addRequirements(belt);
      m_belt = belt;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_belt.eject();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
