package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

public class TeleopCommand extends CommandBase {

    private static final Logger LOG = LoggerFactory.getLogger(TeleopCommand.class);

    private Drivetrain drivetrain;

    public TeleopCommand(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
    }

    public TeleopCommand() {
    }

    @Override
    public void initialize() {
    }

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

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
