package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by pingwin on 07.01.17.
 */
public class DFSMutator implements Mutator<ComputationState> {

    private LinkedList<Pizza> pizzasToDistribute;
    private Random random = new Random();
    private List<PizzaDeliverer> deliverers;
    @Override
    public void addPizzasToDistribute(Collection<Pizza> pizzasToDistribute) {
        this.pizzasToDistribute = new LinkedList<>(pizzasToDistribute);
    }

    @Override
    public ComputationState getNext(ComputationState computationState) {
        ComputationState next = new ComputationState(computationState);
        deliverers = next.getPizzaDeliverers();
        if (random.nextInt(100) == 0) {
            int indexOfDelivererToClearStack = random.nextInt(deliverers.size());
            clearStackOfDeliverer(indexOfDelivererToClearStack);
        } else {
            int indexOfDelivererToModify = random.nextInt(deliverers.size());
            modifyDeliverer(indexOfDelivererToModify);
        }
        return null;
    }

    private void clearStackOfDeliverer(int indexOfDelivererToClearStack) {
        PizzaDeliverer delivererToClear = deliverers.get(indexOfDelivererToClearStack);
    }

    private void modifyDeliverer(int indexOfDelivererToModify) {
        if (random.nextBoolean()) {
        }
    }
}
