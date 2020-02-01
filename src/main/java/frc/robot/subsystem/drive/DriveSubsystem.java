/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  private final double shooterY = inchesToMeters(-9.248);

  private final Translation2d frontLeftLocation = new Translation2d(inchesToMeters(-10),
      inchesToMeters(-4.405) - shooterY);
  private final Translation2d frontRightLocation = new Translation2d(inchesToMeters(10),
      inchesToMeters(-4.405) - shooterY);
  private final Translation2d rearLeftLocation = new Translation2d(inchesToMeters(-10),
      inchesToMeters(-27.905) - shooterY);
  private final Translation2d rearRightLocation = new Translation2d(inchesToMeters(10),
      inchesToMeters(-27.905) - shooterY);

  private final SwerveModule frontLeftModule = new SwerveModule(Constants.FRONT_LEFT_DRIVE_CAN_ID,
      Constants.FRONT_LEFT_TURN_CAN_ID, Constants.FRONT_LEFT_ABS_ENCODER_ID);
  private final SwerveModule frontRightModule = new SwerveModule(Constants.FRONT_RIGHT_DRIVE_CAN_ID,
      Constants.FRONT_RIGHT_TURN_CAN_ID, Constants.FRONT_RIGHT_ABS_ENCODER_ID);
  private final SwerveModule rearLeftModule = new SwerveModule(Constants.REAR_LEFT_DRIVE_CAN_ID,
      Constants.REAR_LEFT_TURN_CAN_ID, Constants.REAR_LEFT_ABS_ENCODER_ID);
  private final SwerveModule rearRightModule = new SwerveModule(Constants.REAR_RIGHT_DRIVE_CAN_ID,
      Constants.REAR_RIGHT_TURN_CAN_ID, Constants.REAR_RIGHT_ABS_ENCODER_ID);

  private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation,
      rearLeftLocation, rearRightLocation);

  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
  
  }

  public void drive(double xSpeed, double ySpeed, double yawSpeed) {
    SwerveModuleState[] states = kinematics.toSwerveModuleStates(new ChassisSpeeds(xSpeed, ySpeed, yawSpeed));
    SwerveDriveKinematics.normalizeWheelSpeeds(states, 60);
    frontLeftModule.setState(states[0]);
    frontRightModule.setState(states[1]);
    rearLeftModule.setState(states[2]);
    rearRightModule.setState(states[3]);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private double inchesToMeters(double inches) {
    return inches * 2.54 / 100;
  }
}
