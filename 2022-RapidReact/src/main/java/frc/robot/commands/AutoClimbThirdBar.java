package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;

public class AutoClimbThirdBar extends SequentialCommandGroup {
  public AutoClimbThirdBar(Climber climber) {
    addRequirements(climber);

    addCommands(
      new ClimberDownCommand(climber),
      new LevelThreeClimb(climber),
      new ClimberUpCommand(climber)
      );
  }
}
