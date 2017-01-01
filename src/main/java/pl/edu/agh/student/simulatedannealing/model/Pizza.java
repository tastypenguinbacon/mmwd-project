package pl.edu.agh.student.simulatedannealing.model;

import pl.edu.agh.student.simulatedannealing.util.Point;

import java.io.Serializable;

/**
 * Created by pingwin on 01.01.17.
 */

public class Pizza implements Serializable {
    private int timeUntilCold = 15;
    private final Point position;
    private final Point destination;

    public Pizza(Point position, Point destination) {
        this.position = position;
        this.destination = destination;
    }

    public int getTimeUntilCold() {
        return timeUntilCold;
    }

    public void setTimeUntilCold(int timeUntilCold) {
        this.timeUntilCold = timeUntilCold;
    }

    public Point getPosition() {
        return position;
    }

    public Point getDestination() {
        return destination;
    }
}
