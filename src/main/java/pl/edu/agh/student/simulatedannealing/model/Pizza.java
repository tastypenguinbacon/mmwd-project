package pl.edu.agh.student.simulatedannealing.model;

import pl.edu.agh.student.simulatedannealing.util.Point;

import java.io.Serializable;

/**
 * Created by pingwin on 01.01.17.
 */

public class Pizza implements Serializable {
    private int timeUntilCold = 15;
    private Point position;
    private Point destination;

    public Pizza(Point position, Point destination) {
        this.position = position;
        this.destination = destination;
    }

    public Pizza() {}

    public int getTimeUntilCold() {
        return timeUntilCold;
    }

    public void setTimeUntilCold(int timeUntilCold) {
        this.timeUntilCold = timeUntilCold;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;

        if (timeUntilCold != pizza.timeUntilCold) return false;
        if (position != null ? !position.equals(pizza.position) : pizza.position != null) return false;
        return destination != null ? destination.equals(pizza.destination) : pizza.destination == null;
    }

    @Override
    public int hashCode() {
        int result = timeUntilCold;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}
