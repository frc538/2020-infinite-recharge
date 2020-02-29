/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private boolean isExtended = false;

  private final Solenoid thiccBimba = new Solenoid(Constants.PCM_ID.THICC_TUBE);
  private final Solenoid smolBimba = new Solenoid(Constants.PCM_ID.SMOL_TUBE);

  private final WPI_TalonSRX leftWinch = new WPI_TalonSRX(Constants.CAN_ID.WINCH_LEFT);
  private final WPI_TalonSRX rightWinch = new WPI_TalonSRX(Constants.CAN_ID.WINCH_RIGHT);

  /**
   * Creates a new Climber.
   */
  public Climber() {
    
    // retract();
    rightWinch.follow(leftWinch);
    
  }

  public boolean isExtended(){
    return isExtended;
  }

  public void extend(){

    smolBimba.set(true);
    thiccBimba.set(true);
    isExtended = true;

  }

  public void retract(){

    smolBimba.set(false);
    thiccBimba.set(false);
    isExtended = false;

  }

  public void climb(){

    leftWinch.set(0.75);

  }

  public void stop(){

    leftWinch.set(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
