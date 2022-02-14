package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;

public class ShootLowCommand extends SequentialCommandGroup {

    private final Shooter m_shooter;

    public ShootLowCommand(Shooter subsystem) {
        m_shooter = subsystem;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooter.shootLow();
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
