package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Constants.LEDConstants;
import frc.robot.subsystems.Belt;
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
        // System.out.print("Running");
        // m_lEDFeedback.setColor(Color.kPurple);
        Belt beltSubsystem = RobotContainer.getInstance().m_belt;
       
         if (DriverStation.getMatchTime() <= 30.0) {
            if (m_lEDFeedback.isClimbingActivated()) {
                m_lEDFeedback.setFlash(Color.kGreen, LEDConstants.defaultFlash);
            } else if (m_lEDFeedback.isClimbLineDetected()) {
                m_lEDFeedback.setColor(Color.kGreen);
            } else if (DriverStation.getMatchTime() <= 10.0) {
                m_lEDFeedback.setFlash(Color.kWhite, LEDConstants.defaultFastFlash);
            } else if (DriverStation.getMatchTime() <= 15.0) {
                m_lEDFeedback.setFlash(Color.kWhite, LEDConstants.defaultFlash);
            } else if (DriverStation.getMatchTime() <= 20.0) {
                m_lEDFeedback.setFlash(Color.kWhite, LEDConstants.defaultSlowFlash);
            } else {
                m_lEDFeedback.setColor(Color.kWhite);
            }

        } else if (beltSubsystem.getIRTop() && beltSubsystem.getIRMiddle()) {
            m_lEDFeedback.setFlash(Color.kOrange, LEDConstants.defaultFlash);
        } else if (beltSubsystem.getIRTop() && !beltSubsystem.getIRMiddle()) {
            m_lEDFeedback.setColor(Color.kOrange);
        } else {
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
