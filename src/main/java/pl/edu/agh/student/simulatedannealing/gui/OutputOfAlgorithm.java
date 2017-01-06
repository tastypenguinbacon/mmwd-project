package pl.edu.agh.student.simulatedannealing.gui;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;
import pl.edu.agh.student.simulatedannealing.statistics.StatisticPoint;

import java.util.Collection;

/**
 * Created by pingwin on 05.01.17.
 */
public class OutputOfAlgorithm extends ScatterChart<Number, Number> {
    private Collection<PizzaDeliverer> pizzaDeliverers;
    private Collection<Pizza> pizzasToDeliver;

    public OutputOfAlgorithm() {
        super(new NumberAxis(0, 50, 1), new NumberAxis(0, 50, 1));
        setMinSize(720, 720);
    }

    public Collection<PizzaDeliverer> getPizzaDeliverers() {
        return pizzaDeliverers;
    }

    public Collection<Pizza> getPizzasToDeliver() {
        return pizzasToDeliver;
    }

    public void update(ComputationState finalState) {
        getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (PizzaDeliverer entry : finalState.getPizzaDeliverers()) {
            XYChart.Data<Number, Number> dataEntry = new XYChart.Data<>(entry.getCurrentPosition().getX(), entry.getCurrentPosition().getY());
            series.getData().add(dataEntry);
        }
        series.setName("Objective function values");
        getData().add(series);
    }
}
