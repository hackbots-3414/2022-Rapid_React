package frc.robot.commands.shoot.shootLow;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.shoot.BeltThread;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Shooter;

public class ShootLow extends ParallelCommandGroup {
    public ShootLow(Shooter shooter, Belt belt) {
        addCommands(new ShooterThread(shooter), new BeltThread(belt));
    }
}
