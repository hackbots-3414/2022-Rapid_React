package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

public class WaitBackupSequential extends SequentialCommandGroup {
    private Drivetrain drive;

    public WaitBackupSequential(Drivetrain drive) {
        addCommands(new WaitCommand(), new DriveStraight(drive, -86.5));
        
        this.drive = drive;
    }

    @Override
    public void end(boolean interrupted) {
        drive.stopDriving();
        super.end(interrupted);
    }
}
