package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.DriveStraight;

public class backwardAuton extends SequentialCommandGroup {
  private Drivetrain m_drive;

  public backwardAuton(Drivetrain drive) {

    m_drive = drive;
    addCommands(new DriveStraight(m_drive, -36.0), new TurnCommand(m_drive, 0.1, 45));

    
  }
}
