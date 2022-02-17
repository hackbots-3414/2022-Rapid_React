package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {

    Intake intake;

    public RunIntake(Intake m_intake) {
        this.intake = m_intake;
        addRequirements(m_intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        intake.goDown();
        intake.setSpeed(Constants.IntakeConstants.intakeMotorSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
        intake.goUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
