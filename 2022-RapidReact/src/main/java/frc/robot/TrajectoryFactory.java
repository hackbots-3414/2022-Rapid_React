// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.PathweaverConstants;
import frc.robot.Constants.RobotConstants;

/** Add your docs here. */
public class TrajectoryFactory {
    private static final String BASE_PATH = "paths/";
    private static final HashMap<String, Trajectory> pathCache = new HashMap<>();

    private TrajectoryFactory() {
    }

    // private static Trajectory loadTrajectory(String fileName) {
    //     try {
    //         Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(BASE_PATH + fileName);
    //         return TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    //     } catch (IOException ex) {
    //         DriverStation.reportError("Unable to open trajectory: " + BASE_PATH + fileName, ex.getStackTrace());
    //     }
    //     return null;
    // }

    private static Trajectory loadTrajectory(String fileName, TrajectoryConfig trajectoryConfig) {
        try {
            Trajectory trajectory = TrajectoryGenerator.generateTrajectory(WaypointReader.getControlVectors(fileName), trajectoryConfig);
            return trajectory;
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + BASE_PATH + fileName, ex.getStackTrace());
        }
        return null;
    }

    public static Trajectory getPath(String name, Boolean isReversed) {
        TrajectoryConfig config = new TrajectoryConfig(PathweaverConstants.kMaxSpeed, PathweaverConstants.kMaxAcceleration);
        DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(PathweaverConstants.ksVolts, PathweaverConstants.kvVoltSecondsPerMeter, PathweaverConstants.kaVoltSecondsSquaredPerMeter), RobotConstants.kDriveKinematics, 10);
        config.addConstraint(new CentripetalAccelerationConstraint(PathweaverConstants.kMaxSpinAcceleration));
        config.setEndVelocity(PathweaverConstants.kMaxEndSpeed);
        config.setKinematics(RobotConstants.kDriveKinematics);
        config.setReversed(isReversed);
        config.addConstraint(autoVoltageConstraint);
        Trajectory trajectory = loadTrajectory(name, config);
        return trajectory;
    }

    // public static Trajectory getPath(String name) {
    //     return getTrajectory(name + ".wpilib.json");
    // }
}
