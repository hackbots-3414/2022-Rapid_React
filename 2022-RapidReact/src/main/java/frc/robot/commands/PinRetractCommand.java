package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class PinRetractCommand extends CommandBase {
  private final Climber climber;

  public PinRetractCommand(Climber subsytem) {
    climber = subsytem;
    addRequirements(climber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    climber.pinsRetract();
  }

  @Override
  public void end(boolean interrupted) {
    climber.pinsPush();
  }


  @Override
  public boolean isFinished() {
    return false;
  }
}