package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;

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
}