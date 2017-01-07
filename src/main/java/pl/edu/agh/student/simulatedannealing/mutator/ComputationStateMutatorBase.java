package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
     * //todo currently always tries insertion starting from first deliverer to last
     * Attempts to add the specified pizza to random valid deliverer in the specified solution.
     * Tries inserting into consecutive deliverers until success or deliverer route exhaustion.
     *
     * @return true upon successful addition of the pizza. False otherwise.
     */
    boolean addPizzaToSolution(Pizza toBeAdded, ComputationState solution) {
        for (PizzaDeliverer candidate : solution.getPizzaDeliverers())
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
            if (addPizzaToSolution(toBeInserted, solution))
                return true;
        }
        return false;
    }
}
