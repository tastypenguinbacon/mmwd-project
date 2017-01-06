package pl.edu.agh.student.simulatedannealing.solver;

import pl.edu.agh.student.simulatedannealing.mutator.Mutator;
import pl.edu.agh.student.simulatedannealing.statistics.Statistics;
import pl.edu.agh.student.simulatedannealing.temperature.Temperature;

import java.util.Random;
import java.util.function.Function;

import static java.lang.Math.*;

public class SimulatedAnnealingSolver<CurrentState> {
    private final Mutator<CurrentState> mutator;
    private final Temperature temperature;
    private final Function<CurrentState, Double> objectiveFunction;
    private Statistics statistics = new Statistics();

    public SimulatedAnnealingSolver(Mutator<CurrentState> mutator, Temperature temperature,
                                    Function<CurrentState, Double> objectiveFunction) {
        this.mutator = mutator;
        this.temperature = temperature;
        this.objectiveFunction = objectiveFunction;
    }

    public CurrentState solve(CurrentState startingPoint, int maxSteps) {
        CurrentState best = startingPoint;
        CurrentState current = startingPoint;
        statistics.add(0, getObjectiveFunctionValue(startingPoint));
        for (int step = 0; step < maxSteps; step++) {
            double currentTemperature = temperature.getNextTemperature();
            CurrentState child = mutator.getNext(current);
            statistics.add(step + 1, getObjectiveFunctionValue(child));
            double deltaE = getObjectiveFunctionValue(current) - getObjectiveFunctionValue(child);

            if (getObjectiveFunctionValue(child) > getObjectiveFunctionValue(best)) {
                best = child;
            }
            if (shouldAccept(deltaE, currentTemperature)) {
                current = child;
            }
        }
        return best;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    private double getObjectiveFunctionValue(CurrentState sol) {
        return objectiveFunction.apply(sol);
    }

    private boolean shouldAccept(double deltaE, double T) {
        return (deltaE < 0) || new Random().nextDouble() < exp(-1 * deltaE / T);
    }
}
