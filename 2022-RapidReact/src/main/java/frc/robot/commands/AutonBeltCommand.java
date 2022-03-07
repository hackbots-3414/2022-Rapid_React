package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Belt;

public class AutonBeltCommand extends BeltCommand {
    public AutonBeltCommand(Belt belt) {
        super(belt);
    }

    @Override
    public void execute() {
        System.out.print("Running");
        m_belt.stopMotorTop();
        m_belt.startMotorBottom(Constants.BeltConstants.motorSpeedTransfer);
        m_belt.startMotorMiddle(Constants.BeltConstants.motorSpeedTransfer);
        m_belt.startIntakeMotor(Constants.BeltConstants.intakeSpeed);
        m_belt.goDown();
    }

    @Override
    public boolean isFinished() {
        // Robot has two balls in position
        return false;
    }
}
