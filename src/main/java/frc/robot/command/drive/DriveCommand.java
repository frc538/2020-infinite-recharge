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
    // I think these are right or wrong.
    mDrive.drive(mJoystick.getY(), mJoystick.getX(), -mJoystick.getZ());
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
