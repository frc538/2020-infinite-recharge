/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utilities;

/**
 * Add your docs here.
 */
public class SwerveModule {
    private static final double WHEEL_RADIUS = Utilities.inchesToMeters(2);

    private final CANSparkMax drive;
    private final CANSparkMax turn;
    private final CANEncoder driveEncoder;
    private final CANEncoder turnEncoder;
    private final AnalogEncoder absEncoder;
    private final CANPIDController driveController;
    private final CANPIDController turnController;
    private final Rotation2d mAngleOffset;

    public final int TURN_ID;

    public SwerveModule(int driveId, int turnId, int absEncId, Rotation2d angleOffset) {

        mAngleOffset = angleOffset;

        drive = new CANSparkMax(driveId, MotorType.kBrushless);
        turn = new CANSparkMax(turnId, MotorType.kBrushless);

        TURN_ID = turnId;

        drive.restoreFactoryDefaults();
        turn.restoreFactoryDefaults();

        driveEncoder = drive.getEncoder();
        driveEncoder.setVelocityConversionFactor(Math.PI * WHEEL_RADIUS / 30);
        absEncoder = new AnalogEncoder(new AnalogInput(absEncId));
        // absEncoder.reset();
        absEncoder.setDistancePerRotation(2*Math.PI);

        turnEncoder = turn.getEncoder();
        turnEncoder.setPositionConversionFactor(2 * Math.PI / 18);

        init();
    
        driveController = new CANPIDController(drive);
        turnController = new CANPIDController(turn);

        driveController.setP(0.0001);
        driveController.setFF(0.000171);

        turnController.setP(0.08);
    }

    public void init() {

        // absEncoder.reset();
        turnEncoder.setPosition((absEncoder.getDistance() - mAngleOffset.getRadians())  % (2 * Math.PI));
        
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(turnEncoder.getPosition()));
    }

    public void setState(SwerveModuleState state) {
        driveController.setReference(state.speedMetersPerSecond, ControlType.kVelocity);

        turnController.setReference(state.angle.getRadians(), ControlType.kPosition);
        putData();
    }

    public void putData() {
        SmartDashboard.putNumber("Abs Encoder " + TURN_ID + ":", (absEncoder.getDistance() % (2 * Math.PI)) * 180 / Math.PI );
        SmartDashboard.putNumber("Turn Encoder" + TURN_ID + ":", turnEncoder.getPosition() * 180 / Math.PI);
    }

}
