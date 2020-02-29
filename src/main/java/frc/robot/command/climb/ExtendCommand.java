/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.ClimberSubsystem;

public class ExtendCommand extends CommandBase {

  private final ClimberSubsystem mClimber;

  /**
   * Creates a new Extend.
   */
  public ExtendCommand(ClimberSubsystem climber) {
    mClimber = climber;
    addRequirements(mClimber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mClimber.extend();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
