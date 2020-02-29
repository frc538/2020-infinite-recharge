/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utilities;

public class DriveSubsystem extends SubsystemBase {

    private final double shooterY = Utilities.inchesToMeters(-9.248);

    private final Translation2d frontLeftLocation = new Translation2d(Utilities.inchesToMeters(-10),
            Utilities.inchesToMeters(-4.405) - shooterY);
    private final Translation2d frontRightLocation = new Translation2d(Utilities.inchesToMeters(10),
            Utilities.inchesToMeters(-4.405) - shooterY);
    private final Translation2d rearLeftLocation = new Translation2d(Utilities.inchesToMeters(-10),
            Utilities.inchesToMeters(-27.905) - shooterY);
    private final Translation2d rearRightLocation = new Translation2d(Utilities.inchesToMeters(10),
            Utilities.inchesToMeters(-27.905) - shooterY);

    private final SwerveModule frontLeftModule = new SwerveModule(Constants.CAN_ID.FRONT_LEFT_DRIVE,
            Constants.CAN_ID.FRONT_LEFT_TURN, Constants.FRONT_LEFT_ABS_ENCODER_ID, 25.80);
    private final SwerveModule frontRightModule = new SwerveModule(Constants.CAN_ID.FRONT_RIGHT_DRIVE,
            Constants.CAN_ID.FRONT_RIGHT_TURN, Constants.FRONT_RIGHT_ABS_ENCODER_ID, 99.70);
    private final SwerveModule rearLeftModule = new SwerveModule(Constants.CAN_ID.REAR_LEFT_DRIVE,
            Constants.CAN_ID.REAR_LEFT_TURN, Constants.REAR_LEFT_ABS_ENCODER_ID, 215.45);
    private final SwerveModule rearRightModule = new SwerveModule(Constants.CAN_ID.REAR_RIGHT_DRIVE,
            Constants.CAN_ID.REAR_RIGHT_TURN, Constants.REAR_RIGHT_ABS_ENCODER_ID, 296.00);

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation,
            rearLeftLocation, rearRightLocation);

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem() {

    }

    public void drive(double xSpeed, double ySpeed, double yawSpeed) {
        xSpeed *= 4000;
        ySpeed *= 4000;
        SwerveModuleState[] states = kinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, 0));
        // SwerveDriveKinematics.normalizeWheelSpeeds(states, 20);
        frontLeftModule.putData();
        frontRightModule.putData();
        rearLeftModule.putData();
        rearRightModule.putData();

        frontLeftModule.setState(states[0]);
        frontRightModule.setState(states[1]);
        rearLeftModule.setState(states[2]);
        rearRightModule.setState(states[3]);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // SmartDashboard.putNumber("Front Left Position:",
        // frontLeftModule.getState().angle.getDegrees());
        // SmartDashboard.putNumber("Front Right Position:",
        // frontRightModule.getState().angle.getDegrees());
        // SmartDashboard.putNumber("Rear Left Position:",
        // rearLeftModule.getState().angle.getDegrees());
        // SmartDashboard.putNumber("Rear Right Position:",
        // rearRightModule.getState().angle.getDegrees());
    }
}
