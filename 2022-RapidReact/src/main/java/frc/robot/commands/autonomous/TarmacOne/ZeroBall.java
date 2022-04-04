package frc.robot.commands.autonomous.tarmacOne;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.autonomous.WaitCommand;
import frc.robot.subsystems.Drivetrain;

public class ZeroBall extends SequentialCommandGroup {

    public ZeroBall(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        
        addCommands(
            new WaitCommand(),
            new DriveStraight(drivetrain, -86.5)
        );
    }
}
