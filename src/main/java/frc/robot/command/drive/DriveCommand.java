/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.drive.DriveSubsystem;

public class DriveCommand extends CommandBase {
  private final DriveSubsystem mDrive;
  private final Joystick mJoystick;
  private final double SMALL_BAND_DEGREES = 5;

  /**
   * Creates a new DriveCommand.
   */
  public DriveCommand(DriveSubsystem drive, Joystick joystick) {
    // Use addRequirements() here to declare subsystem dependencies.
    mDrive = drive;
    mJoystick = joystick;
    addRequirements(mDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double joystickForward = -mJoystick.getY();
    double joystickRight = mJoystick.getX();

    double degrees = mJoystick.getDirectionDegrees();
    double magnitude = mJoystick.getMagnitude();

    if (degrees >= 180 - (SMALL_BAND_DEGREES) || degrees <= -180 + (SMALL_BAND_DEGREES)) {
      // BACK
      joystickForward = -magnitude;
      joystickRight = 0;
    } else if (degrees <= -90 + (SMALL_BAND_DEGREES / 2) && degrees >= -90 - (SMALL_BAND_DEGREES / 2)) {
      // LEFT
      joystickForward = 0;
      joystickRight = -magnitude;
    } else if  (degrees >= -(SMALL_BAND_DEGREES / 2) && degrees <= (SMALL_BAND_DEGREES / 2)) {
      // FORWARD
      joystickForward = magnitude;
      joystickRight = 0;
    } else if (degrees >= 90 - (SMALL_BAND_DEGREES / 2) && degrees <= 90 + (SMALL_BAND_DEGREES / 2)) {
      // RIGHT
      joystickForward = 0;
      joystickRight = magnitude;
    }

    double rotation = mJoystick.getZ();
    if(magnitude > 0.2) {
      rotation = 0;
    }



    mDrive.drive(joystickForward, joystickRight, rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
