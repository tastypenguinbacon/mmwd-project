package pl.edu.agh.student.simulatedannealing.solver;

import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.lang.Math.*;

public class SimulatedAnnealingSolver<Solution> {
    private Solution current;
    private final UnaryOperator<Solution> generateChild;
    private final Function<Solution, Double> objectiveFunction;
    private double startingTemperature;
    private final double alpha;

    private SimulatedAnnealingSolver(Builder<Solution> builder) {
        this.generateChild = builder.generateChild;
        this.objectiveFunction = builder.objectiveFunction;
        this.alpha = builder.alpha;
        this.startingTemperature = builder.startingTemperature;
    }

    public Solution solve(Solution startingPoint, int maxSteps) {
        Solution best = startingPoint;
        current = startingPoint;
        double temperature = this.startingTemperature;
        int bestStep = -1;

        for (int step = 0; step < maxSteps; step++) {
            Solution child = generateChild.apply(current);
            double deltaE = getValue(child) - getValue(current);

            // TODO: refactor double check for deltaE < 0
            if (deltaE < 0) {
                best = child;
                bestStep = step;
            }
            if (shouldAccept(deltaE, temperature)) {
                current = child;
            }
            temperature = temperature * alpha;
        }
        return best;
    }

    private double getValue(Solution sol) {
        return objectiveFunction.apply(sol);
    }

    private boolean shouldAccept(double deltaE, double T) {
        return (deltaE < 0) || new Random().nextDouble() < exp(-1 * deltaE / T);
    }

    public static <E> Builder<E> getBuilder() {
        return new Builder<>();
    }

    public static class Builder<E> {
        private UnaryOperator<E> generateChild;
        private Function<E, Double> objectiveFunction;
        private double startingTemperature;
        private double alpha;

        public Builder<E> setGenerateChild(UnaryOperator<E> generateChild) {
            this.generateChild = generateChild;
            return this;
        }

        public Builder<E> setObjectiveFunction(Function<E, Double> objectiveFunction) {
            this.objectiveFunction = objectiveFunction;
            return this;
        }

        public Builder<E> setStartingTemperature(double startingTemperature) {
            this.startingTemperature = startingTemperature;
            return this;
        }

        public Builder<E> setAlpha(double alpha) {
            this.alpha = alpha;
            return this;
        }

        public SimulatedAnnealingSolver<E> build() {
            return new SimulatedAnnealingSolver<>(this);
        }
    }
}
