package frc.robot.commands.autonomous.tarmacOne;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ThreeBall extends SequentialCommandGroup {
    public ThreeBall(Drivetrain drivetrain, Belt belt, Shooter shooter) {    

        addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);

        addCommands(
            new ShootCommand(belt, shooter, 1, 200),
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                        new DriveStraight(drivetrain, -46),
                        new Turn(drivetrain, -23),
                        new DriveStraight(drivetrain, -13), // changed for comp; used to be 20
                        new Turn(drivetrain, 105), // 112
                        new DriveStraight(drivetrain, -70),
                        new Turn(drivetrain, -23),
                        new DriveStraight(drivetrain, 92), // changed for comp; used to be 75
                        new Turn(drivetrain, -27, 0.3), // -13
                        new DriveStraight(drivetrain, 10, 0.55)),
                new BeltCommand(belt).withTimeout(10)),
            new ShootCommand(belt, shooter, 1, 3000)
        );
    }
}
