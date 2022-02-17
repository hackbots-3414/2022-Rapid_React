package frc.robot.commands.shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class BeltSpinUp extends CommandBase {

    public final Belt m_belt;

    public BeltSpinUp(Belt subsystem) {
        m_belt = subsystem;
        addRequirements(m_belt);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_belt.startAllMotors();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) m_belt.stopAllMotors();
    }

    @Override
    public boolean isFinished() {
        return m_belt.atSpeed();
    }
}
