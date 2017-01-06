package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

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
        Point delivererPosition = new Point(3, 6);
        PizzaDeliverer deliverer = new PizzaDeliverer(delivererPosition);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<Pizza> pizzasWeAreObligatedToDeliver = new LinkedList<>();
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

    @Test
    public void serializeToFileAndDeserialize() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Point delivererPosition = new Point(3, 6);
        PizzaDeliverer deliverer = new PizzaDeliverer(delivererPosition);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        List<Pizza> pizzasWeAreObligatedToDeliver = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            Pizza newPizza = new Pizza(new Point(i, i), new Point(i, i));
            pizzasWeAreObligatedToDeliver.add(newPizza);
        }
        deliverer.setPizzasWeAreObligatedToDeliver(pizzasWeAreObligatedToDeliver);
        String serializedDeliverer = objectMapper.writeValueAsString(deliverer);

        Charset encoding = StandardCharsets.UTF_8;
        String path = "deliverer.txt";
        Files.write(Paths.get(path),
                    serializedDeliverer.getBytes(encoding),
                    StandardOpenOption.CREATE);

        serializedDeliverer = new String(Files.readAllBytes(Paths.get(path)), encoding);
        PizzaDeliverer deserializedDeliverer = objectMapper.readValue(serializedDeliverer, PizzaDeliverer.class);

        assertTrue(deserializedDeliverer.equals(deliverer));
    }
}