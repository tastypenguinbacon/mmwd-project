package pl.edu.agh.student.simulatedannealing.gui.plot;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pl.edu.agh.student.simulatedannealing.gui.InputBox;
import pl.edu.agh.student.simulatedannealing.gui.OutputOfAlgorithm;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.model.Point;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by pingwin on 06.01.17.
 */
public class PizzaDelivererItem extends ImageView {
    private final OutputOfAlgorithm plot;
    private PizzaDeliverer pizzaDeliverer;

    public PizzaDelivererItem(PizzaDeliverer pizzaDeliverer, OutputOfAlgorithm plot) {
        super(new Image(PizzaItem.class.getClassLoader().getResourceAsStream("deliverer.png"),100, 100, false, false));
        this.plot = plot;
        this.pizzaDeliverer = pizzaDeliverer;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            InfoBox infoBox = new InfoBox();
            infoBox.add("Position: ", pizzaDeliverer.getCurrentPosition().getX() + " " + pizzaDeliverer.getCurrentPosition().getY());
            for (Pizza pizza : pizzaDeliverer.getPizzasWeAreObligatedToDeliver()) {
                infoBox.add("Must deliver to:", pizza.getDestination().getX() + " " + pizza.getDestination().getY());
            }
            for (Pizza pizza : pizzaDeliverer.getPizzasWeCouldDeliver()) {
                infoBox.add("Delivers from " + pizza.getPosition().getX() + " " + pizza.getPosition().getY(),
                        " to " + pizza.getDestination().getX() + " " + pizza.getDestination().getY());
            }
            Button button = new Button("Remove");
            button.setPadding(new Insets(20));
            button.setOnAction(actionEvent -> {
                plot.getPizzaDeliverers().remove(pizzaDeliverer);
                infoBox.close();
                plot.redraw();
            });
            infoBox.addButton(button);
            button = new Button("Add pizza to backpack");
            button.setPadding(new Insets(20));
            button.setOnAction(actionEvent -> {
                InputBox inputBox = new InputBox(Arrays.asList("Destination X", "Destination Y", "Time until cold"));
                inputBox.showAndWait();
                Map<String, String> inputs = inputBox.getInputs();
                int timeUntilCold = Integer.valueOf(inputs.get("Time until cold"));
                int destinationX = Integer.valueOf(inputs.get("Destination X"));
                int destinationY = Integer.valueOf(inputs.get("Destination Y"));
                Pizza pizzaToDeliver = new Pizza(new Point(0,0), new Point(destinationX, destinationY));
                pizzaToDeliver.setTimeUntilCold(timeUntilCold);
                pizzaDeliverer.getPizzasWeAreObligatedToDeliver().add(pizzaToDeliver);
                infoBox.add("Must deliver to:", pizzaToDeliver.getDestination().getX() + " " + pizzaToDeliver.getDestination().getY());
                infoBox.close();
                infoBox.show();
            });
            infoBox.addButton(button);
            infoBox.show();
        });
    }
}
