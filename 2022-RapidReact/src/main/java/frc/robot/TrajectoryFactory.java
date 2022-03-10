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
            case "3BallAutonBall2":
                return getTrajectory("3BallAutonBall2.wpilib.json");
            case "3BallAutonBall3For":
                return getTrajectory("3BallAutonBall3For.wpilib.json");
            case "3BallAutonBall3Rev":
                return getTrajectory("3BallAutonBall3Rev.wpilib.json");
            case "3BallAutonShoot2":
                return getTrajectory("3BallAutonShoot2.wpilib.json");
            case "3BallAutonBackup":
                return getTrajectory("3BallAutonBackup.wpilib.json");
            case "BlueBottom2For":
                return getTrajectory("BlueBottom2For.wpilib.json");
            case "BlueBottom2Rev":
                return getTrajectory("BlueBottom2Rev.wpilib.json");
            default:
                return getTrajectory("TestPathSmooth.wpilib.json");

        }
    }
}
