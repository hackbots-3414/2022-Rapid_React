package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Belt;

public class RunBelt extends CommandBase {

    final Belt m_belt;

    public RunBelt(Belt belt) {
        addRequirements(belt);
        m_belt = belt;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        //ball not in top and bottom
        if (!m_belt.getIRTop() && !m_belt.getIRBottom()) {
            m_belt.startBeltMotors(Constants.BeltConstants.motorSpeedTransfer, Constants.BeltConstants.topMotorSpeedTransfer);
            m_belt.goDown();
        }
        // Robot has one ball in the top position
        if (m_belt.getIRTop() && !m_belt.getIRBottom()) {
            m_belt.stopMotorTop();
            m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedTransfer);
            m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedTransfer);
            m_belt.goDown();
        }
        //ball not in top but in bottom
        if (!m_belt.getIRTop() && m_belt.getIRBottom()) {
            m_belt.startBeltMotors(Constants.BeltConstants.motorSpeedTransfer, Constants.BeltConstants.topMotorSpeedTransfer);
            m_belt.goDown();
        }
    }

    @Override
    public boolean isFinished() {
        // Robot has two balls in position
        return m_belt.getIRTop() && m_belt.getIRBottom();
    }

    public void end(boolean interrupted) {
        m_belt.stopAllMotors();
        m_belt.goUp();
    }
}

