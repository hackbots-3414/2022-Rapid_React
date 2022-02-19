package frc.robot.commands.shoot.shootLow;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SpinUp extends CommandBase {

    private final Shooter m_shooter;

    public SpinUp(Shooter subsystem) {
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
        if (interrupted) m_shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return m_shooter.lowAtSpeed();
    }
}
