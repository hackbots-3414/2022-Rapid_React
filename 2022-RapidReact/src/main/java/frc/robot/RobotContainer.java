package frc.robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.Eject;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ShootHighWaitBackup;
import frc.robot.commands.ShootLowWaitBackup;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.WaitBackupSequential;
import frc.robot.commands.WaitCommand;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

    private static final Logger LOG = LoggerFactory.getLogger(RobotContainer.class);

    private static RobotContainer m_robotContainer = new RobotContainer();

    // The robot's subsystems
    public final LEDFeedback m_lEDFeedback = new LEDFeedback();
    public final Shooter m_shooter = new Shooter();
    public final Drivetrain m_drivetrain = new Drivetrain();

    public final Climber m_climber = new Climber();

    public final Belt m_belt = new Belt();


    // Joysticks
    private final XboxController operatorPad = new XboxController(1);

    // A chooser for autonomous commands
    SendableChooser<Command> m_chooser = new SendableChooser<>();

    private RobotContainer() {
        // Smartdashboard Subsystems

        // SmartDashboard Buttons
        // SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        // SmartDashboard.putData("Belt Command", new BeltCommand(m_belt));
        // SmartDashboard.putData("climberUp", new ClimberUpCommand(m_climber));
        // SmartDashboard.putData("climberDown", new ClimberDownCommand(m_climber));

        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands

        m_drivetrain.setDefaultCommand(new TeleopCommand(m_drivetrain));

        // Configure autonomous sendable chooser

        m_chooser.addOption("Wait and Backup", new WaitBackupSequential(m_drivetrain));
        m_chooser.addOption("ShootLow, Wait, Back Up", new ShootLowWaitBackup(m_shooter, m_drivetrain, m_belt));
        m_chooser.setDefaultOption("ShootHigh, Wait, Backup", new ShootHighWaitBackup(m_shooter, m_drivetrain, m_belt));

        SmartDashboard.putData("Auto Mode", m_chooser);
        SmartDashboard.putData("Wait Command", new WaitCommand());

    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    private void configureButtonBindings() {
        // Create some buttons
        final JoystickButton shootHighButton = new JoystickButton(operatorPad, XboxController.Button.kRightBumper.value);
        final JoystickButton shootLowButton = new JoystickButton(operatorPad, XboxController.Button.kB.value);
        final JoystickButton intakeButton = new JoystickButton(operatorPad, XboxController.Button.kLeftBumper.value);
        final POVButton climberUpButton = new POVButton(operatorPad, Constants.ClimberConstants.climbUpAngle);
        final POVButton climberDownButton = new POVButton(operatorPad, Constants.ClimberConstants.climbDownAngle);
        final JoystickButton ejectButton = new JoystickButton(operatorPad, XboxController.Button.kX.value);
        ejectButton.whileHeld(new Eject(m_belt), true);
        intakeButton.whileHeld(new BeltCommand(m_belt), true);
        shootHighButton.whileHeld(new ShootCommand(m_belt, m_shooter, true, Constants.ShooterConstants.shooterTimer), true);
        shootLowButton.whenPressed(new ShootCommand(m_belt, m_shooter, false, Constants.ShooterConstants.shooterTimer), true);
        climberUpButton.whenPressed(new ClimberUpCommand(m_climber), true);
        climberDownButton.whenPressed(new ClimberDownCommand(m_climber), true);
    }

    public XboxController getoperatorPad() {
        return operatorPad;
    }

    public Command getAutonomousCommand() {
        // The selected command will be run in autonomous
        return m_chooser.getSelected();
    }
}
