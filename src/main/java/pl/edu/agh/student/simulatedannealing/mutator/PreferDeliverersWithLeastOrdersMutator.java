package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Szymek on 2017-01-07.
 * This mutator attempts to give pizzas to deliverers with low amount of orders first.
 */
public class PreferDeliverersWithLeastOrdersMutator extends ComputationStateMutatorBase{
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

    @Override
    boolean addPizzaToSolution(Pizza toBeAdded, ComputationState solution) {
        //shallow copy
        List<PizzaDeliverer> list = new LinkedList<>(solution.getPizzaDeliverers());
        list.sort(delivererPizzaCountComparator);

        for (PizzaDeliverer candidate : list)
            if (candidate.attemptInsertingPizza(toBeAdded))
                return true;
        return false;
    }

    // least orders first
    private Comparator<PizzaDeliverer> delivererPizzaCountComparator
            = (deliverer1, deliverer2) -> {

        int count1 =
                deliverer1.getPizzasWeAreObligatedToDeliver().size()
                + deliverer1.getPizzasWeCouldDeliver().size();
        int count2 =
                deliverer2.getPizzasWeAreObligatedToDeliver().size()
                + deliverer2.getPizzasWeCouldDeliver().size();

        return count1 - count2;
    };
}
