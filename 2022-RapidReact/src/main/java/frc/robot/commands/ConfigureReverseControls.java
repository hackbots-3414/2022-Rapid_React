// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ConfigureReverseControls extends CommandBase {
  private boolean reverse;
  
  private static final Logger LOG = LoggerFactory.getLogger(ConfigureReverseControls.class);

  /** Creates a new configureReverseControls. */
private final Drivetrain m_drivetrain;
  public ConfigureReverseControls(Drivetrain subsystem, boolean reverse) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = subsystem;
    // addRequirements(m_drivetrain);
    this.reverse = reverse;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    LOG.trace("ConfigureReverseControls @ execute(), reverse = {}", reverse);
    m_drivetrain.setControlsReversed(reverse);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    LOG.trace("ConfigureReverseControls isFinished(), reverse = {}", m_drivetrain.isControlsReversed());
    return reverse==m_drivetrain.isControlsReversed();
  }
}
