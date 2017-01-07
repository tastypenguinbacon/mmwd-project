package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Szymek on 2017-01-07.
 * This mutator attempts to insert pizzas with lower value of timeUntilCold first.
 */
public class InsertColdPizzasFirstMutator extends ComputationStateMutatorBase{

    @Override
    public ComputationState getNext(ComputationState parent) {
        ComputationState child = new ComputationState(parent);

        List<Pizza> notDeliveredYet = new LinkedList<>(pizzasToDeliver);
        notDeliveredYet.removeAll(child.getPotentialPizzas());
        notDeliveredYet.sort(pizzaTimeComparator);

        boolean modified = addPizzaFromList(child, notDeliveredYet);
        if (!modified)
            removeRandomPizza(child);

        assert (child.isValid());

        return child;
    }

    private Comparator<Pizza> pizzaTimeComparator
            = (pizza1, pizza2) -> {

                int time1 = pizza1.getTimeUntilCold();
                int time2 = pizza2.getTimeUntilCold();

                return time1 - time2;
            };


}
