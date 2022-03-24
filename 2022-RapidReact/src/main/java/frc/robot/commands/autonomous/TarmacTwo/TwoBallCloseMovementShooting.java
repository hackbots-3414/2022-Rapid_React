package frc.robot.commands.autonomous.tarmacTwo;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class TwoBallCloseMovementShooting extends SequentialCommandGroup {

    public TwoBallCloseMovementShooting(Drivetrain drivetrain, Belt belt, Shooter shooter) {
        addCommands(
            new ShootCommand(belt, shooter, 1, 100),
            new DriveStraight(drivetrain, -50),
            new Turn(drivetrain, 22),
            new DriveStraight(drivetrain, -20),
            new DriveStraight(drivetrain, 20),
            new Turn(drivetrain, -22),
            new DriveStraight(drivetrain, -50),
            new ShootCommand(belt, shooter, 1, 100)
        );
    }
}

