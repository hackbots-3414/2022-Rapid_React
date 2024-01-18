package frc.robot.commands.autonomous;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class shootFarBackup extends SequentialCommandGroup {
  public shootFarBackup(Shooter shooter, Drivetrain drivetrain, Belt belt) {
    addRequirements(shooter);
    addRequirements(drivetrain);
    addRequirements(belt);

    addCommands(
        new ShootCommand(belt, shooter, 3, 100),
        new WaitCommand(),
        new Turn(drivetrain, -10),
        new ParallelCommandGroup(
            new DriveStraight(drivetrain, -50),
            new BeltCommand(belt).withTimeout(5)),
        new Turn(drivetrain, -60),
        new ShootCommand(belt, shooter, 3, 100),
        new Turn(drivetrain, 60));
  }
}