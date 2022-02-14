package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ShootWaitBackup extends SequentialCommandGroup {
    
    private Drivetrain drive;
    private Shooter shooter;

    public ShootWaitBackup(Shooter shooter, Drivetrain drive) {
        addCommands(new ShootLowCommand(shooter), new WaitCommand(), new DriveStraight(drive, -86.5));

        this.shooter = shooter;
        this.drive = drive;
    }

    public void execute() {
        super.execute();
    }

    public void end(boolean interrupted) {
        shooter.stop();
        drive.stopDriving();
        super.end(interrupted);
    }
}
