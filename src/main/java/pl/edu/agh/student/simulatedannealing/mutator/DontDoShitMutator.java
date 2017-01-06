package pl.edu.agh.student.simulatedannealing.mutator;

import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

/**
 * Created by pingwin on 05.01.17.
 */
public class DontDoShitMutator implements Mutator<ComputationState> {
    @Override
    public ComputationState getNext(ComputationState computationState) {
        return computationState;
    }
}
