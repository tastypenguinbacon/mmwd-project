package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.*;

import static java.lang.Math.min;

/**
 * Created by Szymek on 2017-01-03.
 */
public class ComputationStateMutator extends ComputationStateMutatorBase {

    //private Random generator = new Random();

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




}
