package pl.edu.agh.student.simulatedannealing.solver;

import org.junit.Test;
import pl.edu.agh.student.simulatedannealing.mutator.Mutator;
import pl.edu.agh.student.simulatedannealing.temperature.ExponentiallyDecreasingTemperature;
import pl.edu.agh.student.simulatedannealing.temperature.Temperature;

import java.util.Random;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;

/**
 * Created by pingwin on 10.12.16.
 */
public class SimulatedAnnealingSolverTest {
    @Test
    public void givenQuadraticEquationFindMinimum() {
        Mutator<Double> mutator = current -> current + new Random().nextGaussian() / 10;
        Temperature temperature = new ExponentiallyDecreasingTemperature(100, 0.997);
        Function<Double, Double> objectiveFunction = x -> -Math.pow(x - 3, 2) + 1;
        SimulatedAnnealingSolver<Double> solver = new SimulatedAnnealingSolver<>(mutator, temperature, objectiveFunction);
        double solution = solver.solve(-10.0, 3000);
        System.out.println(solution);
        assertTrue(Math.abs(solution - 3.0) < 0.01);
    }
}