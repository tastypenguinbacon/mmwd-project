package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pingwin on 01.01.17.
 */
public class PizzaDeliverer implements Cloneable {
    
    private Set<Pizza> pizzasWeAreObligatedToDeliver = new HashSet<>();
    private Set<Pizza> pizzasWeCouldDeliver = new HashSet<>();
    private List<Point> route = new LinkedList<>();

    private Point currentPosition;

    public PizzaDeliverer() {
    }

    public PizzaDeliverer(Point position) {
        this.currentPosition = position;
    }

    /**
     * This method is intended to return true when:
     * all pizzas from pizzasWeAreObligatedToDeliver and pizzasWeCouldDeliver are being delivered
     * and all pizzas are delivered before getting cold,
     * and pizzas from pizzasWeCouldDeliver are first picked up, and only later delivered,
     * It should return false otherwise.
     *
     * @return true when is able to collect the pizzas and false if at least one of them is cold after delivery
     */
    @JsonIgnore
    public boolean isAbleToCollectThePizzas() {
        return false; // todo - implement
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
        delivererClone.pizzasWeCouldDeliver = pizzasWeCouldDeliver.stream().collect(Collectors.toSet());
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
    public Collection<Pizza> getAssignedPizzas() {
        return new HashSet<>(pizzasWeCouldDeliver);
    }

    /**
     * Should be used while mutating the current state.
     * @param pizza pizza to be removed from the pizzas we could deliver
     */
    public void removePizza(Pizza pizza) {
        pizzasWeCouldDeliver.remove(pizza);
    }


    /**
     * These are the pizzas the deliverer already took with him. Should be used by the Json parsers.
     * @return the pizzas the deliverer has already with him.
     */
    public Set<Pizza> getPizzasWeAreObligatedToDeliver() {
        return pizzasWeAreObligatedToDeliver;
    }

    /**
     * Should be used mostly for Json deserialization purposes.
     * @param pizzasWeAreObligatedToDeliver
     */
    public void setPizzasWeAreObligatedToDeliver(Set<Pizza> pizzasWeAreObligatedToDeliver) {
        this.pizzasWeAreObligatedToDeliver = pizzasWeAreObligatedToDeliver;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PizzaDeliverer deliverer = (PizzaDeliverer) o;

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
