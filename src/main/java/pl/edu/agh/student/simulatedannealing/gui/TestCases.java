package pl.edu.agh.student.simulatedannealing.gui;

import javafx.scene.layout.VBox;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.util.JsonLoader;

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
            MenuItem button = new MenuItem();
            button.setText(element.get(JsonLoader.DESCRIPTION));
            String pizzas = element.get(JsonLoader.PIZZAS);
            String deliverers = element.get(JsonLoader.DELIVERERS);
            InputStream jsonDeliverers = getClass().getClassLoader().getResourceAsStream(deliverers);
            InputStream jsonPizzas = getClass().getClassLoader().getResourceAsStream(pizzas);
            List<PizzaDeliverer> delivererList = loader.loadDeliverers(jsonDeliverers);
            List<Pizza> pizzaList = loader.loadPizzas(jsonPizzas);;
            button.setOnAction(event -> {
                this.deliverers = delivererList;
                this.pizzas = pizzaList;
            });
            this.getChildren().add(button);
        });
        MenuItem customArrangement = new MenuItem();
        customArrangement.setText("Custom Arrangement");
        customArrangement.setOnAction(event -> {
            this.deliverers = null;
            this.pizzas = null;
        });
        this.getChildren().add(customArrangement);
    }

    public List<PizzaDeliverer> getDeliverers() {
        return deliverers;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
}
