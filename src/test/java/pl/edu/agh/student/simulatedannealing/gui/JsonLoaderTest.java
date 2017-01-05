package pl.edu.agh.student.simulatedannealing.gui;

import org.junit.Test;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

import java.io.InputStream;
import java.util.List;

/**
 * Created by pingwin on 05.01.17.
 */
public class JsonLoaderTest {
    @Test
    public void loadPizzas() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("test/pizzas.json");
        JsonLoader loader = new JsonLoader();
        List<Pizza> pizzas = loader.loadPizzas(resourceAsStream);
        System.out.println(pizzas);
    }

    @Test
    public void loadDeliverers() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("test/deliverers.json");
        JsonLoader loader = new JsonLoader();
        List<PizzaDeliverer> deliverers = loader.loadDeliverers(resourceAsStream);
        System.out.println(deliverers);
    }
}