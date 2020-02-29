/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;



import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallCollector extends SubsystemBase {

  private final WPI_TalonSRX Collect = new WPI_TalonSRX(Constants.CAN_ID.COLLECT);

  private boolean isOn = false;

  /**
   * Creates a new BallCollector.
   */
  public BallCollector() {
  }

  public boolean isOn(){
    return isOn;
  }

  public void collect(){

    Collect.set(-0.75);
    isOn = true;
  }

  public void stop(){
    Collect.set(0);
    isOn = false;
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
