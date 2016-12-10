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
    public void start(Stage stage) throws Exception {
        stage.setTitle("Line Chart Sample");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Iteration");
        yAxis.setLabel("Function :3");
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        List<XYChart.Data<Number, Number>> data = whenUsingHammingDistance().getStatistics().stream()
                .map(point -> new XYChart.Data<Number, Number>(point.getIteration(), point.getValue()))
                .collect(Collectors.toList());

        Scene scene  = new Scene(lineChart,800,600);
        series.getData().addAll(data);
        lineChart.getData().add(series);
        stage.setScene(scene);
        stage.show();
    }

    private static final String optimal = "abcdefghijklmnopqrstuvwxyz";

    private static double hammingDistance(String first, String second) {
        if (first.length() != second.length())
            return -1;

        int res = 0;
        for (int i = 0; i < first.length(); i++)
            res += (first.charAt(i) == second.charAt(i)) ? 0 : 1;
        return res;
    }

    private static String mutate(String str) {
        Random r1 = new Random();
        int position = r1.nextInt(str.length());

        Random r2 = new Random();
        char ch = (char) (r2.nextInt(26) + 'a');

        StringBuilder newStr = new StringBuilder(str);
        newStr.setCharAt(position, ch);

        return newStr.toString();
    }

    public Statistics whenUsingHammingDistance() {
        SimulatedAnnealingSolver.Builder<String> solverBuilder = SimulatedAnnealingSolver.getBuilder();
        SimulatedAnnealingSolver<String> solver = solverBuilder
                .setGenerateChild(MainApplication::mutate)
                .setObjectiveFunction( (String str) -> hammingDistance(str, optimal))
                .setStartingTemperature(10.0).setAlpha(0.97).build();
        String solution = solver.solve("kmgniwsodfhydmsferinoqtngh", 2500);
        return solver.getStatistics();
    }
}
