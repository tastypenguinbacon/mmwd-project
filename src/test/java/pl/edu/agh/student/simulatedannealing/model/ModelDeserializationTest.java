package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import pl.edu.agh.student.simulatedannealing.util.Point;

/**
 * Created by pingwin on 01.01.17.
 */
public class ModelDeserializationTest {
    @Test
    public void howThePizzaJsonLooksLike() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Pizza pizza = new Pizza(new Point(10, 20), new Point(10, 40));
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(objectMapper.writeValueAsString(pizza));
    }
}