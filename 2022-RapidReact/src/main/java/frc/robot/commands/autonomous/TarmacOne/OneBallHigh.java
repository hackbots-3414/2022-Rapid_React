package frc.robot.commands.autonomous.TarmacOne;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.autonomous.WaitBeforeShoot;
import frc.robot.commands.autonomous.WaitCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class OneBallHigh extends SequentialCommandGroup {
    
    public OneBallHigh(Shooter shooter, Drivetrain drivetrain, Belt belt) {
        addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);
        
        addCommands(
            new WaitBeforeShoot(),
            new ShootCommand(belt, shooter, true, 100),
            new WaitCommand(),
            new DriveStraight(drivetrain, -86.5)
        );
    }
}
