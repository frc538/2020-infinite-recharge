/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  private final double shooterY = inchesToMeters(-9.248);

  private final Translation2d frontLeftLocation = new Translation2d(inchesToMeters(-10), inchesToMeters(-4.405) -shooterY);
  private final Translation2d frontRightLocation = new Translation2d(inchesToMeters(10), inchesToMeters(-4.405) -shooterY);
  private final Translation2d rearLeftLocation = new Translation2d(inchesToMeters(-10), inchesToMeters(-27.905) -shooterY);
  private final Translation2d rearRightLocation = new Translation2d(inchesToMeters(10), inchesToMeters(-27.905) -shooterY);


  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private double inchesToMeters(double inches) {
    return inches * 2.54 / 100;
  }
}
