package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class ClimberUpCommand extends CommandBase {
    private final Climber m_climber;
    private long shortDelay;

    public ClimberUpCommand(Climber subsystem) {
        m_climber = subsystem;
        //addRequirements(m_climber);
    }

    @Override
    public void initialize() {
        RobotContainer.getInstance().m_lEDFeedback.setClimbingActivated(true);
        shortDelay = 0;
    }

    @Override
    public void execute() {
        m_climber.climberUp();
        shortDelay = System.currentTimeMillis();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        if (shortDelay > 0) {
            return true;
        }
        return false;
    }
}
