package frc.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.RunIntake;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot
 * (including subsystems, commands, and button mappings) should be declared
 * here.
 */
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

    // A chooser for autonomous commands
    SendableChooser<Command> m_chooser = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    private RobotContainer() {
        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("ShooterCommand", new ShooterCommand(m_shooter));
        SmartDashboard.putData("Intake Command", new RunIntake(m_intake));

        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        m_drivetrain.setDefaultCommand(new TeleopCommand(m_drivetrain));
        m_intake.setDefaultCommand(new RunIntake(m_intake));

        // Configure autonomous sendable chooser
        m_chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

        SmartDashboard.putData("Auto Mode", m_chooser);
        SmartDashboard.putData("Wait Command", new WaitCommand());
    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Create some buttons
        final JoystickButton shootButton = new JoystickButton(operatorPad, XboxController.Button.kRightBumper.value);
        final JoystickButton intakeButton = new JoystickButton(operatorPad, XboxController.Button.kLeftBumper.value);
        intakeButton.whileHeld(new RunIntake(m_intake), true);
        shootButton.whileHeld(new ShooterCommand(m_shooter), true);
        SmartDashboard.putData("shootButton", new ShooterCommand(m_shooter));
    }

    public XboxController getoperatorPad() {
        return operatorPad;
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // The selected command will be run in autonomous
        return m_chooser.getSelected();
    }
}
