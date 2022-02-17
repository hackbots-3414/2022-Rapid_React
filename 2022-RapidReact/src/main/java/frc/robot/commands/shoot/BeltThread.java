package frc.robot.commands.shoot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Belt;

public class BeltThread extends SequentialCommandGroup {
    public BeltThread(Belt belt) {
        addCommands(new BeltSpinUp(belt), new BeltHold(belt));
    }
}
