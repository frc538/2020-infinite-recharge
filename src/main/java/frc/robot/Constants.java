/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class CAN_ID {
    
    public static final int FRONT_LEFT_DRIVE_CAN_ID = 2;
    public static final int FRONT_LEFT_TURN_CAN_ID = 1;
    public static final int FRONT_RIGHT_DRIVE_CAN_ID = 4;
    public static final int FRONT_RIGHT_TURN_CAN_ID = 3;
    public static final int REAR_LEFT_DRIVE_CAN_ID = 6;
    public static final int REAR_LEFT_TURN_CAN_ID = 5;
    public static final int REAR_RIGHT_DRIVE_CAN_ID = 7;
    public static final int REAR_RIGHT_TURN_CAN_ID = 8;
    public static final int SHOOTER = 9;    
    public static final int COLLECT = 3;
    public static final int WINCH_RIGHT = 1;
    public static final int WINCH_LEFT = 2;

    }

    public static class PCM_ID{
        
    public static final int THICC_TUBE = 4;
    public static final int SMOL_TUBE = 5;

    }

    public static class BUTTON_ID{
        
        public static final int SHOOTER = XboxController.Button.kA.value;
        public static final int COLLECT = XboxController.Button.kX.value;
        public static final int LIFT = XboxController.Button.kY.value;

        public static final int KEY = 4;
        public static final int RED_BUTTON = 1;

    }


    public static final int FRONT_LEFT_ABS_ENCODER_ID = 1;
    public static final int FRONT_RIGHT_ABS_ENCODER_ID = 0;
   
    public static final int REAR_LEFT_ABS_ENCODER_ID = 2;
    public static final int REAR_RIGHT_ABS_ENCODER_ID = 3;

    public static final int JOYSTICK_DRIVER_USB_ID = 0;
    public static final int XBOX_DRIVER_USB_ID = 1;

    public static final int BUTTON_USB_ID = 2;
    


}
