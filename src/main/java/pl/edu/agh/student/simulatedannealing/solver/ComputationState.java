package pl.edu.agh.student.simulatedannealing.solver;

import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

import java.util.*;

/**
 * Created by pingwin on 02.01.17.
 */
public class ComputationState {
    private final LinkedList<PizzaDeliverer> pizzaDeliverers;

    public ComputationState(Collection<PizzaDeliverer> pizzaDeliverers) {
        this.pizzaDeliverers = new LinkedList<>(pizzaDeliverers);
    }

    public List<PizzaDeliverer> getPizzaDeliverers() {
        return pizzaDeliverers;
    }

    public boolean isValid() {
        return pizzaDeliverers.stream().allMatch(PizzaDeliverer::isAbleToCollectThePizzas);
    }
}
