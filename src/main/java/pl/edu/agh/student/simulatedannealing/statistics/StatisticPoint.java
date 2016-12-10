package pl.edu.agh.student.simulatedannealing.statistics;

/**
 * Created by pingwin on 10.12.16.
 */
public class StatisticPoint {
    private final int iteration;
    private final double value;

    public StatisticPoint(int iteration, double value) {
        this.iteration = iteration;
        this.value = value;
    }

    public int getIteration() {
        return iteration;
    }

    public double getValue() {
        return value;
    }
}
