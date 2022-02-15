package frc.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.commands.RunIntake;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.WaitBackupSequential;
import frc.robot.commands.WaitCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

    private static final Logger LOG = LoggerFactory.getLogger(RobotContainer.class);

    private static RobotContainer m_robotContainer = new RobotContainer();

    // The robot's subsystems
    public final LEDFeedback m_lEDFeedback = new LEDFeedback();
    public final Shooter m_shooter = new Shooter();
    public final Intake m_intake = new Intake();
    public final Drivetrain m_drivetrain = new Drivetrain();

    // Joysticks
    private final XboxController operatorPad = new XboxController(1);

    public final Belt m_belt = new Belt();

    // A chooser for autonomous commands
    SendableChooser<Command> m_chooser = new SendableChooser<>();

    private RobotContainer() {
        // Smartdashboard Subsystems

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("ShooterCommand", new ShooterCommand(m_shooter));
        SmartDashboard.putData("Intake Command", new RunIntake(m_intake));

        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands

        m_intake.setDefaultCommand(new DefaultIntakeCommand(m_lEDFeedback, m_intake));
        m_drivetrain.setDefaultCommand(new TeleopCommand(m_drivetrain));
        m_belt.setDefaultCommand(new BeltCommand(m_belt, 1.0));
        //m_intake.setDefaultCommand(new RunIntake(m_intake));

        // Configure autonomous sendable chooser

        m_chooser.addOption("Autonomous Command", new AutonomousCommand());
        m_chooser.setDefaultOption("Wait and Backup", new WaitBackupSequential(m_drivetrain));

        SmartDashboard.putData("Auto Mode", m_chooser);
        SmartDashboard.putData("Wait Command", new WaitCommand());

    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    private void configureButtonBindings() {
        // Create some buttons
        final JoystickButton shootButton = new JoystickButton(operatorPad, XboxController.Button.kRightBumper.value);
        final JoystickButton intakeButton = new JoystickButton(operatorPad, XboxController.Button.kLeftBumper.value);
        intakeButton.whileHeld(new RunIntake(m_intake), true);
        shootButton.whileHeld(new ShooterCommand(m_shooter), true);

        

    }

    public XboxController getoperatorPad() {
        return operatorPad;
    }

    public Command getAutonomousCommand() {
        // The selected command will be run in autonomous
        return m_chooser.getSelected();
    }
}
