package frc.robot.commands.shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Shooter;

public class BeltHold extends CommandBase {

    private final Belt m_belt;

    public BeltHold(Belt subsystem) {
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
        m_belt.stopAllMotors();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
