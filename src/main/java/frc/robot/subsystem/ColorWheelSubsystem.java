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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ColorWheelSubsystem extends SubsystemBase {
  private final double MAX_SPEED = 0.25;
  private final int PROXIMITY_THRESHOLD = 180;

  private final Solenoid raiser = new Solenoid(Constants.PCM_ID.COLOR_RAISE_TUBE);
  private final WPI_TalonSRX spinner = new WPI_TalonSRX(Constants.CAN_ID.SPINNER);
  private final ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);
  private final ColorMatch colorMatch = new ColorMatch();
  private Color prevColor = null;
  private int count = 0;
  private Color mFMSColor = null;

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
    if (colorSensor.getProximity() < PROXIMITY_THRESHOLD) {
      raiser.set(false);
    }
  }

  public void toggle() {
    if (raiser.get()) {
      lower();
    } else {
      raise();
    }
  }

  public void spin() {

    spinner.set(MAX_SPEED);

  }

  public void stopSpin() {

    spinner.set(0);

  }

  public void toggleSpin() {

    if (Math.abs(spinner.get()) <= MAX_SPEED / 2) {
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

  private Color getFMSColor(){

    String gameData = DriverStation.getInstance().getGameSpecificMessage();

    if(gameData.isEmpty()){
      return null;
    }

    Color value = null;

    switch (gameData.charAt(0)){

      case 'R' : 
        value = Constants.COLORS.RED;
        break;

      case 'G' :
        value = Constants.COLORS.GREEN;
        break;
      
      case 'B' :
        value = Constants.COLORS.BLUE;
        break;
      
      case 'Y' :
        value = Constants.COLORS.YELLOW;
        break;
    }

    return value;

  }

  public Color sensedColor(){

    Color retVal = null;

    if(mFMSColor == Constants.COLORS.RED){
      retVal = Constants.COLORS.BLUE;
    } else if(mFMSColor == Constants.COLORS.BLUE) {
      retVal = Constants.COLORS.RED;
    } else if(mFMSColor == Constants.COLORS.YELLOW) {
      retVal = Constants.COLORS.GREEN;
    } else if(mFMSColor == Constants.COLORS.GREEN) {
      retVal = Constants.COLORS.YELLOW;
    }

    return retVal; 

  }

  public void countColor() {
    Color result = getColor();
    if (result != prevColor) {
      count++;
      prevColor = result;
    }
  }

  public void report() {
    String currentColor = colorToString(getColor());
    String fmsColor = colorToString(mFMSColor);
    String colorToSense = colorToString(sensedColor());
    String fmsData = DriverStation.getInstance().getGameSpecificMessage();
    
    SmartDashboard.putString("Current Color", currentColor);
    SmartDashboard.putString("FMS Color Retrieved", fmsColor);
    SmartDashboard.putString("Color To Sense", colorToSense);
    SmartDashboard.putString("FMS Data", fmsData);
    SmartDashboard.putNumber("Color Count ", count);
    SmartDashboard.putNumber("Distance", colorSensor.getProximity());
  }

  private String colorToString(Color color) {
    String textString = "No Color";

    if (color == Constants.COLORS.BLUE) {
      textString = "Blue";
    } else if (color == Constants.COLORS.GREEN) {
      textString = "Green";
    } else if (color == Constants.COLORS.RED) {
      textString = "Red";
    } else if (color == Constants.COLORS.YELLOW) {
      textString = "Yellow";
    }

    return textString;
  }

  public int getCount() {
    return count;
  }

  @Override
  public void periodic() {
    
    Color fMSColor = getFMSColor();

    if(fMSColor != null && fMSColor != mFMSColor) {
      mFMSColor = fMSColor;
    }
    
    report();

    // This method will be called once per scheduler run
  }
}
