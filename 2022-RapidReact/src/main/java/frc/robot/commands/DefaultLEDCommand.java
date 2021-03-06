package frc.robot.commands;

import javax.management.loading.MLet;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.LEDFeedback;

public class DefaultLEDCommand extends CommandBase {

    private final LEDFeedback m_lEDFeedback;
    private final Climber m_climber;

    public DefaultLEDCommand(LEDFeedback subsystem, Climber climberSubsystem) {
        m_lEDFeedback = subsystem;
        m_climber = climberSubsystem;
        addRequirements(m_lEDFeedback);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // System.out.print("Running");
        // m_lEDFeedback.setColor(Color.kPurple);
    if (DriverStation.isAutonomous()) {
        m_lEDFeedback.setColor(Color.kPurple);
    }
    else if (m_climber.getAirPressure() < ClimberConstants.minLevelTwoClimb){
         m_lEDFeedback.setColor(Color.kYellow);

    }

    /* else  if (m_lEDFeedback.isClimbingActivated()) {
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
        */

        else {
            if (DriverStation.getMatchTime() <= 0) {
                m_lEDFeedback.setColor(Color.kPurple);
            }
            else if (DriverStation.getMatchTime() <= 10.0) {
                m_lEDFeedback.setFlash(Color.kRed, 125);
            }
            else if (DriverStation.getMatchTime() <= 15.0) {
                m_lEDFeedback.setFlash(Color.kWhite, 250);
            }
            else if (DriverStation.getMatchTime() <= 20.0) {
                m_lEDFeedback.setFlash(Color.kGreen, 500);
            }
            else if (DriverStation.getMatchTime() <= 30.0) {
                m_lEDFeedback.setFlash(Color.kGreen, 1000);
            }
            else {
                m_lEDFeedback.setColor(Color.kPurple);
            }
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
