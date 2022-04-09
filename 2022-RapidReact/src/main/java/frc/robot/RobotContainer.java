package frc.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.DefaultLEDCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.Eject;
import frc.robot.commands.RunShoot;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.autonomous.shootFarBackup;
import frc.robot.commands.autonomous.TarmacOne.OneBallHigh;
import frc.robot.commands.autonomous.TarmacOne.OneBallLow;
import frc.robot.commands.autonomous.TarmacOne.ThreeBall;
import frc.robot.commands.autonomous.TarmacOne.TwoBall;
import frc.robot.commands.autonomous.TarmacOne.ZeroBall;
import frc.robot.commands.autonomous.TarmacTwo.TwoBallFar;
import frc.robot.commands.autonomous.Pathweaver.AutonomousFactory;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.LEDFeedback;
import frc.robot.subsystems.Shooter;

public class RobotContainer {

    private static final Logger LOG = LoggerFactory.getLogger(RobotContainer.class);

    private static RobotContainer m_robotContainer = new RobotContainer();

    // The robot's subsystems
    public final Belt m_belt;
    public final LEDFeedback m_lEDFeedback;
    public final Shooter m_shooter;
    public final Drivetrain m_drivetrain;
    public final Climber m_climber;
    public final PowerDistribution m_powerdistribution;
    public final AutonomousFactory m_autonomousFactory;
    // public final Pixy m_pixy;

    // Joysticks
    private final XboxController operatorPad = new XboxController(1);

    // A chooser for autonomous commands
    SendableChooser<Command> m_chooser = new SendableChooser<>();

    private RobotContainer() {
        // The robot's subsystems
        m_belt = new Belt();
        m_lEDFeedback = new LEDFeedback();
        m_shooter = new Shooter();
        m_drivetrain = new Drivetrain();
        m_climber = new Climber();
        m_powerdistribution = new PowerDistribution(Constants.PowerDistribution.CanID, ModuleType.kRev);
        m_autonomousFactory = AutonomousFactory.getInstance(m_drivetrain, m_belt, m_shooter);
        
        // m_pixy = new Pixy();

        // Smartdashboard Subsystems

        // SmartDashboard Buttons
        // SmartDashboard.putData("Belt Command", new BeltCommand(m_belt));
        // SmartDashboard.putData("climberUp", new ClimberUpCommand(m_climber));
        // SmartDashboard.putData("climberDown", new ClimberDownCommand(m_climber));

        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands

        m_drivetrain.setDefaultCommand(new TeleopCommand(m_drivetrain));
        m_lEDFeedback.setDefaultCommand(new DefaultLEDCommand(m_lEDFeedback, m_climber));

        // Configure autonomous sendable chooser

        m_chooser.addOption("Any Tarmac - 0 Ball", new ZeroBall(m_drivetrain));
        m_chooser.addOption("Any Tarmac - 1 Ball Low", new OneBallLow(m_shooter, m_drivetrain, m_belt));
        m_chooser.addOption("Any Tarmac - 1 Ball High", new OneBallHigh(m_shooter, m_drivetrain, m_belt));
        m_chooser.addOption("Tarmac 1 - 2 Ball", new TwoBall(m_drivetrain, m_belt, m_shooter));
        m_chooser.setDefaultOption("Tarmac 1 - 3 Ball", new ThreeBall(m_drivetrain, m_belt, m_shooter));
        m_chooser.addOption("Tarmac 2 - 2 Ball", new TwoBallFar(m_drivetrain, m_belt, m_shooter));
        m_chooser.addOption("Tarmac 2 - 1 Ball High/Far", new shootFarBackup(m_shooter, m_drivetrain, m_belt));
        m_chooser.addOption("Tarmac 1 - 3 Ball Pathweaver", m_autonomousFactory.create3BallAuton());
        m_chooser.addOption("Tarmac 1 - 2 Ball Pathweaver", m_autonomousFactory.create2BallAutonT1());
        m_chooser.addOption("Tarmac 1 - 3 Ball Pathweaver Wierd", m_autonomousFactory.create3BallAutonWierd());
        m_chooser.addOption("Tarmac 1 - 4 Ball Pathweaver Wierd", m_autonomousFactory.create4BallAuton());
        //m_chooser.addOption("3 ball trial", new ThreeBallNew(m_drivetrain, m_belt, m_shooter));
        // m_chooser.addOption("Tarmac 2 - 2 Ball CLose", new TwoBallCloseMovementShooting(m_drivetrain, m_belt, m_shooter));

        SmartDashboard.putData("Auton Mode", m_chooser);

        // SmartDashboard.putData("Wait Command", new WaitCommand());

    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    private void configureButtonBindings() {
        // Create some buttons
        final JoystickButton shootHighButton = new JoystickButton(operatorPad, XboxController.Button.kRightBumper.value);
        final JoystickButton shootLowButton = new JoystickButton(operatorPad, XboxController.Button.kB.value);
        //final JoystickButton beltButton = new JoystickButton(operatorPad, XboxController.Button.kY.value);
        final JoystickButton intakeButton = new JoystickButton(operatorPad, XboxController.Button.kLeftBumper.value);
        final POVButton climberUpButton = new POVButton(operatorPad, Constants.ClimberConstants.climbUpAngle);
        final POVButton climberDownButton = new POVButton(operatorPad, Constants.ClimberConstants.climbDownAngle);
        final JoystickButton ejectButton = new JoystickButton(operatorPad, XboxController.Button.kX.value);
        final JoystickButton eatBallButton = new JoystickButton(operatorPad, XboxController.Button.kA.value);
        final JoystickButton shootButton = new JoystickButton(operatorPad, XboxController.Button.kBack.value);
        final JoystickButton shootHighFarButton = new JoystickButton(operatorPad, XboxController.Button.kRightStick.value);

        //assign button fuctions
        shootHighFarButton.whileHeld(new ShootCommand(m_belt, m_shooter, 3, Constants.ShooterConstants.shooterTimer));
        shootButton.whileHeld(new RunShoot(m_belt, m_shooter, true, Constants.ShooterConstants.shooterTimer), true);
        ejectButton.whileHeld(new Eject(m_belt), true);
        //beltButton.whileHeld(new RunBelt(m_belt), true);
        intakeButton.whileHeld(new BeltCommand(m_belt), true);
        shootHighButton.whileHeld(new ShootCommand(m_belt, m_shooter, 1, Constants.ShooterConstants.shooterTimer), true);
        shootLowButton.whileHeld(new ShootCommand(m_belt, m_shooter, 2, Constants.ShooterConstants.shooterTimer), true);
        climberUpButton.whenPressed(new ClimberUpCommand(m_climber), true);
        climberDownButton.whenPressed(new ClimberDownCommand(m_climber), true);
        // eatBallButton.whileHeld(new EatBall(m_drivetrain, m_pixy), true);
        //beltButton.whileHeld(new BeltCommand(m_belt));

    }

    public XboxController getoperatorPad() {
        return operatorPad;
    }

    public Command getAutonomousCommand() {
        // The selected command will be run in autonomous
        return m_chooser.getSelected();
    }
}
