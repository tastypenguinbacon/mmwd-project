package pl.edu.agh.student.simulatedannealing.util;

import org.junit.Test;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.temperature.Temperature;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

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

    @Test
    public void loadMappings() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("resource_mappings.json");
        JsonLoader loader = new JsonLoader();
        List<Map<String, String>> resources = loader.getResourceMappings(resourceAsStream);
        System.out.println(resources);
        resources.forEach(resource -> {
            System.out.println(resource.get(JsonLoader.DESCRIPTION));
            System.out.println(resource.get(JsonLoader.PIZZAS));
            System.out.println(resource.get(JsonLoader.DELIVERERS));
        });
    }

    @Test
    public void loadTemperatures() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("temperatures.json");
        JsonLoader jsonLoader = new JsonLoader();
        //Map<String, Temperature> temperatures = jsonLoader.getProperties(resourceAsStream);
    }
}