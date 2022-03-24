package frc.robot.commands.autonomous.tarmacTwo;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BeltCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class TwoBallClose extends SequentialCommandGroup {

    public TwoBallClose(Drivetrain drivetrain, Belt belt, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);
        
        addCommands(
            new TwoBallCloseMovementShooting(drivetrain, belt, shooter),
            new BeltCommand(belt)
        );
    }
}

