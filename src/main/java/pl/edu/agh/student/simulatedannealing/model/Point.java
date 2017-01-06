package pl.edu.agh.student.simulatedannealing.model;

import java.io.Serializable;

import static java.lang.Math.abs;

/**
 * Created by pingwin on 01.01.17.
 */
public class Point implements Serializable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    int distanceTo(Point other) {
        if (null == other) {
            throw new NullPointerException("You can only measure distance to another Point.");
        }
        return abs(this.x - other.x) + abs(this.y - other.y);
    }
}
