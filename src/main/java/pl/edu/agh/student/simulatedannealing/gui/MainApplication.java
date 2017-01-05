package pl.edu.agh.student.simulatedannealing.gui;


import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
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

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        HBox verticalLayout = new HBox(20);
        verticalLayout.getChildren().add(getControlPane());
        verticalLayout.getChildren().add(getAlgorithmOutput());
        verticalLayout.getChildren().add(getStatisticsOfObjectiveFunction());
        stage.setScene(new Scene(verticalLayout));
        stage.show();
    }

    private Node getControlPane() {
        VBox controlPane = new VBox(50);
        controlPane.getChildren().add(getSampleTestCases());
        return controlPane;
    }

    private StatisticsPlot getStatisticsOfObjectiveFunction() {
        Function<Integer, Double> function;
        function = (Integer x) -> exp(-(double) x / 200) * sin((double) x / 20);
        Statistics statistics = new Statistics();
        for (int i = 0; i < 1000; i++) {
            statistics.add(i, function.apply(i));
        }
        return new StatisticsPlot(statistics);
    }

    private OutputOfAlgorithm getAlgorithmOutput() {
        return new OutputOfAlgorithm();
    }

    private TestCases getSampleTestCases() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resource_mappings.json");
        return new TestCases(inputStream);
    }
}
