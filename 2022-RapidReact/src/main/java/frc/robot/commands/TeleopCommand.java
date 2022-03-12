package frc.robot.commands;

import org.strykeforce.thirdcoast.util.ExpoScale;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.CompControllerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

public class TeleopCommand extends CommandBase {

    private static final double RIGHTX_DEADBAND = CompControllerConstants.right_x_offset;
    private static final double LEFTY_DEADBAND = CompControllerConstants.left_y_offset;

    private static final double RIGHTX_XPOSCALE = CompControllerConstants.right_x_max;
    private static final double LEFTY_XPOSCALE = CompControllerConstants.left_y_max;

    private final ExpoScale leftHaloScale;
    private final ExpoScale rightHaloScale;

    private Drivetrain drivetrain;

    public TeleopCommand(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;

        leftHaloScale = new ExpoScale(LEFTY_DEADBAND, LEFTY_XPOSCALE);
        rightHaloScale = new ExpoScale(RIGHTX_DEADBAND, RIGHTX_XPOSCALE);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double leftHalo = leftHaloScale.apply(OI.getLeftVerticalRaw());
        double rightHalo = rightHaloScale.apply(OI.getRightLateralRaw());
        double vleftHalo = leftHalo * DriveConstants.kMaxSpeed;
        double vrightHalo = rightHalo * DriveConstants.kMaxSpeed;

        if (OI.getButtonB() == 0) {
            drivetrain.arcadeDrive(vleftHalo, vrightHalo);
        } /* else if (OI.getButtonB() == 1) {
            drivetrain.arcadeDrive(vleftArcade, vrightArcade);
        } else if (OI.getButtonB() == 2) {
            drivetrain.tankDrive(vleftTank, vrightTank);
        } */
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
