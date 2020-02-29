/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private final CANSparkMax Shooter = new CANSparkMax(Constants.CAN_ID.SHOOTER, MotorType.kBrushless);
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    Shooter.setInverted(true);
  }

  public void shoot(){
    Shooter.set(1);
  }

  public void stop(){
    Shooter.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
