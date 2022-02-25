// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyStore.LoadStoreParameter;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

/** Add your docs here. */
public class TrajectoryFactory {
    private static final String BASE_PATH = "paths/";
    private static final Logger LOG = LoggerFactory.getLogger(TrajectoryFactory.class);
    private static final HashMap<String, Trajectory> pathCache = new HashMap<>();

    private TrajectoryFactory() {
    }

    private static Trajectory loadTrajectory(String fileName) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(BASE_PATH + fileName);
            return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + BASE_PATH + fileName, ex.getStackTrace());
            LOG.error("Failed to load Trajectory path {}, error: {}", BASE_PATH + fileName, ex);
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
            case "TestPathSmooth":
                return getTrajectory("TestPathSmooth.wpilib.json");
            case "BlueBottom1Rev":
                return getTrajectory("BlueBottom1Rev.wpilib.json");
            case "BlueBottom1For":
                return getTrajectory("BlueBottom1For.wpilib.json");
            case "BlueBottom2Rev":
                return getTrajectory("BlueBottom2Rev.wpilib.json");
            case "BlueBottom2For":
                return getTrajectory("BlueBottom3For.wpilib.json");
            case "BlueBottom3Rev":
                return getTrajectory("BlueBottom3Rev.wpilib.json");
            case "BlueBottom3For":
                return getTrajectory("BlueBottom3For.wpilib.json");
            case "BlueTop1Rev":
                return getTrajectory("BlueTop1Rev.wpilib.json");
            case "BlueTop1For":
                return getTrajectory("BlueTop1For.wpilib.json");
            case "BlueTop2Rev":
                return getTrajectory("BlueTop2Rev.wpilib.json");
            case "BlueTop2For":
                return getTrajectory("BlueTop2For.wpilib.json");
            case "BlueTop3Rev":
                return getTrajectory("BlueTop3Rev.wpilib.json");
            case "BlueTop3For":
                return getTrajectory("BlueTop3For.wpilib.json");
            case "RedBottom1Rev":
                return getTrajectory("RedBottom1Rev.wpilib.json");
            case "RedBottom1For":
                return getTrajectory("RedBottom1For.wpilib.json");
            case "RedBottom2Rev":
                return getTrajectory("RedBottom2Rev.wpilib.json");
            case "RedBottom2For":
                return getTrajectory("RedBottom2For.wpilib.json");
            case "RedBottom3Rev":
                return getTrajectory("RedBottom3Rev.wpilib.json");
            case "RedBottom3For":
                return getTrajectory("RedBottom3For.wpilib.json");
            case "RedTop1Rev":
                return getTrajectory("RedTop1Rev.wpilib.json");
            case "RedTop1For":
                return getTrajectory("RedTop1For.wpilib.json");
            case "RedTop2Rev":
                return getTrajectory("RedTop2Rev.wpilib.json");
            case "RedTop2For":
                return getTrajectory("RedTop2For.wpilib.json");
            case "RedTop3Rev":
                return getTrajectory("RedTop3Rev.wpilib.json");
            case "RedTop3For":
                return getTrajectory("RedTop3For.wpilib.json");
            default:
                return getTrajectory("TestPathSmooth.wpilib.json");

        }
    }
}
