// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootWaitBackup extends SequentialCommandGroup {
private Drivetrain drive;
private Shooter shooter;

/** Creates a new ShootWaitBackup. */

  public ShootWaitBackup(Shooter shooter, Drivetrain drive) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    addCommands(new ShootLowCommand(shooter),new WaitCommand(), new DriveStraight(drive, -86.5));
    this.shooter = shooter;
    this.drive = drive;

  }
  public void execute () {
    super.execute();
  }

  public void end(boolean interrupted){
  shooter.stop();
  drive.stopDriving();
  super.end(interrupted);
  }

}

