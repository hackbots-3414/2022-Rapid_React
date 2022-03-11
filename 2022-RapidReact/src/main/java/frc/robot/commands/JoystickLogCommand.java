// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.FileReader;




public class JoystickLogCommand extends CommandBase {

  /** Creates a new JoystickLogCommand. */
    public final static String filepath = "C:/Users/Hackbots/code/2022-Rapid_React/2022-RapidReact/src/main/deploy/LOG.txt";

    public final static File file = new File(filepath); 

    public final static Joystick joystick = new Joystick(0); 
  
    public static FileWriter outputFile;
    public static PrintWriter writer;
  
  public JoystickLogCommand() {
    // Use addRequirements() here to declare subsystem dependencies.


    try {

      outputFile = new FileWriter(file);
      writer = new PrintWriter(outputFile); 

      String header = "leftXOffset, rightXOffset, leftYOffset, rightYOffset";

      writer.println(header);
      
      
      

    } catch (IOException e) {

      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {


}
    



  

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    



  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

  

    if (isFinished()) {

      writer.close();

      return true;

    } else {

      writeToFile();
      
      return false; 

    }
 

  
}





public static void writeToFile() {

  final double rightX = joystick.getRawAxis(3);
  final double rightY = joystick.getRawAxis(4);
  final double leftX = joystick.getRawAxis(0); 
  final double leftY = joystick.getRawAxis(1); 


  String data = leftX + ", " + rightX + ", " + leftY + ", " + rightY;

  writer.println(data);




}
  


}
















