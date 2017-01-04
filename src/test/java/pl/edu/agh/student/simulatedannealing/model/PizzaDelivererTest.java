package pl.edu.agh.student.simulatedannealing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ja on 2017-01-04.
 */
public class PizzaDelivererTest {

    PizzaDeliverer deliverer;

    @Before
    public void setUp() {
        Point delivererPosition = new Point(-1, 0);
        PizzaDeliverer deliverer = new PizzaDeliverer(delivererPosition);
        Set<Pizza> pizzasWeAreObligatedToDeliver = new HashSet<>();
        Set<Pizza> pizzasWeCouldDeliver = new HashSet<>();
        List<Point> route = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            Pizza newPizza = new Pizza(new Point(i, i), new Point(i, i));
            Pizza couldBeDelivered = new Pizza(new Point(i, i), new Point(i+1, i+1));
            pizzasWeAreObligatedToDeliver.add(newPizza);
            pizzasWeCouldDeliver.add(couldBeDelivered);
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