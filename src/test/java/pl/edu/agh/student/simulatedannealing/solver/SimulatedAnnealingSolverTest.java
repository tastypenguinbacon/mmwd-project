package pl.edu.agh.student.simulatedannealing.solver;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Created by pingwin on 10.12.16.
 */
public class SimulatedAnnealingSolverTest {
    private static final String optimal = "abcdefghijklmnopqrstuvwxyz";

    private static double hammingDistance(String first, String second) {
        if (first.length() != second.length())
            return -1;

        int res = 0;
        for (int i = 0; i < first.length(); i++)
            res += (first.charAt(i) == second.charAt(i)) ? 0 : 1;
        return res;
    }

    private static String mutate(String str) {
        Random r1 = new Random();
        int position = r1.nextInt(str.length());

        Random r2 = new Random();
        char ch = (char) (r2.nextInt(26) + 'a');

        StringBuilder newStr = new StringBuilder(str);
        newStr.setCharAt(position, ch);

        return newStr.toString();
    }

    @Test
    public void whenUsingHammingDistance() {
        SimulatedAnnealingSolver.Builder<String> solverBuilder = SimulatedAnnealingSolver.getBuilder();
        SimulatedAnnealingSolver<String> solver = solverBuilder
                .setGenerateChild(SimulatedAnnealingSolverTest::mutate)
                .setObjectiveFunction( (String str) -> hammingDistance(str, optimal))
                .setStartingTemperature(10.0).setAlpha(0.97).build();
        String solution = solver.solve("kmgniwsodfhydmsferinoqtngh", 2500);
        assertTrue(hammingDistance(optimal, solution) < 3);
    }
}