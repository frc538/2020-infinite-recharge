/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class ColorWheelSubsystem extends SubsystemBase {

  private final Solenoid raiser = new Solenoid(Constants.PCM_ID.COLOR_RAISE_TUBE);
  private final WPI_TalonSRX spinner = new WPI_TalonSRX(Constants.CAN_ID.SPINNER);
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);

  /**
   * Creates a new ColorWheel.
   */
  public ColorWheelSubsystem() {

  }


  public void raise() {

    raiser.set(true);

  }

  public void lower() {

    raiser.set(false);

  }

  public void toggle() {
    if(raiser.get()){
      lower();
    } else {
      raise();
    }
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
