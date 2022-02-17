package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class BeltCommand extends CommandBase {

    final Belt m_belt;

    boolean isRunning;

    public BeltCommand(Belt belt) {
        addRequirements(belt);
        m_belt = belt;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        isRunning = true;
        m_belt.go();
    }

    @Override
    public void end(boolean interrupted) {
        isRunning = false;
        m_belt.stop();
        m_belt.setConveyorSensorfront(false);
        m_belt.setConveyorSensorback(false);
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
