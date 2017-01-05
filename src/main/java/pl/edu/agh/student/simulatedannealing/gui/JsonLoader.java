package pl.edu.agh.student.simulatedannealing.gui;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by pingwin on 05.01.17.
 */
public class JsonLoader {
    public static String DESCRIPTION = "description";
    public static String PIZZAS = "pizzas";
    public static String DELIVERERS = "deliverers";

    public List<Pizza> loadPizzas(InputStream pizzaSource) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        List<Pizza> pizzas = null;
        try {
            pizzas = objectMapper.readValue(pizzaSource, objectMapper.getTypeFactory().constructCollectionType(List.class, Pizza.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pizzas;
    }

    public List<PizzaDeliverer> loadDeliverers(InputStream delivererSource) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        List<PizzaDeliverer> deliverers = null;
        try {
            deliverers = objectMapper.readValue(delivererSource, objectMapper.getTypeFactory().constructCollectionType(List.class, PizzaDeliverer.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deliverers;
    }

    public List<Map<String, String>> getResourceMappings(InputStream resourcesSource) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        List<Map<String, String>> resources = null;
        try {
            resources = objectMapper.readValue(resourcesSource, objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
