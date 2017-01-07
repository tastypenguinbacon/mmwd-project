package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Szymek on 2017-01-03.
 * This mutator attempts to insert pizzas at random into random deliverers.
 */
public class ComputationStateMutator extends ComputationStateMutatorBase {
    @Override
    public ComputationState getNext(ComputationState parent) {
        ComputationState child = new ComputationState(parent);

        List<Pizza> notDeliveredYet = new LinkedList<>(pizzasToDeliver);
        notDeliveredYet.removeAll(child.getPotentialPizzas());
        Collections.shuffle(notDeliveredYet, generator);

        boolean modified = addPizzaFromList(child, notDeliveredYet);
        if (!modified)
            removeRandomPizza(child);

        assert (child.isValid());

        return child;
    }
}
