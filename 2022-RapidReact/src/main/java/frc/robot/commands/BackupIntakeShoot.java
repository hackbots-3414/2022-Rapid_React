// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class BackupIntakeShoot extends SequentialCommandGroup {
  private Drivetrain drive;
  private Shooter shooter;
  private Belt belt;

  /** Creates a new BackupIntakeShoot. */
  public BackupIntakeShoot(Drivetrain drivetrainInput, Shooter shooterInput, Belt beltInput) {

    this.belt = beltInput;
    this.shooter = shooterInput;
    this.drive = drivetrainInput;

    addCommands(new DriveStraight(drive, 36.44), new AutonBeltCommand(belt).withTimeout(2.5),
        new DriveStraight(drive, -159.1), new ShootCommand(belt, shooter, true, 100), new DriveStraight(drive, 86.5));
  }

  @Override
  public void end(boolean interrupted) {
    shooter.stop();
    belt.stopAllMotors();
    drive.stopDriving();
    super.end(interrupted);
  }
}
