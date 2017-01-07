package pl.edu.agh.student.simulatedannealing.gui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.edu.agh.student.simulatedannealing.statistics.StatisticPoint;
import pl.edu.agh.student.simulatedannealing.statistics.Statistics;

/**
 * Created by pingwin on 05.01.17.
 */
public class StatisticsPlot extends LineChart<Number, Number> {
    public StatisticsPlot(Statistics statistics) {
        super(getAxis("Iteration count"), getAxis("Objective function value"));
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (StatisticPoint entry : statistics.getStatistics()) {
            XYChart.Data<Number, Number> dataEntry = new XYChart.Data<>(entry.getIteration(), entry.getValue());
            series.getData().add(dataEntry);
        }
        series.setName("Objective function values");
        setCreateSymbols(false);
        getData().add(series);
        setMinSize(720, 720);
    }

    private static Axis<Number> getAxis(String label) {
        Axis<Number> axis = new NumberAxis();
        axis.setLabel(label);
        return axis;
    }

    public void update(Statistics statistics) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        int maxIteration = Integer.MIN_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (StatisticPoint entry : statistics.getStatistics()) {
            if (entry.getValue() > maxValue) {
                maxValue = (int) entry.getValue();
                maxIteration = entry.getIteration();
            }
            XYChart.Data<Number, Number> dataEntry = new XYChart.Data<>(entry.getIteration(), entry.getValue());
            series.getData().add(dataEntry);
        }
        series.setName("Objective function values, max value is " + maxValue + " at iteration " + maxIteration + ".");
        setCreateSymbols(false);
        getData().clear();
        getData().add(series);
    }
}
