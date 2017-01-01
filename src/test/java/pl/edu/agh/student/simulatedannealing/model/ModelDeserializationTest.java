package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by pingwin on 01.01.17.
 */
public class ModelDeserializationTest {
    @Test
    public void howThePizzaJsonLooksLike() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Pizza pizza = new Pizza(new Point(10, 20), new Point(10, 40));
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String serializedPizza = objectMapper.writeValueAsString(pizza);
        System.out.println(serializedPizza);
        Pizza deserializedPizza = objectMapper.readValue(serializedPizza, Pizza.class);
        assertTrue(deserializedPizza.equals(pizza));
    }

    @Test
    public void howThePizzaDelivererJsonLooksLike() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PizzaDeliverer deliverer = new PizzaDeliverer();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Set<Pizza> pizzasWeAreObligatedToDeliver = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Pizza newPizza = new Pizza(new Point(i, i), new Point(i, i));
            pizzasWeAreObligatedToDeliver.add(newPizza);
        }
        deliverer.setPizzasWeAreObligatedToDeliver(pizzasWeAreObligatedToDeliver);
        String serializedDeliverer = objectMapper.writeValueAsString(deliverer);
        System.out.println(serializedDeliverer);
        PizzaDeliverer deserializedDeliverer = objectMapper.readValue(serializedDeliverer, PizzaDeliverer.class);
        assertTrue(deserializedDeliverer.equals(deliverer));
    }
}