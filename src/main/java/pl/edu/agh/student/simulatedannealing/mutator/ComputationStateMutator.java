package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.mutator.Mutator;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.List;
import java.util.Random;

/**
 * Created by Szymek on 2017-01-03.
 */
public class ComputationStateMutator implements Mutator<ComputationState> {
    private List<Pizza> pizzasToDeliver;
    private List<Pizza> pizzasWeAreObligatedToDeliver;

    private Random generator = new Random();

    @Override
    public ComputationState getNext(ComputationState parent) {
        ComputationState child = new ComputationState(parent);

        boolean modified = removeRandomPizza(child);
        if (modified)
            return child;
        else
            return parent;

        //todo adding new pizzas!!!
        //currently the method can only remove pizzas

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
        solution.removePizzaFromSolution(pizzasThatMayBeRemoved.get(generator.nextInt(pizzasThatMayBeRemoved.size())));
        return true;
    }

    public void initializePizzasToDeliver(List<Pizza> pizzasToDeliver) {
        this.pizzasToDeliver = pizzasToDeliver;
    }

    public void initializePizzasWeAreObligatedToDeliver(List<Pizza> pizzasWeAreObligatedToDeliver) {
        this.pizzasWeAreObligatedToDeliver = pizzasWeAreObligatedToDeliver;
    }
}
