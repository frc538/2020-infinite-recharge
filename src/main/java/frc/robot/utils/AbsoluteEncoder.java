/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

/**
 * Add your docs here.
 */
public class AbsoluteEncoder {
    
    private static final double VOLTAGE_RANGE = 5;
    private static final int READINGS = 10;
    private final AnalogInput mInput;
    private final double mScale;

    private final List<Double> voltages = new ArrayList();

    private AbsoluteEncoder(AnalogInput input, double scale) {
        
        mInput = input;
        mScale = scale;

    }

    public AbsoluteEncoder(AnalogInput input) {
        
        this(input, 1);

    }

    private double smoothVoltageRead() {

        voltages.clear();
        double total = 0;

        for(int i = 0; i < READINGS; i++){

            double current = mInput.getVoltage();
            voltages.add(current);
            total += current;

        }

        total -= Collections.min(voltages);
        total -= Collections.max(voltages);

        return total / (READINGS - 2);

    }

    public Rotation2d getAngle() {

        double voltage = smoothVoltageRead();
        double angleRads = voltage * (((2 * Math.PI) / VOLTAGE_RANGE) / mScale);
        return new Rotation2d(angleRads); 

    }

}
