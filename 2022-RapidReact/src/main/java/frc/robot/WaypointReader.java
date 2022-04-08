// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.spline.Spline;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Filesystem;

/** Add your docs here. */
public class WaypointReader {
    private static final double PATHWEAVER_Y_OFFSET = 8.2296; 
    /* Look at first waypoint of the path file and read the y value. lets call this a. 
    then look at the first pose's translation's y coordinate. let's call this b. 
    do b-a to get the value. 
    the pathweaver points and the path file have different y points for some reason, so this compensates for that. */
    public static TrajectoryGenerator.ControlVectorList getControlVectors(String pathName) throws IOException{
        String relativePath = "waypoints/" + pathName + ".path";
        Path path = Filesystem.getDeployDirectory().toPath().resolve(relativePath);

        TrajectoryGenerator.ControlVectorList controlVectors = new TrajectoryGenerator.ControlVectorList();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))){
            boolean skippedFirst = false;
            String line = reader.readLine();
            while (line != null) {
                if (!skippedFirst || !line.contains(",")) {
                    skippedFirst = true;
                    line = reader.readLine();
                    continue;
                }
                String[] split = line.split(",");
                controlVectors.add(new Spline.ControlVector(new double[]{Double.parseDouble(split[0]), Double.parseDouble(split[2]), 0}, new double[]{Double.parseDouble(split[1]) + PATHWEAVER_Y_OFFSET, Double.parseDouble(split[3]), 0}));

                line = reader.readLine();
            }
        }

        return controlVectors;
    }   
}
