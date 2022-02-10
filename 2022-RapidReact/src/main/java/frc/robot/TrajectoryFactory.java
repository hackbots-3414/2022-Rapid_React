// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;

/** Add your docs here. */
public class TrajectoryFactory {
    private static final String BASE_PATH = "paths/";
    private static final TrajectoryFactory me = new TrajectoryFactory();
    private static final Logger LOG = LoggerFactory.getLogger(TrajectoryFactory.class);
    private Trajectory testPath1;
    
    private TrajectoryFactory() {
        
    }

    private Trajectory loadTrajectory(String fileName) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(BASE_PATH+fileName);
            return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + BASE_PATH+fileName, ex.getStackTrace());
            LOG.error("Failed to load Trajectory path {}, error: {}", BASE_PATH + fileName, ex);
        }
        return null;
    }

    public static TrajectoryFactory getInstance() {
        return me;
    }
    
    public Trajectory getTestPath1() {
        if (testPath1 == null) {
            testPath1 = loadTrajectory("TestPath1.path");
        }
        return testPath1;
    }
}
