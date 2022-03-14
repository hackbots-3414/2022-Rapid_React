package frc.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {

    private static final Logger LOG = LoggerFactory.getLogger(Robot.class);

    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;

    public final Drivetrain m_drivetrain = new Drivetrain();

    @Override
    public void robotInit() {
        
        m_robotContainer = RobotContainer.getInstance();
        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setFPS(30);
        camera.setResolution(320, 240);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        m_drivetrain.setCoastMode();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        m_robotContainer.getInstance().m_lEDFeedback.setClimbingActivated(false);
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();
        m_drivetrain.setBrakeMode();


        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        m_drivetrain.setBrakeMode();
        
        m_robotContainer.getInstance().m_lEDFeedback.setClimbingActivated(false);
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
