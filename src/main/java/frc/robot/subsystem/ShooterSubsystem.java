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

public class ShooterSubsystem extends SubsystemBase {

  private final CANSparkMax shooter = new CANSparkMax(Constants.CAN_ID.SHOOTER, MotorType.kBrushless);
  private boolean isOn = false;

  /**
   * Creates a new Shooter.
   */
  public ShooterSubsystem() {
    shooter.setInverted(true);
  }

  

  public void shoot() {
    shooter.set(1);
    isOn = true;
  }

  public void stop() {
    shooter.set(0);
    isOn = false;
  }

  public void toggleShoot(){

    if(isOn){
      stop();
    } else{
      shoot();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
