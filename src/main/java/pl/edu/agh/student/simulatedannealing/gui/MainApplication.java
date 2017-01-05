package pl.edu.agh.student.simulatedannealing.gui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.agh.student.simulatedannealing.statistics.Statistics;

import java.io.InputStream;
import java.util.function.Function;

import static java.lang.Math.exp;
import static java.lang.Math.sin;

/**
 * Created by pingwin on 10.12.16.
 */
public class MainApplication extends Application {
    private StatisticsPlot statisticsPlot = getStatisticsOfObjectiveFunction();
    private OutputOfAlgorithm algorithmOutput = getAlgorithmOutput();
    private VBox controlPane = getControlPane();

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        HBox verticalLayout = new HBox(20);
        verticalLayout.getChildren().add(controlPane);
        verticalLayout.getChildren().add(algorithmOutput);
        verticalLayout.getChildren().add(statisticsPlot);
        stage.setScene(new Scene(verticalLayout));
        stage.setResizable(false);
        stage.show();
    }

    private VBox getControlPane() {
        VBox controlPane = new VBox(50);
        controlPane.setPadding(new Insets(50, 20, 50, 20));
        controlPane.getChildren().add(getSampleTestCases());
        controlPane.getChildren().add(getStartButton());
        return controlPane;
    }

    private TestCases getSampleTestCases() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resource_mappings.json");
        return new TestCases(inputStream);
    }

    private Button getStartButton() {
        MenuItem compute = new MenuItem();
        compute.setText("Compute");
        return compute;
    }

    private StatisticsPlot getStatisticsOfObjectiveFunction() {
        Function<Integer, Double> function;
        function = (Integer x) -> exp(-(double) x / 200) * sin((double) x / 20);
        Statistics statistics = new Statistics();
        for (int i = 0; i < 1000; i++) {
            statistics.add(i, function.apply(i));
        }
        StatisticsPlot statisticsPlot = new StatisticsPlot(statistics);
        statisticsPlot.setTitle("Objective function");
        return statisticsPlot;
    }

    private OutputOfAlgorithm getAlgorithmOutput() {
        return new OutputOfAlgorithm();
    }
}
