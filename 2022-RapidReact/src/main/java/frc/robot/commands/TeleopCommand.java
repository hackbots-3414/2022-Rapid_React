package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

public class TeleopCommand extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(TeleopCommand.class);

    private Drivetrain drivetrain;

    /** Creates a new TeleopCommand. */
    public TeleopCommand(Drivetrain drivetrain) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
    }

    public TeleopCommand() {
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (OI.getButtonB() == 0) {
            drivetrain.arcadeDrive(OI.getLeftVertical(), OI.getRightLateral());
        } else if (OI.getButtonB() == 1) {
            drivetrain.arcadeDrive(OI.getLeftVertical(), OI.getLeftLateral());
        } else if (OI.getButtonB() == 2) {
            drivetrain.tankDrive(OI.getLeftVertical(), OI.getRightVertical());
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
