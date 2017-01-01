package pl.edu.agh.student.simulatedannealing.pizzas;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class Solution {

    private List<Route> routes;

    private static Random rand = new Random();
    static void setRand(Random generator) {
        rand = generator;
    }

    Solution(List<Route> routeList) {
        this.routes = new LinkedList<>();
        routes.addAll(routeList);
    }

    Solution(Solution other) {
        this.routes = new LinkedList<>();
        for (Route route : other.getRoutes())
            routes.add(new Route(route));
    }

    List<Route> getRoutes() {
        return routes;
    }

    void removeOrder(PizzaOrder order) {
        for (Route next : routes) {
            if (next.getList().remove(order))
                return;
        }
        assert false : "Couldn't find the order to be removed.";
    }

    boolean consistsOfValidRoutes() {
        return routes.stream().allMatch(Route::isValid);
    }

    // returns all PizzaOrders making up the Solution's routes
    List<PizzaOrder> getOrders() {
        return routes.stream()
                //.flatMap(route -> route.getList().stream())
                .map(Route::getList)
                .flatMap(Collection::stream)
                .filter(checkpoint -> checkpoint instanceof PizzaOrder)
                .map(checkpoint -> (PizzaOrder) checkpoint)
                .collect(Collectors.toList());
    }

    /*
    try inserting into each route consecutively until success (return true)
    or route list exhaustion (return false)
    */
    boolean insertOrderIntoRandomValidRoute(PizzaOrder order) {
        List<Route> candidateRoutes = new LinkedList<>(routes);
        Collections.shuffle(candidateRoutes, rand);

        for (Route candidate : candidateRoutes)
            if (candidate.insertOrderAtRandomValidPosition(order))
                return true;
        return false;
        //return candidateRoutes.stream().anyMatch(candidate -> candidate.insertOrderAtRandomValidPosition(order));
    }

    @Override
    public String toString() {
        String str = "";
        int i = 1;
        for (Route route : getRoutes()) {
            str += "route " + i++ + " {" + route.getList().size() + "} :\n" + route + "\n";
        }
        return str;
    }
}
