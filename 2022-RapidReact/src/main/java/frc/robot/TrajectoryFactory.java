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
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(BASE_PATH+fileName);
            return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + BASE_PATH+fileName, ex.getStackTrace());
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
    
    public static Trajectory getTestPathSmooth() {
        return getTrajectory("TestPathSmooth.wpilib.json");
    }
    public static Trajectory getBlueBottom1Rev() {
        return getTrajectory("BlueBottom1Rev.wpilib.json");
    }
    public static Trajectory getBlueBottom1For() {
        return getTrajectory("BlueBottom1For.wpilib.json");
    }
    public static Trajectory getBlueBottom2Rev() {
        return getTrajectory("BlueBottom2Rev.wpilib.json");
    }
    public static Trajectory getBlueBottom2For() {
        return getTrajectory("BlueBottom2For.wpilib.json");
    }
    public static Trajectory getBlueBottom3Rev() {
        return getTrajectory("BlueBottom3Rev.wpilib.json");
    }
    public static Trajectory getBlueBottom3For() {
        return getTrajectory("BlueBottom3For.wpilib.json");
    }
    public static Trajectory getBlueTop1Rev() {
        return getTrajectory("BlueTop1Rev.wpilib.json");
    }
    public static Trajectory getBlueTop1For() {
        return getTrajectory("BlueTop1For.wpilib.json");
    }
    public static Trajectory getBlueTop2Rev() {
        return getTrajectory("BlueTop2Rev.wpilib.json");
    }
    public static Trajectory getBlueTop2For() {
        return getTrajectory("BlueTop2For.wpilib.json");
    }
    public static Trajectory getBlueTop3Rev() {
        return getTrajectory("BlueTop3Rev.wpilib.json");
    }
    public static Trajectory getBlueTop3For() {
        return getTrajectory("BlueTop3For.wpilib.json");
    }
    public static Trajectory getRedBottom1Rev() {
        return getTrajectory("RedBottom1Rev.wpilib.json");
    }
    public static Trajectory getRedBottom1For() {
        return getTrajectory("RedBottom1For.wpilib.json");
    }
    public static Trajectory getRedBottom2Rev() {
        return getTrajectory("RedBottom2Rev.wpilib.json");
    }
    public static Trajectory getRedBottom2For() {
        return getTrajectory("RedBottom2For.wpilib.json");
    }
    public static Trajectory getRedBottom3Rev() {
        return getTrajectory("RedBottom3Rev.wpilib.json");
    }
    public static Trajectory getRedBottom3For() {
        return getTrajectory("RedBottom3For.wpilib.json");
    }
    public static Trajectory getRedTop1Rev() {
        return getTrajectory("RedTop1Rev.wpilib.json");
    }
    public static Trajectory getRedTop1For() {
        return getTrajectory("RedTop1For.wpilib.json");
    }
    public static Trajectory getRedTop2Rev() {
        return getTrajectory("RedTop2Rev.wpilib.json");
    }
    public static Trajectory getRedTop2For() {
        return getTrajectory("RedTop2For.wpilib.json");
    }
    public static Trajectory getRedTop3Rev() {
        return getTrajectory("RedTop3Rev.wpilib.json");
    }
    public static Trajectory getRedTop3For() {
        return getTrajectory("RedTop3For.wpilib.json");
    }
}
