package pl.edu.agh.student.simulatedannealing.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by pingwin on 05.01.17.
 */
public class TestCases extends VBox {
    private List<PizzaDeliverer> deliverers;
    private List<Pizza> pizzas;

    public TestCases(InputStream resourceMappings) {
        super(10);
        JsonLoader loader = new JsonLoader();
        List<Map<String, String>> buttons;
        buttons = loader.getResourceMappings(resourceMappings);
        buttons.forEach((Map<String, String> element) -> {
            Button button = new Button();
            button.setText(element.get(JsonLoader.DESCRIPTION));
            String pizzas = element.get(JsonLoader.PIZZAS);
            String deliverers = element.get(JsonLoader.DELIVERERS);
            InputStream jsonDeliverers = getClass().getClassLoader().getResourceAsStream(deliverers);
            InputStream jsonPizzas = getClass().getClassLoader().getResourceAsStream(pizzas);
            List<PizzaDeliverer> delivererList = loader.loadDeliverers(jsonDeliverers);
            List<Pizza> pizzaList = loader.loadPizzas(jsonPizzas);
            button.setOnAction(event -> {
                this.deliverers = delivererList;
                this.pizzas = pizzaList;
            });
            button.setPadding(new Insets(20));
            this.getChildren().add(button);
        });
        Button customArrangement = new Button("Custom Arrangement");
        customArrangement.setPadding(new Insets(20));
        customArrangement.setOnAction(event -> {
            this.deliverers = null;
            this.pizzas = null;
        });
        this.setPadding(new Insets(50, 20, 50, 20));
        this.getChildren().add(customArrangement);
    }

    public List<PizzaDeliverer> getDeliverers() {
        return deliverers;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
}
