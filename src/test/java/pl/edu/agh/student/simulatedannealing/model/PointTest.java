package pl.edu.agh.student.simulatedannealing.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Szymek on 2017-01-04.
 */
public class PointTest {
    @Test
    public void distanceSymmetryTest() {
        Point first = new Point(2, -5);
        Point second = new Point(0, 4);
        int original = first.distanceTo(second);
        int reversed = second.distanceTo(first);

        assertTrue(original == reversed);
    }

    @Test
    public void distanceMeasurementTest() {
        Point first = new Point(2, -5);
        Point second = new Point(0, 4);
        int distance = first.distanceTo(second);

        assertTrue(11 == distance);
    }

    @Test(expected = NullPointerException.class)
    public void distanceToNull() throws Exception {
        Point validPoint = new Point(-3, 7);
        Point nullPoint = null;
        validPoint.distanceTo(nullPoint);
    }

    @Test
    public void pointEqualityTest() {
        Point first = new Point(-5, 2);
        Point second = new Point(-5, 2);

        assertTrue(first.equals(second));
    }
}
