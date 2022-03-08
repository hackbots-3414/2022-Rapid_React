package frc.robot.commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitBeforeShoot extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(WaitBeforeShoot.class);

    public long prevMilliseconds;
    public long waitTime;

    static {
        SmartDashboard.putNumber("Delay Before Shoot", 2000);
    }

    @Override
    public void initialize() {
        prevMilliseconds = System.currentTimeMillis();
        waitTime = (long) SmartDashboard.getNumber("Delay Before Shoot", 0);

        LOG.info("wait command starting prevMilliseconds = {}, waitTime = {}", prevMilliseconds, waitTime);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        long currentMillis = System.currentTimeMillis();

        long newMilliseconds = currentMillis - prevMilliseconds;

        if (newMilliseconds >= waitTime) {
            LOG.info("wait before shoot command finished");
            return true;
        } else {
            return false;
        }
    }
}

