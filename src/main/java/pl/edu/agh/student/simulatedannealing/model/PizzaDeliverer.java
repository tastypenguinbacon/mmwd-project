package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pingwin on 01.01.17.
 */
public class PizzaDeliverer implements Cloneable {


    private List<Pizza> pizzasWeAreObligatedToDeliver = new LinkedList<>();
    private List<Pizza> pizzasWeCouldDeliver = new LinkedList<>();
    private List<Point> route = new LinkedList<>();

    private Point currentPosition;

    public PizzaDeliverer() {
    }

    public PizzaDeliverer(Point position) {
        this.currentPosition = position;
    }

    //copy constructor
    public PizzaDeliverer(PizzaDeliverer other) {
        this.pizzasWeAreObligatedToDeliver = other.pizzasWeAreObligatedToDeliver;
        this.pizzasWeCouldDeliver = new LinkedList<>(other.pizzasWeCouldDeliver);
        this.route = new LinkedList<>(other.route);
        this.currentPosition = other.currentPosition;
    }

    /**
     * This method is intended to return true when:
     * all pizzas from pizzasWeAreObligatedToDeliver and pizzasWeCouldDeliver are being delivered
     * and all pizzas are delivered before getting cold,
     * and pizzas from pizzasWeCouldDeliver are first picked up, and only later delivered,
     * It should return false otherwise.
     *
     * @return true when is able to collect and deliver hot pizzas
     * and return false if at least one pizza is cold after delivery
     */
    @JsonIgnore
    public boolean isAbleToCollectThePizzas() {
        final List<Pizza> backpack = new LinkedList<>(pizzasWeAreObligatedToDeliver);
        final List<Pizza> delivered = new LinkedList<>();

        Consumer<Point> pickUpPizzasFromPoint = (point) -> {
            for (Pizza pizza : pizzasWeCouldDeliver) {
                if (pizza.getPosition().equals(point)) {
                    backpack.add(pizza);
                }
            }
        };

        BiFunction<Point, Integer, Boolean> deliverPizzasToPoint = (point, time) -> {
            Iterator<Pizza> iter = backpack.iterator();
            while (iter.hasNext()) {
                Pizza pizza = iter.next();
                if (pizza.getDestination().equals(point)) {
                    if (pizza.getTimeUntilCold() >= time) {
                        iter.remove();
                        delivered.add(pizza);
                    } else
                        return false;
                }
            }
            return true;
        };

        Point currentPoint = currentPosition;
        int time = 0;

        pickUpPizzasFromPoint.accept(currentPoint);
        if (!deliverPizzasToPoint.apply(currentPoint, time))
            return false;

        //traverse the route picking up and delivering pizzas
        for (Point point : route) {
            Point prev = currentPoint;
            currentPoint = point;
            time += currentPoint.distanceTo(prev);

            pickUpPizzasFromPoint.accept(currentPoint);
            if(!deliverPizzasToPoint.apply(currentPoint, time))
                return false;
        }

        return backpack.isEmpty()
                && delivered.containsAll(pizzasWeAreObligatedToDeliver)
                && delivered.containsAll(pizzasWeCouldDeliver);
    }

    /**
     * VERRY IMPORTANT! The pizza deliverer has to be cloned before any changes are applied to him! If he is not cloned
     * then we are not able to restore him to the previous state in case of a failed assumption.
     *
     * pizzasWeAreObligatedToDeliver should be immutable
     * every pizza on its own is also immutable, so a shallow copy is enough
     *
     * @return returns a copy of the object
     * @throws CloneNotSupportedException should be never thrown is just because of Java reasons
     */
    @Override
    public PizzaDeliverer clone() throws CloneNotSupportedException {
        PizzaDeliverer delivererClone = (PizzaDeliverer) super.clone();
        delivererClone.pizzasWeAreObligatedToDeliver = pizzasWeAreObligatedToDeliver;
        delivererClone.pizzasWeCouldDeliver = pizzasWeCouldDeliver.stream().collect(Collectors.toList());
        delivererClone.route = route.stream().collect(Collectors.toList());
        delivererClone.currentPosition = currentPosition;
        return delivererClone;
    }

    /**
     * Should be used while mutating the current state.
     * @param pizza the Pizza object to be added to the deliverer
     */
    public void addPizza(Pizza pizza) {
        pizzasWeCouldDeliver.add(pizza);
    }

    /**
     * Attempt inserting pizza and updating deliverer's route.
     * @param pizza the Pizza object to be added to the deliverer
     * @return true upon successful insertion, and false otherwise.
     */

    public boolean attemptInsertingPizza(Pizza pizza) {
        pizzasWeCouldDeliver.add(pizza);
        if (isAbleToCollectThePizzas())
            return true;
        pizzasWeCouldDeliver.remove(pizza);

        return attemptInsertingPizzaAtRandomPosition(pizza);
    }

    private boolean attemptInsertingPizzaAtRandomPosition(Pizza pizza) {
        List<Point> copyOfRoute = new LinkedList<>(route);
        final Point start = pizza.getPosition();
        final Point destination = pizza.getDestination();

        List<Integer> validStartIndexes = resolveValidStartIndexes(start);

        assert (route.containsAll(copyOfRoute) && copyOfRoute.containsAll(route));

        //todo maybe move rand to method parameter
        Random rand = new Random();
        Collections.shuffle(validStartIndexes, rand);
        while (!validStartIndexes.isEmpty()) {
            int startIndex = validStartIndexes.remove(0);


            List<Integer> validDestinationIndexes = new LinkedList<>();
            route.add(startIndex, start);

            int index = startIndex + 1;
            route.add(index, destination);
            if (isAbleToCollectThePizzas()) {
                if (timeAtPoint(index) <= pizza.getTimeUntilCold())
                    validDestinationIndexes.add(index);
            }
            for (index++; index < route.size(); index++) {
                Collections.swap(route, index, index - 1);
                if (isAbleToCollectThePizzas())
                    if (timeAtPoint(index) <= pizza.getTimeUntilCold())
                        validDestinationIndexes.add(index);
                        //todo maybe continue/break on fail
                        //and remove assertion
            }
            assert (index == route.size());
            route.remove(index - 1); //remove destination

            if (validDestinationIndexes.isEmpty()) {
                route.remove(startIndex);
            } else {
                Collections.shuffle(validDestinationIndexes);
                route.add(validDestinationIndexes.get(0), destination);
                pizzasWeCouldDeliver.add(pizza);
                return true;
            }
        }

        assert (route.containsAll(copyOfRoute) && copyOfRoute.containsAll(route));
        return false;
    }

    //helper method
    // determines time when deliverer reaches the specified point on the route
    private int timeAtPoint(int index) {
        assert (index >= 0 && index < route.size());

        Point currentPoint = currentPosition;
        int currentIndex = 0;
        int time = 0;
        for (Point point : route) {
            Point prev = currentPoint;
            currentPoint = point;
            time += currentPoint.distanceTo(prev);
            if (index == currentIndex)
                return time;

            currentIndex++;
        }

        //should not be reached
        return -1;
    }

    private List<Integer> resolveValidStartIndexes(Point start) {
        List<Integer> validStartIndexes = new LinkedList<>();
        int index = 0;
        route.add(index, start);
        if (isAbleToCollectThePizzas()) {
            validStartIndexes.add(index);
        }
        for (index = 1; index < route.size(); index++) {
            Collections.swap(route, index, index - 1);
            if (isAbleToCollectThePizzas()) {
                validStartIndexes.add(index);
            }
        }
        assert (index == route.size());
        route.remove(index - 1);

        return validStartIndexes;
    }

    /**
     * Returns a collection of pizzas which could be delivered. It's a snapshot copy of the original set to avoid
     * infinite loops like:
     *
     * for (Pizza pizza : deliverer.getAssignedPizzas()) {
     *     deliverer.addPizza(pizza);
     * }
     *
     * @return Returns the pizzas which could be delivered
     */
    @JsonIgnore
    public Collection<Pizza> getAssignedPizzas() { return new LinkedList<>(pizzasWeCouldDeliver); }

    @JsonIgnore
    public List<Pizza> getPizzasWeCouldDeliver() { return pizzasWeCouldDeliver; }

    /**
     * Should be used while mutating the current state.
     * It removes the pizza from the list of assigned pizzas.
     * Then tries to shorten the route (remove pickup and delivery points).
     * If the route shortening was not possible leaves the route unchanged.
     *
     * @param pizza pizza to be removed from the pizzas we could deliver
     */
    public void removePizza(Pizza pizza) {
        pizzasWeCouldDeliver.remove(pizza);

        List<Point> copyOfRoute = new LinkedList<>(route);

        //try shortening route if possible possible
        final List<Pizza> backpack = new LinkedList<>(pizzasWeAreObligatedToDeliver);

        //pick up pizzas and return number of pizzas picked up at this point
        Function<Point, Integer> pickUpPizzasFromPoint = (point) -> {
            int count = 0;
            for (Pizza el : pizzasWeCouldDeliver) {
                if (el.getPosition().equals(point)) {
                    backpack.add(el);
                    count++;
                }
            }
            return count;
        };

        //deliver and return number of pizzas delivered at this point
        Function<Point, Integer> deliverPizzasToPoint = (point) -> {
            int count = 0;
            Iterator<Pizza> iter = backpack.iterator();
            while (iter.hasNext()) {
                Pizza el = iter.next();
                if (el.getDestination().equals(point)) {
                    iter.remove();
                    count++;
                }
            }
            return count;
        };

        int pickupIndex = route.indexOf(pizza.getPosition());
        int deliveryIndex = pickupIndex +
                route.subList(pickupIndex, route.size()).indexOf(pizza.getDestination());

        boolean safeToRemoveStart = false;
        boolean safeToRemoveDestination = false;

        //starting point cannot be removed but route has to be traversed
        pickUpPizzasFromPoint.apply(currentPosition);
        deliverPizzasToPoint.apply(currentPosition);

        //traverse the route picking up and delivering pizzas
        int currentIndex = 0;
        for (Point currentPoint : route) {
            int pickedUp = pickUpPizzasFromPoint.apply(currentPoint);
            int delivered = deliverPizzasToPoint.apply(currentPoint);

            if (currentIndex == pickupIndex)
                safeToRemoveStart = (0 == pickedUp && 0 == delivered);
            if (currentIndex == deliveryIndex) {
                safeToRemoveDestination = (0 == pickedUp && 0 == delivered);
                break;
            }

            currentIndex++;
        }

        if (pickupIndex == deliveryIndex) {
            if (safeToRemoveStart && safeToRemoveDestination)
                route.remove(pickupIndex);
        } else {
            if (safeToRemoveDestination)
                route.remove(deliveryIndex);
            if (safeToRemoveStart)
                route.remove(pickupIndex);
        }

        //todo check if this can be removed (the route should never be broken after pizza removal)
        //if the new route is not valid fallback to starting route
        if (!isAbleToCollectThePizzas()) {
            route = copyOfRoute;
        }
    }

    /**
     * These are the pizzas the deliverer already took with him. Should be used by the Json parsers.
     * @return the pizzas the deliverer has already with him.
     */
    public List<Pizza> getPizzasWeAreObligatedToDeliver() {
        return pizzasWeAreObligatedToDeliver;
    }

    /**
     * Should be used mostly for Json deserialization purposes.
     * @param pizzasWeAreObligatedToDeliver
     */
    public void setPizzasWeAreObligatedToDeliver(List<Pizza> pizzasWeAreObligatedToDeliver) {
        this.pizzasWeAreObligatedToDeliver = pizzasWeAreObligatedToDeliver;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setRoute(List<Point> route) { this.route = route; }

    public List<Point> getRoute() { return this.route; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PizzaDeliverer deliverer = (PizzaDeliverer) o;

        if (currentPosition != null ? !currentPosition.equals(deliverer.currentPosition) : deliverer.currentPosition != null)
            return false;

        if (pizzasWeAreObligatedToDeliver != null ? !pizzasWeAreObligatedToDeliver.equals(deliverer.pizzasWeAreObligatedToDeliver) : deliverer.pizzasWeAreObligatedToDeliver != null)
            return false;
        return pizzasWeCouldDeliver != null ? pizzasWeCouldDeliver.equals(deliverer.pizzasWeCouldDeliver) : deliverer.pizzasWeCouldDeliver == null;
    }

    @Override
    public int hashCode() {
        int result = pizzasWeAreObligatedToDeliver != null ? pizzasWeAreObligatedToDeliver.hashCode() : 0;
        result = 31 * result + (pizzasWeCouldDeliver != null ? pizzasWeCouldDeliver.hashCode() : 0);
        return result;
    }
}
