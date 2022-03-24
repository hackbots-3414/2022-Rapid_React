// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

/** Add your docs here. */
public class TrajectoryFactory {
    private static final String BASE_PATH = "paths/";
    private static final HashMap<String, Trajectory> pathCache = new HashMap<>();

    private TrajectoryFactory() {
    }

    private static Trajectory loadTrajectory(String fileName) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(BASE_PATH + fileName);
            return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + BASE_PATH + fileName, ex.getStackTrace());
        }
        return null;
    }

    private static Trajectory getTrajectory(String fileName) {
        Trajectory trajectory = pathCache.get(fileName);
        if (trajectory == null) {
            trajectory = loadTrajectory(fileName);
            pathCache.put(fileName, trajectory);
        }
        return trajectory;
    }

    public static Trajectory getPath(String name) {
        switch (name) {
            case "TestPath":
                return getTrajectory("TestPath.wpilib.json");
            default:
                return getTrajectory("TestPath.wpilib.json");
        }
    }
}
