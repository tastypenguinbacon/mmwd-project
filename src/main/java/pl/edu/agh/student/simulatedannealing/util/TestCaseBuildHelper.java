package pl.edu.agh.student.simulatedannealing.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.model.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pingwin on 07.01.17.
 */
public class TestCaseBuildHelper {

    public static void main(String... args) throws Exception {
        if (args.length != 1) {
            System.out.println("Add the word pizza to create pizzas, add the word deliverer to create deliverers" +
                    "\nrun like: java -jar TestCaseBuildHelper.jar pizza");
            return;
        }
        if (args[0].equals("pizza")) {
            System.out.println("Start: X Y, End: X Y, timeUntilCold, empty line quits");
            List<Pizza> pizzas = new LinkedList<>();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                input = input.trim();
                String[] inputs = input.split(" ");
                if (inputs.length == 1) {
                    break;
                }
                try {
                    int startX = Integer.valueOf(inputs[0]);
                    int startY = Integer.valueOf(inputs[1]);
                    int endX = Integer.valueOf(inputs[2]);
                    int endY = Integer.valueOf(inputs[3]);
                    int timeUntilCold = Integer.valueOf(inputs[4]);
                    Pizza pizza = new Pizza(new Point(startX, startY), new Point(endX, endY));
                    pizza.setTimeUntilCold(timeUntilCold);
                    pizzas.add(pizza);
                } catch (Exception ignored) {
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(System.out, pizzas);
        } else {
            List<PizzaDeliverer> pizzaDeliverers = new LinkedList<>();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Current position of deliverer X Y");
                String input = scanner.nextLine();
                input = input.trim();
                String[] inputs = input.split(" ");
                if (inputs.length == 1) {
                    break;
                }
                try {
                    int posX = Integer.valueOf(inputs[0]);
                    int posY = Integer.valueOf(inputs[1]);
                    PizzaDeliverer deliverer = new PizzaDeliverer(new Point(posX, posY));
                    System.out.println("Pizza destination X, Y, time until cold");
                    List<Pizza> pizzas = new LinkedList<>();
                    while (scanner.hasNextLine()) {
                        input = scanner.nextLine();
                        input = input.trim();
                        inputs = input.split(" ");
                        if (inputs.length == 1) {
                            break;
                        }
                        try {
                            int destX = Integer.valueOf(inputs[0]);
                            int destY = Integer.valueOf(inputs[1]);
                            int timeUntilCold = Integer.valueOf(inputs[2]);
                            Pizza pizza = new Pizza(new Point(posX, posY), new Point(destX, destY));
                            pizza.setTimeUntilCold(timeUntilCold);
                            pizzas.add(pizza);
                        } catch (Exception ignored) {
                        }
                    }
                    deliverer.setPizzasWeAreObligatedToDeliver(pizzas);
                    pizzaDeliverers.add(deliverer);
                } catch (Exception ignored) {
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(System.out, pizzaDeliverers);
        }
        System.out.println();
    }
}
