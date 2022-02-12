package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class BeltCommand extends CommandBase {

    final Belt m_belt;

    boolean isRunning;
    double output;

    public BeltCommand(Belt belt, double output) {
        addRequirements(belt);
        m_belt = belt;
        this.output = output;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        isRunning = true;
        m_belt.setBeltSpeed(output);
    }

    @Override
    public void end(boolean interrupted) {
        isRunning = false;
        m_belt.setBeltSpeed(0.0);
        m_belt.setConveyorSensorfront(false);
        m_belt.setConveyorSensorback(false);
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return !isRunning;
    }
}
