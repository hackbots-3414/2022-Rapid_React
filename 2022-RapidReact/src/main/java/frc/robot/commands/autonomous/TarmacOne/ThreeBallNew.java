// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous.TarmacOne;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BeltCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Turn;
import frc.robot.subsystems.Belt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ThreeBallNew extends SequentialCommandGroup {
  /** Creates a new ThreeBallNew. */
  public ThreeBallNew(Drivetrain drivetrain, Belt belt, Shooter shooter) {
    addRequirements(drivetrain);
        addRequirements(belt);
        addRequirements(shooter);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ShootCommand(belt, shooter, 1, 200),
    new ParallelCommandGroup(
      new SequentialCommandGroup(
        new DriveStraight(drivetrain, -46), 
        new Turn(drivetrain, -22),
        new DriveStraight(drivetrain, -17),//changed for comp; used to be 20
        new Turn(drivetrain, 108), //112
        new DriveStraight(drivetrain, -70),
        new Turn(drivetrain, -25),
        new DriveStraight(drivetrain, 55), //changed for comp; used to be 75
        new Turn(drivetrain, -35), //-13
        new DriveStraight(drivetrain, 32)), 
      new BeltCommand(belt).withTimeout(10)),
      new ShootCommand(belt, shooter, 1, 3000));
  }
}
