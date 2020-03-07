/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command.colorwheel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.ColorWheelSubsystem;

public class CountColorCommand extends CommandBase {

  private final ColorWheelSubsystem mColorWheelSubsystem;
  private final int mNumberOfColors;
  /**
   * Creates a new CountColorCommand.
   */
  public CountColorCommand(ColorWheelSubsystem colorWheelSubsystem, int numberOfColors) {

    mColorWheelSubsystem = colorWheelSubsystem;
    mNumberOfColors = numberOfColors;


    // addRequirements(mColorWheelSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    mColorWheelSubsystem.countColor();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return mColorWheelSubsystem.getCount() >= mNumberOfColors;
  }
}
