package frc.robot;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


import java.util.List;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.ConfigureReverseControls;
import frc.robot.commands.DefaultLEDCommand;
import frc.robot.commands.Eject;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ShootHighWaitBackup;
import frc.robot.commands.ShootLowWaitBackup;
import frc.robot.commands.TeleopCommand;
import frc.robot.commands.WaitBackupSequential;
import frc.robot.commands.WaitCommand;
import frc.robot.commands.Pathweaver.AutonomousFactory;
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
    public final AutonomousFactory autonomousFactory = AutonomousFactory.getInstance();


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

        // Smartdashboard Subsystems

        // SmartDashboard Buttons
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
        m_chooser.addOption("PathweaverTest", autonomousFactory.createShootBackupIntake());
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

        //assign button fuctions
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
