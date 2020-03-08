/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.command.climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.ClimberSubsystem;

public class LiftCommand extends CommandBase {

  private final ClimberSubsystem mClimber;
  private final boolean mLift;

  /**
   * Creates a new Lift.
   */
  public LiftCommand(ClimberSubsystem climber, boolean lift) {
    mClimber = climber;
    mLift = lift;
    addRequirements(mClimber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (mLift) {
      mClimber.climb();
    } else {
      mClimber.stop();
    }
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
