package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitCommand extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(WaitCommand.class);

    public long prevMilliseconds;
    public long waitTime;

    static {
        SmartDashboard.putNumber("Auton Delay", 1000);
    }

    public WaitCommand() {
    }

    @Override
    public void initialize() {
        prevMilliseconds = System.currentTimeMillis();
        waitTime = (long) SmartDashboard.getNumber("Auton Delay", 0);

        LOG.info("wait command starting prevMilliseconds = {}, waitTime = {}", prevMilliseconds, waitTime);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        long currentMillis = System.currentTimeMillis();

        long newMilliseconds = currentMillis - prevMilliseconds;

        if (newMilliseconds >= waitTime) {
            LOG.info("wait command finished");
            return true;
        } else {
            return false;
        }
    }
}
