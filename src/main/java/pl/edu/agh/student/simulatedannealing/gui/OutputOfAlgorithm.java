package pl.edu.agh.student.simulatedannealing.gui;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;

/**
 * Created by pingwin on 05.01.17.
 */
public class OutputOfAlgorithm extends ScatterChart<Number, Number> {
    public OutputOfAlgorithm() {
        super(new NumberAxis(0, 50, 1), new NumberAxis(0, 50, 1));
        setMinSize(720, 720);
    }
}
