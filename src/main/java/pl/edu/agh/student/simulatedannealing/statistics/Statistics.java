package pl.edu.agh.student.simulatedannealing.statistics;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pingwin on 10.12.16.
 */
public class Statistics {
    private List<StatisticPoint> data = new LinkedList<>();

    public void add(int step, double value) {
        data.add(new StatisticPoint(step, value));
    }

    public List<StatisticPoint> getStatistics() {
        return new LinkedList<>(data);
    }
}
