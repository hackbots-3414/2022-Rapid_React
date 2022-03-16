package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class WaitBackupSequential extends SequentialCommandGroup {

    public WaitBackupSequential(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        
        addCommands(
            new WaitCommand(),
            new DriveStraight(drivetrain, -86.5)
        );
    }
}
