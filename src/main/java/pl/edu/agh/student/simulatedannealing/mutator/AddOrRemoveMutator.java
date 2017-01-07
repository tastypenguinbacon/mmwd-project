package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Szymek on 2017-01-07.
 * This mutator attempts to insert or remove pizzas with equal probability.
 */
public class AddOrRemoveMutator extends ComputationStateMutatorBase {
    private double chanceToAddInPercent;

    @Override
    public ComputationState getNext(ComputationState parent) {
        ComputationState child = new ComputationState(parent);

        int random = generator.nextInt(100);
        boolean remove = random > chanceToAddInPercent;
        if (remove) {
            removeRandomPizza(child);
        } else {
            List<Pizza> notDeliveredYet = new LinkedList<>(pizzasToDeliver);
            notDeliveredYet.removeAll(child.getPotentialPizzas());
            Collections.shuffle(notDeliveredYet, generator);
            addPizzaFromList(child, notDeliveredYet);
        }

        assert (child.isValid());

        return child;
    }

    public void setChanceToAddInPercent(double chanceToAddInPercent) {
        this.chanceToAddInPercent = chanceToAddInPercent;
    }

}
