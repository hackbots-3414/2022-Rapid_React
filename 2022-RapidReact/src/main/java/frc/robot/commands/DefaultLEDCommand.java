package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.MatchType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.LEDConstants;
import frc.robot.Constants.PressureConstants;
import frc.robot.RobotContainer;
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


        // SmartDashboard.putNumber("Air Pressure", m_lEDFeedback.checkPressure());
        
        // System.out.print("Running");
        // m_lEDFeedback.setColor(Color.kPurple);
    /* if (m_lEDFeedback.checkPressure() < PressureConstants.pressureValue){
         m_lEDFeedback.setColor(Color.kYellow);

    }   else */ if (m_lEDFeedback.isClimbingActivated()) {
            m_lEDFeedback.setFlash(Color.kGreen, LEDConstants.defaultFlash);
        } else if (DriverStation.getMatchTime() <= 30.0 && DriverStation.isTeleop() && (DriverStation.getMatchType()!= MatchType.None)) {
            // Checks if we're in a Practice, Qualification or Final match to use end game times to flash the LEDs
            if (m_lEDFeedback.isClimbLineDetected()) {
                m_lEDFeedback.setColor(Color.kGreen);
            } else if (DriverStation.getMatchTime() <= 15.0 ) {
                m_lEDFeedback.setFlash(Color.kRed, LEDConstants.defaultFastFlash);
            } else if (DriverStation.getMatchTime() <= 30.0 ) {
                m_lEDFeedback.setColor(Color.kRed);
            } else {
                m_lEDFeedback.setColor(Color.kWhite);
            }
        } else if (RobotContainer.getInstance().m_belt.getIRTop() && RobotContainer.getInstance().m_belt.getIRBottom()) {
            // Check if Robot has two balls and flash the LEDs
            m_lEDFeedback.setFlash(Color.kOrange, LEDConstants.defaultFlash);
        } else if (RobotContainer.getInstance().m_belt.getIRTop() || RobotContainer.getInstance().m_belt.getIRBottom()) {
            // Check if Robot has one ball and set LEDs to solid color
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
