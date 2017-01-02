package pl.edu.agh.student.simulatedannealing.mutator;

/**
 * Created by pingwin on 02.01.17.
 */
@FunctionalInterface
public interface Mutator<CurrentState> {
    CurrentState getNext(CurrentState currentState);
}
