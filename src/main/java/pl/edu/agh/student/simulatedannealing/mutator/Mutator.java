package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.model.Pizza;

import java.util.Collection;

/**
 * Created by pingwin on 02.01.17.
 */
@FunctionalInterface
public interface Mutator<CurrentState> {
    CurrentState getNext(CurrentState currentState);
    default void addPizzasToDistribute(Collection<Pizza> pizzasToDistribute) {

    }
}
