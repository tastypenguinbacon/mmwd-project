package pl.edu.agh.student.simulatedannealing.model;

import pl.edu.agh.student.simulatedannealing.mutator.Mutator;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Szymek on 2017-01-03.
 */
public class ComputationStateMutator implements Mutator<ComputationState> {
    Set<Pizza> pizzasToDeliver = new HashSet<>();

    @Override
    public ComputationState getNext(ComputationState computationState) {
        return null; //todo generate new solutions
    }
}
