package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.*;

import static java.lang.Math.min;

/**
 * Created by Szymek on 2017-01-03.
 */
public class ComputationStateMutator implements Mutator<ComputationState> {
    private List<Pizza> pizzasToDeliver;

    private Random generator = new Random();

    @Override
    public ComputationState getNext(ComputationState parent) {
        ComputationState child = new ComputationState(parent);

        List<Pizza> notDeliveredYet = new LinkedList<>(pizzasToDeliver);
        notDeliveredYet.removeAll(child.getPotentialPizzas());
        Collections.shuffle(notDeliveredYet, generator);

        /*//todo - move to field and add setter
        int maxInsertionAttempts = 10;
        int limit = min(notDeliveredYet.size(), maxInsertionAttempts);

        boolean modified = addPizzaFromList(child, notDeliveredYet.subList(0, limit));
        if (!modified)
            modified = removeRandomPizza(child);
        if (!modified)
            addPizzaFromList(child, notDeliveredYet.subList(limit, notDeliveredYet.size()));*/

        boolean modified = addPizzaFromList(child, notDeliveredYet);
        if (!modified)
            removeRandomPizza(child);

        assert (child.isValid());

        return child;
    }

    @Override
    public void addPizzasToDistribute(Collection<Pizza> pizzasToDistribute) {
        this.pizzasToDeliver = new LinkedList<>(pizzasToDistribute);
    }

    /**
     * This function attempts to remove a randomly chosen pizza from the solution.
     * The pool of pizzas eligible for removal consists of all pizzas in the deliverer's pizzasWeCouldDeliver field.
     *
     * @return true if a pizza was removed from the solution, false otherwise.
     */
    private boolean removeRandomPizza(ComputationState solution) {
        List<Pizza> pizzasThatMayBeRemoved = solution.getPotentialPizzas();
        if (pizzasThatMayBeRemoved.isEmpty())
            return false;
        Pizza toBeRemoved = pizzasThatMayBeRemoved.get(generator.nextInt(pizzasThatMayBeRemoved.size()));
        removePizzaFromSolution(toBeRemoved, solution);
        return true;
    }

    /**
     * Removes the specified pizza from the specified solution.
     * The specified pizza should be part of the solution.
     */
    private void removePizzaFromSolution(Pizza toBeRemoved, ComputationState solution) {
        for (PizzaDeliverer deliverer : solution.getPizzaDeliverers()) {
            if (deliverer.getPizzasWeCouldDeliver().contains(toBeRemoved)) {
                deliverer.removePizza(toBeRemoved);
                return;
            }
        }
        assert false : "Couldn't find the order to be removed.";
    }

    private boolean addPizzaFromList(ComputationState solution, List<Pizza> candidatePizzas) {
        for (Pizza toBeInserted : candidatePizzas) {
            if (addPizzaToSolution(toBeInserted, solution))
                return true;
        }
        return false;
    }

    /**
     * //todo currently always tries insertion starting from first deliverer to last
     * Attempts to add the specified pizza to random valid deliverer in the specified solution.
     * Tries inserting into consecutive deliverers until success or deliverer route exhaustion.
     *
     * @return true upon successful addition of the pizza. False otherwise.
     */
    private boolean addPizzaToSolution(Pizza toBeAdded, ComputationState solution) {
        for (PizzaDeliverer candidate : solution.getPizzaDeliverers())
            if (candidate.attemptInsertingPizza(toBeAdded))
                return true;
        return false;
    }
}
