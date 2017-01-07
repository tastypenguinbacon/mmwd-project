package pl.edu.agh.student.simulatedannealing.gui;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.edu.agh.student.simulatedannealing.gui.plot.PizzaDelivererItem;
import pl.edu.agh.student.simulatedannealing.gui.plot.PizzaItem;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.model.Point;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.*;

/**
 * Created by pingwin on 05.01.17.
 */
public class OutputOfAlgorithm extends ScatterChart<Number, Number> {
    private List<PizzaDeliverer> pizzaDeliverers = new LinkedList<>();
    private List<Pizza> pizzasToDeliver = new LinkedList<>();

    public OutputOfAlgorithm(Stage parent) {
        super(new NumberAxis(0, 50, 1), new NumberAxis(0, 50, 1));
        setMinSize(720, 720);
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                PizzaOrDeliverer pizzaOrDeliverer = new PizzaOrDeliverer();
                pizzaOrDeliverer.showAndWait();
                if (pizzaOrDeliverer.isPizzaSelected() == Boolean.TRUE) {
                    List<String> inputs = Arrays.asList("From X", "From Y", "To X", "To Y", "Time until cold");
                    InputBox inputBox = new InputBox(inputs);
                    inputBox.showAndWait();
                    Map<String, String> stringInputs = inputBox.getInputs();
                    Point from = new Point((int) (double) Double.valueOf(stringInputs.get("From X")),
                            (int) (double) Double.valueOf(stringInputs.get("From Y")));
                    Point to = new Point((int) (double) Double.valueOf(stringInputs.get("To X")),
                            (int) (double) Double.valueOf(stringInputs.get("To Y")));
                    Pizza pizza = new Pizza(from, to);
                    pizza.setTimeUntilCold(Integer.valueOf(stringInputs.get("Time until cold")));
                    addPizzas(pizza);
                } else if (pizzaOrDeliverer.isPizzaSelected() == Boolean.FALSE) {
                    List<String> inputs = Arrays.asList("Position X", "Position Y");
                    InputBox inputBox = new InputBox(inputs);
                    inputBox.showAndWait();
                    Map<String, String> stringInputs = inputBox.getInputs();
                    Point location = new Point((int) (double) Double.valueOf(stringInputs.get("Position X")),
                            (int) (double) Double.valueOf(stringInputs.get("Position Y")));
                    PizzaDeliverer pizzaDeliverer = new PizzaDeliverer(location);
                    pizzaDeliverer.setPizzasWeAreObligatedToDeliver(new LinkedList<>());
                    addPizzaDeliverers(pizzaDeliverer);
                } else {
                    return;
                }
                parent.show();
            }
        });
    }

    public void addPizzaDeliverers(PizzaDeliverer... pizzaDeliverers) {
        this.pizzaDeliverers.addAll(Arrays.asList(pizzaDeliverers));
        redraw();
    }

    public void addPizzas(Pizza... pizzas) {
        this.pizzasToDeliver.addAll(Arrays.asList(pizzas));
        redraw();
    }

    public void redraw() {
        getData().clear();
        for (PizzaDeliverer entry : pizzaDeliverers) {
            addPizzaDelivererToData(entry);
        }
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (Pizza pizza : pizzasToDeliver) {
            XYChart.Data<Number, Number> dataEntry = new XYChart.Data<>(pizza.getPosition().getX(), pizza.getPosition().getY());
            dataEntry.setNode(new PizzaItem(pizza, this));
            series.getData().add(dataEntry);
        }
        getData().add(series);
    }

    public void update(ComputationState finalState) {
        getData().clear();
        for (PizzaDeliverer entry : finalState.getPizzaDeliverers()) {
            addPizzaDelivererToData(entry);
        }
    }

    private void addPizzaDelivererToData(PizzaDeliverer pizzaDeliverer) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        XYChart.Data<Number, Number> dataEntry = new XYChart.Data<>(pizzaDeliverer.getCurrentPosition().getX(), pizzaDeliverer.getCurrentPosition().getY());
        dataEntry.setNode(new PizzaDelivererItem(pizzaDeliverer, this));
        series.getData().add(dataEntry);
        for (Pizza pizza : pizzaDeliverer.getAssignedPizzas()) {
            int x = pizza.getPosition().getX();
            int y = pizza.getPosition().getY();
            dataEntry = new XYChart.Data<>(x, y);
            PizzaItem pizzaItem = new PizzaItem(pizza, this);
            pizzaItem.makeBig();
            dataEntry.setNode(pizzaItem);
            series.getData().add(dataEntry);
        }
        series.setName(null);
        getData().add(series);
    }

    public Collection<PizzaDeliverer> getPizzaDeliverers() {
        return pizzaDeliverers;
    }

    public Collection<Pizza> getPizzasToDeliver() {
        return pizzasToDeliver;
    }

    public void setPizzaDeliverers(List<PizzaDeliverer> pizzaDeliverers) {
        this.pizzaDeliverers = pizzaDeliverers;
    }

    public void setPizzasToDeliver(List<Pizza> pizzasToDeliver) {
        this.pizzasToDeliver = pizzasToDeliver;
    }
}
