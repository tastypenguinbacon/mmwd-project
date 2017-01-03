package pl.edu.agh.student.simulatedannealing.solver;

import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pingwin on 02.01.17.
 */
public class ComputationState {
    private final HashSet<PizzaDeliverer> pizzaDeliverers;

    public ComputationState(Collection<PizzaDeliverer> pizzaDeliverers) {
        this.pizzaDeliverers = new HashSet<>(pizzaDeliverers);
    }

    public Set<PizzaDeliverer> getPizzaDeliverers() {
        return pizzaDeliverers;
    }

    public boolean isValid() {
        return pizzaDeliverers.stream().allMatch(PizzaDeliverer::isAbleToCollectThePizzas);
    }
}
