/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColorWheelSubsystem extends SubsystemBase {

  private final Solenoid raiser = new Solenoid(Constants.PCM_ID.COLOR_RAISE_TUBE);
  private final WPI_TalonSRX spinner = new WPI_TalonSRX(Constants.CAN_ID.SPINNER);
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);
  private final ColorMatch colorMatch = new ColorMatch();
  private Color prevColor = null;
  private int count = 0;

  /**
   * Creates a new ColorWheel.
   */
  public ColorWheelSubsystem() {

    colorMatch.addColorMatch(Constants.COLORS.BLUE);
    colorMatch.addColorMatch(Constants.COLORS.RED);
    colorMatch.addColorMatch(Constants.COLORS.GREEN);
    colorMatch.addColorMatch(Constants.COLORS.YELLOW);

  }

  public void raise() {

    raiser.set(true);

  }

  public void lower() {

    raiser.set(false);

  }

  public void toggle() {
    if (raiser.get()) {
      lower();
    } else {
      raise();
    }
  }

  public void spin() {

    spinner.set(.5);

  }

  public void stopSpin() {

    spinner.set(0);

  }

  public void toggleSpin() {

    if (Math.abs(spinner.get()) <= 0.25) {
      spin();
    } else {
      stopSpin();
    }

  }

  public Color getColor() {

    return colorMatch.matchClosestColor(colorSensor.getColor()).color;

  }

  public void resetColor() {

    count = 0;
    prevColor = null;

  }

  public void countColor() {
    Color result = getColor();
    if (result != prevColor) {
      count++;
      prevColor = result;
    }
  }

  public void report() {
    Color result = getColor();

    String textString = "No Detection";

    if (result == Constants.COLORS.BLUE) {
      textString = "Blue";
    } else if (result == Constants.COLORS.GREEN) {
      textString = "Green";
    } else if (result == Constants.COLORS.RED) {
      textString = "Red";
    } else if (result == Constants.COLORS.YELLOW) {
      textString = "Yellow";
    }

    SmartDashboard.putString("Color", textString);
    SmartDashboard.putNumber("Color Count ", count);

  }

  @Override
  public void periodic() {
    report();

    // This method will be called once per scheduler run
  }
}
