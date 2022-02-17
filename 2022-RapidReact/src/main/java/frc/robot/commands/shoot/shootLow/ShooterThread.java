package frc.robot.commands.shoot.shootLow;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;

public class ShooterThread extends SequentialCommandGroup {
    public ShooterThread(Shooter shooter) {
        addCommands(new SpinUp(shooter), new Hold(shooter));
    }
}
