/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.lib;

public class VisionSim {

    private double m_currentHeading;
    private boolean clockwiseDirection;

    public VisionSim() {
        m_currentHeading = 0;
        clockwiseDirection = true;
    }


    /*
        Resets the current heading value to the function parameter

        This simulates how far (in degress) the robot is initially
     */
    public void reset(double startHeading){
        m_currentHeading = startHeading;
        clockwiseDirection = m_currentHeading > 0 ? false : true;
    }

    /*
        Simulates the target getting closer
     */
    public void turn (double degrees){
        if (m_currentHeading != 0) {
            m_currentHeading = (clockwiseDirection) ? m_currentHeading + degrees : m_currentHeading - degrees;
        }
    }

    /*
        Returns the current delta between the target and the robot
     */
    public double getHeading(){
        return m_currentHeading;
    }
}
