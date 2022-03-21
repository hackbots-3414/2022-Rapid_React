package frc.robot.commands.autonomous.TarmacOne;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ThreeBallMovementShooting extends SequentialCommandGroup {

    public ThreeBallMovementShooting(Drivetrain drivetrain, Belt belt, Shooter shooter) {
        addCommands(
            new ShootCommand(belt, shooter, 1, 200),
            new DriveStraight(drivetrain, -50),
            new Turn(drivetrain, -22),
            new DriveStraight(drivetrain, -17), //changed for comp; used to be 20
            new Turn(drivetrain, 112),
            new DriveStraight(drivetrain, -70),
            new Turn(drivetrain, -25),
            new DriveStraight(drivetrain, 65), //changed for comp; used to be 75
            new Turn(drivetrain, -20), //-13
            new DriveStraight(drivetrain, 12),
            new ShootCommand(belt, shooter, 1, 3000)
        );
    }
}

