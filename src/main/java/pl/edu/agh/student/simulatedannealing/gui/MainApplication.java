package pl.edu.agh.student.simulatedannealing.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.agh.student.simulatedannealing.statistics.Statistics;

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
        Function<Integer, Double> function;
        function = (Integer x) -> exp(- (double)x / 200) * sin((double)x / 20);
        Statistics statistics = new Statistics();
        for (int i = 0; i < 1000; i++) {
            statistics.add(i, function.apply(i));
        }
        StatisticsPlot plot = new StatisticsPlot(statistics);
        stage.setScene(new Scene(plot));
        stage.show();
    }
}
