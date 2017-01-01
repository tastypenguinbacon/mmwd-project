package pl.edu.agh.student.simulatedannealing.pizzas;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

import static java.lang.Math.min;

class ChildGenerator implements UnaryOperator<Solution> {

    private final List<PizzaOrder> oldOrders;
    private final List<PizzaOrder> newOrders;

    //TODO: initialize in constructor or setter
    private final int maxInsertionAttempts = 10;
    private Random rand = new Random();

    void setRand(Random generator) {
        rand = generator;
    }

    ChildGenerator(List<PizzaOrder> oldOrders, List<PizzaOrder> newOrders) {
        this(oldOrders, newOrders, new Random());
    }

    ChildGenerator(List<PizzaOrder> oldOrders, List<PizzaOrder> newOrders, Random generator) {
        this.oldOrders = oldOrders;
        this.newOrders = newOrders;
        this.rand = generator;

        // for reproducibility
        Solution.setRand(rand);
        Route.setRand(rand);
    }

    /* return true on successful removal */
    private boolean removeRandomOrder(Solution solution) {

        List<PizzaOrder> chosenOrders = solution.getOrders();
        chosenOrders.removeAll(oldOrders);

        if (chosenOrders.isEmpty()) {
            return false;
        }
        solution.removeOrder(chosenOrders.get(rand.nextInt(chosenOrders.size())));
        return true;
    }

    /* return true on successful insertion */
    private boolean addOrderFromList(Solution solution, List<PizzaOrder> candidateOrders) {
        for (PizzaOrder toBeInserted : candidateOrders) {
            if (solution.insertOrderIntoRandomValidRoute(toBeInserted))
                return true;
        }
        return false;
    }

    private boolean checkSolutionValidity(Solution solution) {
        return solution.getOrders().containsAll(oldOrders) && solution.consistsOfValidRoutes();
    }

    // new Solution must be a new object
    @Override
    public Solution apply(Solution parent) {
        Solution child = new Solution(parent);

        // shallow copy
        List<PizzaOrder> candidateOrders = new LinkedList<>(newOrders);
        candidateOrders.removeAll(child.getOrders());
        Collections.shuffle(candidateOrders, rand);
        int limit = min(candidateOrders.size(), maxInsertionAttempts);

        boolean modified = addOrderFromList(child, candidateOrders.subList(0, limit));
        if (!modified)
            modified = removeRandomOrder(child);
        if (!modified)
            addOrderFromList(child, candidateOrders.subList(limit, candidateOrders.size()));

        assert checkSolutionValidity(child) : "Solution isn't valid. Code is buggy!!!";
        return child;
        //TODO: cleanup
        /*if (!addOrderFromList(child, candidateOrders.subList(0, limit)))
            if (!removeRandomOrder(child))
                addOrderFromList(child, candidateOrders.subList(limit, candidateOrders.size()));
        return child;*/
    }


}
