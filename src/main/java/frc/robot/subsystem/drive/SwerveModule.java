/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utilities;
import frc.robot.utils.AbsoluteEncoder;

public class SwerveModule {
  private static final double STEERING_RATIO = 18;
  private static final double RADIANS_PER_ROTATION = 2 * Math.PI;
  private static final double SECONDS_PER_MINUTE = 60;
  private static final double RADIUS_METERS = Utilities.inchesToMeters(2);
  private static final double STAGE_ONE_DRIVER = 42;
  private static final double STAGE_ONE_DRIVEN = 14;
  private static final double STAGE_TWO_DRIVER = 18;
  private static final double STAGE_TWO_DRIVEN = 26;
  private static final double STAGE_THREE_DRIVER = 60;
  private static final double STAGE_THREE_DRIVEN = 15;
  private static final double DRIVING_RATIO = (STAGE_ONE_DRIVER / STAGE_ONE_DRIVEN)
      * (STAGE_TWO_DRIVER / STAGE_TWO_DRIVEN) * (STAGE_THREE_DRIVER / STAGE_THREE_DRIVEN);
  private static final double DRIVE_VELOCITY_CONVERSION_FACTOR = RADIANS_PER_ROTATION / SECONDS_PER_MINUTE
      * RADIUS_METERS / DRIVING_RATIO;
  private static final double MAX_RPM = 5664;
  private static final double STEER_POSITION_CONVERSION_FACTOR = RADIANS_PER_ROTATION / STEERING_RATIO;

  public static double MAX_SPEED_METERS_PER_SECOND = MAX_RPM * DRIVE_VELOCITY_CONVERSION_FACTOR;

  private final CANSparkMax drive;
  private final CANSparkMax turn;
  private final CANEncoder driveEncoder;
  private final CANEncoder turnEncoder;
  private final CANPIDController driveController;
  private final CANPIDController turnController;
  private final AbsoluteEncoder absEncoder;

  private final int TURN_ID;

  private final Rotation2d mAngleOffset;

  public SwerveModule(int driveId, int turnId, int absEncId, Rotation2d angleOffset) {
    mAngleOffset = angleOffset;

    TURN_ID = turnId;

    drive = new CANSparkMax(driveId, MotorType.kBrushless);
    turn = new CANSparkMax(turnId, MotorType.kBrushless);

    drive.restoreFactoryDefaults();
    turn.restoreFactoryDefaults();


    driveEncoder = drive.getEncoder();
    turnEncoder = turn.getEncoder();

    driveEncoder.setVelocityConversionFactor(DRIVE_VELOCITY_CONVERSION_FACTOR);
    turnEncoder.setPositionConversionFactor(STEER_POSITION_CONVERSION_FACTOR);

    absEncoder = new AbsoluteEncoder(new AnalogInput(absEncId));

    initializeTurnEncoder();

    driveController = new CANPIDController(drive);
    turnController = new CANPIDController(turn);

    turnController.setOutputRange(-0.75, 0.75);

    driveController.setP(0.0001);
    driveController.setFF(0.000171);

    turnController.setP(0.08);
  }

  public void initializeTurnEncoder() {
    turnEncoder.setPosition((absEncoder.getAngle().getRadians() - mAngleOffset.getRadians()) % RADIANS_PER_ROTATION);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(turnEncoder.getPosition()));
  }

  public void setState(SwerveModuleState state) {
    double angleRadians = -state.angle.getRadians();
    double speedMs = state.speedMetersPerSecond;

    SmartDashboard.putNumber("Set Turn " + TURN_ID + " to ", (angleRadians) * 180 / Math.PI);
    SmartDashboard.putNumber("Current Turn " + TURN_ID , turnEncoder.getPosition() * 180 / Math.PI);
    SmartDashboard.putNumber("ABS ENC " + TURN_ID, absEncoder.getAngle().getDegrees());

    turnController.setReference(angleRadians, ControlType.kPosition);
    driveController.setReference(speedMs / DRIVE_VELOCITY_CONVERSION_FACTOR, ControlType.kVelocity);
  }
}
