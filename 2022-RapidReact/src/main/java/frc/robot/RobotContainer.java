
package frc.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.RunIntake;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.WaitBackupSequential;
import frc.robot.commands.WaitCommand;
import frc.robot.commands.shoot.shootHigh.ShootHigh;
import frc.robot.commands.shoot.shootLow.ShootLow;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

    // Declare random constants

    private static int x = 1000;

    private static final Logger LOG = LoggerFactory.getLogger(RobotContainer.class);

    private static RobotContainer m_robotContainer = new RobotContainer();

    // The robot's subsystems
    public final LEDFeedback m_lEDFeedback = new LEDFeedback();
    public final Shooter m_shooter = new Shooter();
    public final Intake m_intake = new Intake();
    public final Drivetrain m_drivetrain = new Drivetrain();

    public final Climber m_climber = new Climber();

    public final Belt m_belt = new Belt();


    // Joysticks
    private final XboxController operatorPad = new XboxController(1);

    // A chooser for autonomous commands
    SendableChooser<Command> m_chooser = new SendableChooser<>();

    private RobotContainer() {

        LOG.info("Started the Shuffleboard, hope this works!");
        // Smartdashboard Subsystems

        // SmartDashboard Buttons

        addCommandButtons();

        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("ShooterCommand", new ShootHigh(m_shooter, m_belt));
        SmartDashboard.putData("Intake Command", new RunIntake(m_intake));
        SmartDashboard.putData("climberUp", new ClimberUpCommand(m_climber));
        SmartDashboard.putData("climberDown", new ClimberDownCommand(m_climber));


        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        setCommands();

        m_intake.setDefaultCommand(new DefaultIntakeCommand(m_lEDFeedback, m_intake));
        m_drivetrain.setDefaultCommand(new TeleopCommand(m_drivetrain));
        m_belt.setDefaultCommand(new BeltCommand(m_belt));

        m_climber.setDefaultCommand(new ClimberUpCommand(m_climber));
        m_climber.setDefaultCommand(new ClimberDownCommand(m_climber));



        // m_intake.setDefaultCommand(new RunIntake(m_intake));

        // Configure autonomous sendable chooser
        editUI();

    }



    private void setCommands() {

         // Sets default commands

         m_intake.setDefaultCommand(new DefaultIntakeCommand(m_lEDFeedback, m_intake));
         m_drivetrain.setDefaultCommand(new TeleopCommand(m_drivetrain));
         m_drivetrain.setDefaultCommand(new DriveStraight(m_drivetrain, x));
         m_drivetrain.setDefaultCommand(new WaitCommand());
         m_drivetrain.setDefaultCommand(new WaitBackupSequential(m_drivetrain));

        

    }

    private void addCommandButtons() {

        // Puts data of the commands into the Shuffleboard Layout

        // SmartDashboard.putData("ShooterCommand", new ShooterCommand(m_shooter));
        SmartDashboard.putData("Intake Command", new RunIntake(m_intake));
        SmartDashboard.putData("Drive-Straight Command", new DriveStraight(m_drivetrain, x));
        SmartDashboard.putData("Wait Command", new WaitCommand());
        SmartDashboard.putData("Wait Backup Command", new WaitBackupSequential(m_drivetrain));
        CameraServer.startAutomaticCapture();

    }

    private void editUI() {
        // Setting options for the dropdown menu

        m_chooser.addOption("Drive-Straight Command", new DriveStraight(m_drivetrain, x));
        m_chooser.addOption("Wait Command", new WaitCommand());
        m_chooser.addOption("Wait Backup Command", new WaitBackupSequential(m_drivetrain));

        SmartDashboard.putData("Auto Mode", m_chooser);

    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    private void configureButtonBindings() {
        // Create some buttons
        final JoystickButton shootHighButton = new JoystickButton(operatorPad, XboxController.Button.kRightBumper.value);

        final JoystickButton shootLowButton = new JoystickButton(operatorPad, XboxController.Button.kB.value);

        final JoystickButton intakeButton = new JoystickButton(operatorPad, XboxController.Button.kLeftBumper.value);
        
        intakeButton.whileHeld(new RunIntake(m_intake), true);
        shootHighButton.whileHeld(new ShootHigh(m_shooter, m_belt), true);
        shootLowButton.whileHeld(new ShootLow(m_shooter, m_belt), true);
        final POVButton climberUpButton = new POVButton(operatorPad, Constants.ClimberConstants.climbUpAngle);
        final POVButton climberDownButton = new POVButton(operatorPad, Constants.ClimberConstants.climbDownAngle);
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
