package com.dskimina.mapping;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by daniel on 09.08.16.
 */
@Entity
@Table(name = "Odczyty")
public class Odczyt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "ODCZYTY_SEQ", sequenceName = "ODCZYTY_SEQ")
    private int id;

    @Column(name = "date")
    @Temporal(value = TemporalType.DATE)
    private Date time;

    @Column(name = "predicted")
    private double predicted;

    @Column(name = "current")
    private double current;

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
}
