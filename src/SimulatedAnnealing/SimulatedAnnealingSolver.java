package SimulatedAnnealing;

import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class SimulatedAnnealingSolver<Solution> {
    private Solution current;
    private final UnaryOperator<Solution> generateChild;
    private final Function<Solution, Double> objectiveFunction;
    private double temperature;
    private final int maxSteps;
    private double alpha;

    private Solution best;

    public SimulatedAnnealingSolver(Solution initialSolution,
                                UnaryOperator<Solution> generateChild,
                                Function<Solution, Double> objectiveFunction) {
        this(initialSolution, generateChild, objectiveFunction, 10.0, 50, 0.92);
    }

    public SimulatedAnnealingSolver(Solution initialSolution,
                                    UnaryOperator<Solution> generateChild,
                                    Function<Solution, Double> objectiveFunction,
                                    double temperature,
                                    int maxSteps,
                                    double alpha) {
        this.current = initialSolution;
        this.generateChild = generateChild;
        this.objectiveFunction = objectiveFunction;
        this.maxSteps = maxSteps;
        this.temperature = temperature;
        this.alpha = alpha;
    }
    // algorytm
    public void find(){
        for(int step = 0; step < maxSteps; step++) {
/////////////////////////
            System.out.println("step: " + step +
                    "\trozwiązanie: " + current +
                    " , wartość: " + getValue(current) +
                    " , T: " + temperature);
/////////////////////////
            Solution child = generateChild.apply(current);
            double deltaE = getValue(child) - getValue(current);
            if ( shouldAccept(deltaE, temperature) )
                current = child;
            temperature = temperature * alpha;
        }
        System.out.println("\nKONIEC\nrozwiązanie: " + current + " , wartość: " + getValue(current));
    }

    private double getValue(Solution sol){
        return objectiveFunction.apply(sol);
    }

    private boolean shouldAccept(double deltaE, double T){
        return (deltaE < 0) || new Random().nextDouble() < Math.exp( -1 * deltaE / T );
    }

}
