/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utilities;

public class DriveSubsystem extends SubsystemBase {

  private final Translation2d frontLeftLocation = new Translation2d(Utilities.inchesToMeters(12), Utilities.inchesToMeters(10.25));
  private final Translation2d frontRightLocation = new Translation2d(Utilities.inchesToMeters(12), Utilities.inchesToMeters(-10.25));
  private final Translation2d rearLeftLocation = new Translation2d(Utilities.inchesToMeters(-12), Utilities.inchesToMeters(10.25));
  private final Translation2d rearRightLocation = new Translation2d(Utilities.inchesToMeters(-12), Utilities.inchesToMeters(-10.25));

  private final SwerveModule frontLeftModule = new SwerveModule(Constants.CAN_ID.FRONT_LEFT_DRIVE,
      Constants.CAN_ID.FRONT_LEFT_TURN, Constants.FRONT_LEFT_ABS_ENCODER_ID, Rotation2d.fromDegrees(239.633765));
  private final SwerveModule frontRightModule = new SwerveModule(Constants.CAN_ID.FRONT_RIGHT_DRIVE,
      Constants.CAN_ID.FRONT_RIGHT_TURN, Constants.FRONT_RIGHT_ABS_ENCODER_ID, Rotation2d.fromDegrees(275.350314));
  private final SwerveModule rearLeftModule = new SwerveModule(Constants.CAN_ID.REAR_LEFT_DRIVE,
      Constants.CAN_ID.REAR_LEFT_TURN, Constants.REAR_LEFT_ABS_ENCODER_ID, Rotation2d.fromDegrees(217.792946));
  private final SwerveModule rearRightModule = new SwerveModule(Constants.CAN_ID.REAR_RIGHT_DRIVE,
      Constants.CAN_ID.REAR_RIGHT_TURN, Constants.REAR_RIGHT_ABS_ENCODER_ID, Rotation2d.fromDegrees(231.020484));

  private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(frontLeftLocation, frontRightLocation,
      rearRightLocation, rearLeftLocation);

  /**
   * Creates a new SwerveDriveSubsystem.
   */
  public DriveSubsystem() {

  }

  public void initialize() {
    frontLeftModule.initializeTurnEncoder();
    rearLeftModule.initializeTurnEncoder();
    frontRightModule.initializeTurnEncoder();
    rearRightModule.initializeTurnEncoder();
  }

  private double linearSpeed(double unitRate) {
    return Math.pow(unitRate, 3.0) * SwerveModule.MAX_SPEED_METERS_PER_SECOND;
  }

  private double angularSpeed(double unitRate) {
    return Math.pow(unitRate, 3.0) * 2 * Math.PI;
  }

  public void drive(double forwardRate, double rightRate, double rotationRate) {
    SwerveModuleState[] states = kinematics.toSwerveModuleStates(
        new ChassisSpeeds(linearSpeed(forwardRate), linearSpeed(rightRate), angularSpeed(rotationRate)));

    SwerveDriveKinematics.normalizeWheelSpeeds(states, SwerveModule.MAX_SPEED_METERS_PER_SECOND);
    frontLeftModule.setState(states[1]);
    frontRightModule.setState(states[0]);
    rearRightModule.setState(states[3]);
    rearLeftModule.setState(states[2]);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
