package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.*;

/**
 * Created by Szymek on 2017-01-07.
 * Base class for mutators. Implements methods for basic pizza insertion and removal.
 * Extending classes are supposed to use them to modify the ComputationState.
 */
public abstract class ComputationStateMutatorBase implements Mutator<ComputationState> {
    List<Pizza> pizzasToDeliver;

    Random generator = new Random();

    @Override
    public void addPizzasToDistribute(Collection<Pizza> pizzasToDistribute) {
        this.pizzasToDeliver = new LinkedList<>(pizzasToDistribute);
    }

    /**
     * Removes the specified pizza from the specified solution.
     * The specified pizza should be part of the solution.
     */
    void removePizzaFromSolution(Pizza toBeRemoved, ComputationState solution) {
        for (PizzaDeliverer deliverer : solution.getPizzaDeliverers()) {
            if (deliverer.getPizzasWeCouldDeliver().contains(toBeRemoved)) {
                deliverer.removePizza(toBeRemoved);
                return;
            }
        }
        assert false : "Couldn't find the order to be removed.";
    }

    /**
     * Attempts to add the specified pizza to random valid deliverer in the specified solution.
     * Tries inserting into consecutive deliverers until success or deliverer route exhaustion.
     *
     * @return true upon successful addition of the pizza. False otherwise.
     */
    boolean addPizzaToSolution(Pizza toBeAdded, ComputationState solution) {
        //shallow copy
        List<PizzaDeliverer> deliverers = new LinkedList<>(solution.getPizzaDeliverers());
        Collections.shuffle(deliverers, generator);
        for (PizzaDeliverer candidate : deliverers)
            if (candidate.attemptInsertingPizza(toBeAdded))
                return true;
        return false;
    }

    /**
     * Consecutively attempts to add pizza from the specified list to the specified solution.
     * Tries inserting pizzas until success or list exhaustion.
     *
     * @return true upon successful addition of a pizza. False otherwise.
     */
    boolean addPizzaFromList(ComputationState solution, List<Pizza> candidatePizzas) {
        for (Pizza toBeInserted : candidatePizzas) {
            if (this.addPizzaToSolution(toBeInserted, solution))
                return true;
        }
        return false;
    }

    /**
     * This function attempts to remove a randomly chosen pizza from the solution.
     * The pool of pizzas eligible for removal consists of all pizzas in the deliverer's pizzasWeCouldDeliver field.
     * To be used as the default removal method by mutators that don't need specialized removal policy.
     *
     * @return true if a pizza was removed from the solution, false otherwise.
     */
    boolean removeRandomPizza(ComputationState solution) {
        List<Pizza> pizzasThatMayBeRemoved = solution.getPotentialPizzas();
        if (pizzasThatMayBeRemoved.isEmpty())
            return false;
        Pizza toBeRemoved = pizzasThatMayBeRemoved.get(generator.nextInt(pizzasThatMayBeRemoved.size()));
        removePizzaFromSolution(toBeRemoved, solution);
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
