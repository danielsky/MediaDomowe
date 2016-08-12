package com.dskimina.mapping;

import java.util.Date;

/**
 * Created by daniel on 31.07.16.
 */
public class PradOdczyt {
    private Date time;
    private double predicted;
    private double current;
    private int difference;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getPredicted() {
        return predicted;
    }

    public void setPredicted(double predicted) {
        this.predicted = predicted;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }
}
