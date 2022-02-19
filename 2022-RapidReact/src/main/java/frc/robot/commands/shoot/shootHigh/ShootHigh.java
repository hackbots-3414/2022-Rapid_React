package frc.robot.commands.shoot.shootHigh;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.shoot.BeltThread;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Shooter;

public class ShootHigh extends ParallelCommandGroup {
    public ShootHigh(Shooter shooter, Belt belt) {
        addCommands(new ShooterThread(shooter), new BeltThread(belt));
    }
}
