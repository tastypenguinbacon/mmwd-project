package pl.edu.agh.student.simulatedannealing.solver;

import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pingwin on 02.01.17.
 */
public class ComputationState {
    private final LinkedList<PizzaDeliverer> pizzaDeliverers;

    public ComputationState(Collection<PizzaDeliverer> pizzaDeliverers) {
        this.pizzaDeliverers = new HashSet<>(pizzaDeliverers);
    }

    //copy constructor with deep copy
    public ComputationState(ComputationState other) {
        this.pizzaDeliverers = new HashSet<>();
        for (PizzaDeliverer deliverer : other.pizzaDeliverers) {
            pizzaDeliverers.add(new PizzaDeliverer(deliverer));
        }
    }

    public Set<PizzaDeliverer> getPizzaDeliverers() {
        return pizzaDeliverers;
    }

    public boolean isValid() {
        return pizzaDeliverers.stream().allMatch(PizzaDeliverer::isAbleToCollectThePizzas);
    }

    // returns all obligatory pizzas making up the deliverer's routes
    List<Pizza> getObligatoryPizzas() {
        return pizzaDeliverers.stream()
                .map(PizzaDeliverer::getPizzasWeAreObligatedToDeliver)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // returns all potential pizzas making up the deliverer's routes
    public List<Pizza> getPotentialPizzas() {
        return pizzaDeliverers.stream()
                .map(PizzaDeliverer::getPizzasWeCouldDeliver)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Removes the specified pizza from the solution.
     * The specified pizza should be part of the solution.
     */
    public void removePizzaFromSolution(Pizza toBeRemoved) {
        for (PizzaDeliverer deliverer : pizzaDeliverers) {
            if (deliverer.getPizzasWeCouldDeliver().contains(toBeRemoved)) {
                deliverer.removePizza(toBeRemoved);
                return;
            }
        }
        assert false : "Couldn't find the order to be removed.";
    }
}
