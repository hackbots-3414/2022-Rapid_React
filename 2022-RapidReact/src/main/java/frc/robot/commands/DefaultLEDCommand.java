package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
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

        // if (false /* check if clmbing */) {
        // m_lEDFeedback.setFlash(Color.kGreen, .5);
        // } else if (DriverStation.getMatchTime() <= 30.0){
        // if (m_lEDFeedback.isClimbLineDetected()){

        // }
        // }

        // m_lEDFeedback.setColor(Color.kPurple);
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
