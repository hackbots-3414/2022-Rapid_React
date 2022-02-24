package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.shoot.ShootCommand;
import frc.robot.commands.shoot.shootLow.SpinUp;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ShootHighWaitBackup extends SequentialCommandGroup {
    
    private Drivetrain drive;
    private Shooter shooter;
    private Belt belt;

    public ShootHighWaitBackup(Shooter shooter, Drivetrain drive, Belt belt) {
        //Using Low Shoot Command
        addCommands(new ShootCommand(belt, shooter, true, 100), new WaitCommand(), new DriveStraight(drive, -86.5));
        this.belt = belt;
        this.shooter = shooter;
        this.drive = drive;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        belt.stopAllMotors();
        drive.stopDriving();
        super.end(interrupted);
    }
}