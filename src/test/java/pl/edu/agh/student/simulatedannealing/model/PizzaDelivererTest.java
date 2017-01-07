package pl.edu.agh.student.simulatedannealing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ja on 2017-01-04.
 */
public class PizzaDelivererTest {
    @Test
    public void checkThatInsertionIsNotPossibleTest() {
        //todo test for insertion
        Pizza toBeInserted = new Pizza();
        Point start = new Point(-1,-1);
        Point destination = new Point(1,2);
        int timeUntilCold = 5;

        toBeInserted.setPosition(start);
        toBeInserted.setDestination(destination);
        toBeInserted.setTimeUntilCold(timeUntilCold);

        boolean wasInserted = deliverer.attemptInsertingPizza(toBeInserted);

        assertFalse(wasInserted);
    }

    @Test
    public void checkThatInsertionIsPossibleTest() {
        //todo test for insertion
        Pizza toBeInserted = new Pizza();
        Point start = new Point(-1,-1);
        Point destination = new Point(1,2);
        int timeUntilCold = 6;

        toBeInserted.setPosition(start);
        toBeInserted.setDestination(destination);
        toBeInserted.setTimeUntilCold(timeUntilCold);

        boolean wasInserted = deliverer.attemptInsertingPizza(toBeInserted);

        assertTrue(wasInserted);
    }

    @Test
    public void routeIsShorterAfterRemovalTest() {
        Point start = new Point(2,2);
        Point destination = new Point(3,3);
        Pizza toBeRemoved = new Pizza(start, destination);
        int originalRouteSize = deliverer.getRoute().size();

        //assert valid setup
        assert (deliverer.getAssignedPizzas().contains(toBeRemoved));

        deliverer.removePizza(toBeRemoved);
        int newRouteSize = deliverer.getRoute().size();

        //the route should in general be shorter or stay the same
        //in this specially prepared test case, route lenght should be decrease
        assertTrue(newRouteSize < originalRouteSize);
    }

    private PizzaDeliverer deliverer;

    @Before
    public void setUp() {
        Point delivererPosition = new Point(-1, 0);
        PizzaDeliverer deliverer = new PizzaDeliverer(delivererPosition);
        List<Pizza> pizzasWeAreObligatedToDeliver = new LinkedList<>();
        //List<Pizza> pizzasWeCouldDeliver = new LinkedList<>();
        List<Point> route = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            Pizza newPizza = new Pizza(new Point(i, i), new Point(i, i));
            Pizza couldBeDelivered = new Pizza(new Point(i, i), new Point(i+1, i+1));
            pizzasWeAreObligatedToDeliver.add(newPizza);
            //pizzasWeCouldDeliver.add(couldBeDelivered);
            route.add(new Point(i,i));

            deliverer.addPizza(couldBeDelivered);
        }
        route.add(new Point(3,3));

        deliverer.setRoute(route);
        deliverer.setPizzasWeAreObligatedToDeliver(pizzasWeAreObligatedToDeliver);

        this.deliverer = deliverer;
    }

    @Test
    public void isAbleToCollectThePizzasTest() {
        assertTrue(deliverer.isAbleToCollectThePizzas());
    }

    @Test
    public void isNotAbleToCollectThePizzasTest() {
        deliverer.addPizza(new Pizza(new Point(5,5), new Point(4,6)));
        assertFalse(deliverer.isAbleToCollectThePizzas());
    }

}