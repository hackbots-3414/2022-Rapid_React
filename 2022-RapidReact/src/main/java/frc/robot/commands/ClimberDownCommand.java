package frc.robot.commands;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class ClimberDownCommand extends CommandBase {
    private final Climber m_climber;
    private long shortDelay;


    public ClimberDownCommand(Climber subsystem) {
        m_climber = subsystem;
       // addRequirements(m_climber);
    }

    @Override
    public void initialize() {
        shortDelay = 0;
    }

    @Override
    public void execute() {
        m_climber.climberDown();
        shortDelay = System.currentTimeMillis();
    }
    

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        // ensure we execute at least once before finishing through this check
        if (shortDelay > 0) {
            return true;
        }
        return false;
    }
}
