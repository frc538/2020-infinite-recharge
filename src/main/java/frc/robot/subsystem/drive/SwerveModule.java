/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class SwerveModule {
    private static final int SLOT_ID = 0;
    private static final double MAX_ANG_VEL = Math.PI;
    private static final double MAX_ANG_ACC = 2*Math.PI;
    private static final double WHEEL_DIAM = 4*2.54/100;

    private final CANSparkMax drive;
    private final CANSparkMax turn;
    private final CANEncoder driveEncoder;
    private final CANEncoder turnEncoder;
    private final AnalogEncoder absEncoder;
    private final CANPIDController driveController;
    private final CANPIDController turnController;

    public SwerveModule(int driveId, int turnId, int absEncId) {
        drive = new CANSparkMax(driveId, MotorType.kBrushless);
        turn = new CANSparkMax(turnId, MotorType.kBrushless);
        
        driveEncoder = drive.getEncoder();
        driveEncoder.setPositionConversionFactor(Math.PI*WHEEL_DIAM);
        driveEncoder.setVelocityConversionFactor(Math.PI*WHEEL_DIAM/60);

        turnEncoder = turn.getEncoder();
        turnEncoder.setPositionConversionFactor(2*Math.PI);
        turnEncoder.setVelocityConversionFactor(2*Math.PI/60);

        absEncoder = new AnalogEncoder(new AnalogInput(absEncId));
        absEncoder.setDistancePerRotation(2*Math.PI);

        turnEncoder.setPosition(absEncoder.getDistance());

        driveController = new CANPIDController(drive);
        driveController.setP(1);

        turnController = new CANPIDController(turn);
        turnController.setP(1);
        turnController.setSmartMotionAccelStrategy(CANPIDController.AccelStrategy.kTrapezoidal, SLOT_ID);
        turnController.setSmartMotionMaxAccel(MAX_ANG_ACC, SLOT_ID);
        turnController.setSmartMotionMaxVelocity(MAX_ANG_VEL, SLOT_ID);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(turnEncoder.getPosition()));
    }

}
