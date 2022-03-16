package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ShootLowWaitBackup extends SequentialCommandGroup {
    
    public ShootLowWaitBackup(Shooter shooter, Drivetrain drivetrain, Belt belt) {
        addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);
        
        addCommands(
            new WaitBeforeShoot(), 
            new ShootCommand(belt, shooter, false, 100),
            new WaitCommand(),
            new DriveStraight(drivetrain, -86.5)
        );
    }
}
