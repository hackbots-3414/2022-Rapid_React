package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Belt;

public class BeltCommand extends Command {

    final Belt m_belt;

    public BeltCommand(Belt belt) {
        m_belt = belt;
    }

    @Override
    public void initialize() {
        m_belt.setStopBelt(false);
    }

    @Override
    public void execute() {
        //ball not in top and bottom
        if (!m_belt.getIRTop() && !m_belt.getIRBottom()) {
            m_belt.startAllMotors(Constants.BeltConstants.motorSpeedTransfer, Constants.BeltConstants.topMotorSpeedTransfer, Constants.BeltConstants.intakeSpeed);
            m_belt.goDown();
        }
        // Robot has one ball in the top position
        if (m_belt.getIRTop() && !m_belt.getIRBottom()) {
            m_belt.stopMotorTop();
            m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedTransfer);
            m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedTransfer);
            m_belt.startIntakeMotor(Constants.BeltConstants.intakeSpeed);
            m_belt.goDown();
        }
        //ball not in top but in bottom
        if (!m_belt.getIRTop() && m_belt.getIRBottom()) {
            m_belt.startAllMotors(Constants.BeltConstants.motorSpeedTransfer, Constants.BeltConstants.topMotorSpeedTransfer, Constants.BeltConstants.intakeSpeed);
            m_belt.goDown();
        }
    }

    @Override
    public boolean isFinished() {
        // Robot has two balls in position
        // StopBelts makes it possible to end the command from another part of the code
        if (m_belt.isStopBelts()) {
            return true;
        } else {
            return m_belt.getIRTop() && m_belt.getIRBottom();
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_belt.stopAllMotors();
        m_belt.goUp();
    }
}
