package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;

public class ShootHighSequence extends SequentialCommandGroup {

    private final Shooter m_shooter;
    
    public ShootHighSequence(Shooter shooter) {
        addCommands(new ShootHighSpinUp(shooter));

        this.m_shooter = shooter;
    }
}
