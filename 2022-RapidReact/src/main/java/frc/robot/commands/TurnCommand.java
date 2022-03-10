package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TurnCommand extends CommandBase {
  public final Drivetrain m_driveTrain;
  public double speed;
  public double turnAngle;
  public boolean isFinished = false;

  public TurnCommand(Drivetrain m_driveTrain, double speed, double turnAngle) {
    this.m_driveTrain = m_driveTrain;
    this.speed = speed;
    this.turnAngle = turnAngle;

    addRequirements(m_driveTrain);
  }

  @Override
  public void initialize() {
    m_driveTrain.resetHeading();

  }

  @Override
  public void execute() {
    System.out.print("wertghb");
      m_driveTrain.drive(speed, turnAngle);
    }

  @Override
  public void end(boolean interrupted) {
    m_driveTrain.resetHeading();
    m_driveTrain.resetEncoders();
    m_driveTrain.stopDriving();
  }

  @Override
  public boolean isFinished() {
    if (m_driveTrain.getHeading() == turnAngle) {
        return true;
      }
    else {
      return false;
    }
  }
}
