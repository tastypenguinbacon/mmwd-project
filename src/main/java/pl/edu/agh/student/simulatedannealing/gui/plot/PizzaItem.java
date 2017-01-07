package pl.edu.agh.student.simulatedannealing.gui.plot;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pl.edu.agh.student.simulatedannealing.gui.OutputOfAlgorithm;
import pl.edu.agh.student.simulatedannealing.model.Pizza;

/**
 * Created by pingwin on 06.01.17.
 */
public class PizzaItem extends ImageView {
    private final Pizza pizza;

    public PizzaItem(Pizza pizza, OutputOfAlgorithm plot) {
        super(new Image(PizzaItem.class.getClassLoader().getResourceAsStream("pizza.png"), 40, 40, false, false));
        this.pizza = pizza;
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            InfoBox infoBox = new InfoBox();
            infoBox.add("X, Y position", pizza.getPosition().getX() + " " + pizza.getPosition().getY())
                    .add("X, Y destination", pizza.getDestination().getX() + " " + pizza.getDestination().getY())
                    .add("Time until cold", String.valueOf(pizza.getTimeUntilCold()));
            Button button = new Button("Remove");
            button.setPadding(new Insets(20));
            button.setOnAction(actionEvent -> {
                plot.getPizzasToDeliver().remove(pizza);
                infoBox.close();
                plot.redraw();
            });
            infoBox.addButton(button);
            infoBox.show();
        });
    }

    public void makeBig() {
        setFitWidth(80);
        setFitHeight(80);
    }
}
