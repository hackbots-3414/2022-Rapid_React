package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.vision.Ball;

public class EatBall extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(EatBall.class);
    
    public EatBall() {}

    @Override
    public void initialize() {
        LOG.info(Ball.toString(RobotContainer.getInstance().m_pixy.getBall()));
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
