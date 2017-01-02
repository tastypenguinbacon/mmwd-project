package pl.edu.agh.student.simulatedannealing.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import pl.edu.agh.student.simulatedannealing.solver.SimulatedAnnealingSolver;
import pl.edu.agh.student.simulatedannealing.statistics.Statistics;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by pingwin on 10.12.16.
 */
public class MainApplication extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
