// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
  /** Creates a new RunIntake. */

  private final Intake m_intake;

  final WPI_TalonFX intakeMotor1;
  final WPI_TalonFX intakeMotor2;
  final WPI_TalonFX intakeMotor3;
  private static int intakeId1 = Constants.Transport.transportMotor1;
  private static int intakeId2 = Constants.Transport.transportMotor2;
  private static int intakeId3 = Constants.Transport.transportMotor3;

  public RunIntake(Intake subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.

    m_intake = subsystem;

    addRequirements(m_intake);

    intakeMotor1 = new WPI_TalonFX(intakeId1);
    intakeMotor2 = new WPI_TalonFX(intakeId2);
    intakeMotor3 = new WPI_TalonFX(intakeId3);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Configurable speeds
    intakeMotor1.set(ControlMode.Velocity, 1000);
    intakeMotor2.set(ControlMode.Velocity, 1000);
    intakeMotor3.set(ControlMode.Velocity, 1000);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if ((intakeMotor1.getActiveTrajectoryVelocity() == 0) && (intakeMotor2.getActiveTrajectoryVelocity() == 0)
        && (intakeMotor3.getActiveTrajectoryVelocity() == 0)) {
      return true;
    } else {
      return false;
    }

  }
}
