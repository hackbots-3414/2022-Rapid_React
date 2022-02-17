package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Belt;

public class BeltCommand extends CommandBase {

  final Belt m_belt;
  boolean isRunning;
  double output;
  private boolean isBallMoving = false;
  
  public BeltCommand(Belt belt) {
    addRequirements(belt);
    m_belt = belt;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if(isBallMoving == false && m_belt.getIRBottom() == true) { //Check the state if we are in the process of moving a ball already 
      if( m_belt.getIRMiddle() == false || m_belt.getIRTop() == false) { //space open; start belts
        isBallMoving = true; //set the state to start moving a new ball
      }
    }

    if(isBallMoving == true) {
      if((m_belt.getIRTop() == true) && (m_belt.getIRMiddle() == false)) { // Have ball in top position, but not in middle
        if(m_belt.getIRMiddle() == true) {

          isBallMoving = false; // fully loaded
          m_belt.stopAllMotors();
          

        } // Have ball in middle position       
        }
        else {
          m_belt.startMotorBottom();
          m_belt.startMotorMiddle();
          if(m_belt.getIRMiddle() == true) {
            m_belt.stopAllMotors();
            isBallMoving = false;
          }
        }


      } else if (m_belt.getIRTop() == true) { // Have no balls in top or middle
        isBallMoving = false; // top is loaded
        m_belt.stopAllMotors();
      } else {  //ball not in top position yet
        m_belt.startAllMotors();
      }
     }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_belt.stop();
    // super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false; // Default command
  }
}
