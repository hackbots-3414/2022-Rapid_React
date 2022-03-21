package frc.robot.commands.autonomous.TarmacOne;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.BeltCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ThreeBall extends ParallelCommandGroup {
    
    public ThreeBall(Drivetrain drivetrain, Belt belt, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);
        
        addCommands(
            new ThreeBallMovementShooting(drivetrain, belt, shooter),
            new BeltCommand(belt)
        );
    }
}