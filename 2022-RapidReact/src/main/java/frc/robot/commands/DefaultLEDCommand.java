package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Constants.LED_Constants;
import frc.robot.subsystems.LEDFeedback;

public class DefaultLEDCommand extends CommandBase {

    private final LEDFeedback m_lEDFeedback;

    public DefaultLEDCommand(LEDFeedback subsystem) {
        m_lEDFeedback = subsystem;
        addRequirements(m_lEDFeedback);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.print("Running");
        m_lEDFeedback.setColor(Color.kPurple);

        if (m_lEDFeedback.isClimbingActivated()) {
            m_lEDFeedback.setFlash(Color.kGreen, LED_Constants.defaultFlash);
        } else if (DriverStation.getMatchTime() <= 30.0) {
            if (m_lEDFeedback.isClimbLineDetected()) {
                m_lEDFeedback.setColor(Color.kGreen);
            } else if (DriverStation.getMatchTime() <= 10.0) {
                m_lEDFeedback.setFlash(Color.kWhite, LED_Constants.defaultFastFlash);
            } else if (DriverStation.getMatchTime() <= 15.0) {
                m_lEDFeedback.setFlash(Color.kWhite, LED_Constants.defaultFlash);
            } else if (DriverStation.getMatchTime() <= 20.0) {
                m_lEDFeedback.setFlash(Color.kWhite, LED_Constants.defaultSlowFlash);
            } else {
                m_lEDFeedback.setColor(Color.kWhite);
            }
            
        }
        else if (RobotContainer.getInstance().m_belt.getConveyorState() == 2){
            m_lEDFeedback.setFlash(Color.kOrange, LED_Constants.defaultFlash);
        }
       else if (RobotContainer.getInstance().m_belt.getConveyorState() == 1){
            m_lEDFeedback.setColor(Color.kOrange);
          
        }
        else {
            m_lEDFeedback.setColor(Color.kPurple);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
