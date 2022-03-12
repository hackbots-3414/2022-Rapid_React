package frc.robot.commands.autonomus;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class TwoBall extends SequentialCommandGroup {

    public TwoBall(Drivetrain drivetrain, Belt belt, Shooter shooter) {
        addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);

        addCommands(
            new DriveStraight(drivetrain, 26),
            new BeltCommand(belt).withTimeout(2.5),
            new DriveStraight(drivetrain, -51),
            new Turn(drivetrain, 8),
            new DriveStraight(drivetrain, -20),
            new ShootCommand(belt, shooter, true, 3000)
        );
    }
}

