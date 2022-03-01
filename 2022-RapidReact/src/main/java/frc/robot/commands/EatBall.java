package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.vision.Ball;
import frc.robot.subsystems.vision.Pixy;

public class EatBall extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(EatBall.class);

    private final Drivetrain m_drivetrain;
    private final Pixy m_pixy;

    private Ball ball;
    private boolean aligned = false;

    public EatBall(Drivetrain drivetrain, Pixy pixy) {
        m_drivetrain = drivetrain;
        m_pixy = pixy;
        addRequirements(m_drivetrain, m_pixy);
    }

    @Override
    public void initialize() {
        LOG.trace("EatBall initialized");
        aligned = false;
    }

    @Override
    public void execute() {
        LOG.trace("EatBall executed");
        ball = m_pixy.getBall();
        if (ball != null) {
            if ((double) ball.x - 157.5D >= 75) {
                m_drivetrain.arcadeDrive(0, -0.3);
            }
            else if ((double) ball.x - 157.5D <= -75) {
                m_drivetrain.arcadeDrive(0, 0.3);
            }
            else {
                m_drivetrain.arcadeDrive(0, 0);
                if (Math.max(ball.width, ball.height) >= 180) {
                    m_drivetrain.arcadeDrive(0, 0);
                    aligned = true;
                }
                else {
                    m_drivetrain.arcadeDrive(0.4, 0);
                }
            }
        }
        else {
            LOG.info("No balls detected");
        }
    }

    @Override
    public void end(boolean interrupted) {
        LOG.trace("EatBall ended. Interrupted: {}", interrupted);
    }

    @Override
    public boolean isFinished() {
        return aligned;
    }
}
