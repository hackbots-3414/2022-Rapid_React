package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Belt;

public class IntakeBeltParallel extends ParallelCommandGroup {
  public IntakeBeltParallel(Intake intake, Belt belt) {
    super(new RunIntake(intake), new BeltCommand(belt));
  }
}