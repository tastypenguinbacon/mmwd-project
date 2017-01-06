package pl.edu.agh.student.simulatedannealing.gui;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

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
}
