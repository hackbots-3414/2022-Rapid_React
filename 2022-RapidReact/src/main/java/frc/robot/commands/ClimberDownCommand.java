package frc.robot.commands;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class ClimberDownCommand extends CommandBase {
    private final Climber m_climber;
    DigitalInput limitSwitch = new DigitalInput(ClimberConstants.climbMagneticLimitPort);


    public ClimberDownCommand(Climber subsystem) {
        m_climber = subsystem;
        addRequirements(m_climber);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        m_climber.climberDown();

            if (limitSwitch.get()) {
                
                // We are going up and top limit is tripped so stop
            } else {
                // We are going up but top limit is not tripped so go at commanded speed
                m_climber.climberUp();
            }
           }
    

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
