package pl.edu.agh.student.simulatedannealing.pizzas;

import pl.edu.agh.student.simulatedannealing.solver.SimulatedAnnealingSolver;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PizzaApp {
    public static void main(String[] args) {
        //TODO: replace RNG in the solver (field ?)
        Random randGen = new Random(); //TODO: seed

        List<PizzaOrder> newOrders = new LinkedList<>();
        initializeOrders(newOrders);

        List<Route> driverRoutes = new LinkedList<>();
        initializeRoutes(driverRoutes);
        Solution initialSolution = new Solution(driverRoutes);

        List<PizzaOrder> mandatoryOrders = initialSolution.getOrders();

        System.out.println("initialSolution:\n" + initialSolution);

        SimulatedAnnealingSolver.Builder<Solution> solverBuilder = SimulatedAnnealingSolver.getBuilder();
        SimulatedAnnealingSolver<Solution> solver = solverBuilder
                .setGenerateChild(new ChildGenerator(mandatoryOrders, newOrders, randGen))
                .setObjectiveFunction(sol -> (double) -1 * sol.getOrders().size())
                .setStartingTemperature(10000.0).setAlpha(0.96).build();
        Solution solution = solver.solve(initialSolution, 1000000);

        System.out.println("finalSolution:\n" + solution);

    }

    private static void initializeOrders(List<PizzaOrder> list) {
        //(x, y, deadline)
        int orders[][] = {
                {1, 2, 10},
                {0, 5, 15},
                {3, 4, 12},
                {4, 4, 20},
                {5, 4, 21},
                {5, 4, 3},
                {100, 100, 20},
                {10, 4, 30},
                {-1, 2, 13}

        };

        for (int[] order : orders) {
            if (3 == order.length) {
                list.add(new PizzaOrder(order[0], order[1], order[2]));
            } else {
                throw new IllegalArgumentException("Pizza orders should contain three elements (x, y, t).");
            }

        }
    }
    private static void initializeRoutes(List<Route> list) throws IllegalArgumentException {
        int routeArr[][][] = {
                //(x, y, deadline)
                {// first route
                        {1, 2, 10},
                        {2, 4, 8},
                        {4, 4, 13},
                        {3, 2, 12},
                },
                {// second route
                        {20, 2, 10},
                        {22, 4, 8},
                        {14, 4, 23},
                        {13, 2, 22},
                }
        };

        for (int[][] checkpointArr : routeArr) {
            List<Checkpoint> route = new LinkedList<>();
            for(int[] checkpoint : checkpointArr) {
                if (3 == checkpoint.length) {
                    route.add(new PizzaOrder(checkpoint[0], checkpoint[1], checkpoint[2]));
                } else if (2 == checkpoint.length) {
                    route.add(new Checkpoint(checkpoint[0], checkpoint[1]));
                } else {
                    throw new IllegalArgumentException("The innermost arrays should store exactly two or exactly three integers.");
                }
            }
            Route toBeAdded = new Route(route);
            try {
                if (!toBeAdded.isValid()) throw new AssertionError();
                list.add(toBeAdded);
            } catch (AssertionError e) {
                System.out.println("The following route is not valid:\n" + toBeAdded);
                throw e;
            }
            //list.add(new Route(route));
        }
    }

}
