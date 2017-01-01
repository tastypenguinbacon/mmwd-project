package pl.edu.agh.student.simulatedannealing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pingwin on 01.01.17.
 */
public class PizzaDeliverer implements Cloneable {
    
    private Set<Pizza> pizzasWeAreObligatedToDeliver = new HashSet<>();
    private Set<Pizza> pizzasWeCouldDeliver = new HashSet<>();

    public PizzaDeliverer() {
    }

    @JsonIgnore
    public boolean isAbleToCollectThePizzas() {
        return false;
    }

    public void addPizza(Pizza pizza) {
        pizzasWeCouldDeliver.add(pizza);
    }

    public void removePizza(Pizza pizza) {
        pizzasWeCouldDeliver.remove(pizza);
    }

    @JsonIgnore
    public Collection<Pizza> getAssignedPizzas() {
        return null;
    }

    public Set<Pizza> getPizzasWeAreObligatedToDeliver() {
        return pizzasWeAreObligatedToDeliver;
    }

    public void setPizzasWeAreObligatedToDeliver(Set<Pizza> pizzasWeAreObligatedToDeliver) {
        this.pizzasWeAreObligatedToDeliver = pizzasWeAreObligatedToDeliver;
    }

    @Override
    public PizzaDeliverer clone() throws CloneNotSupportedException {
        PizzaDeliverer delivererClone = (PizzaDeliverer) super.clone();
        delivererClone.pizzasWeAreObligatedToDeliver = pizzasWeAreObligatedToDeliver;
        delivererClone.pizzasWeCouldDeliver = pizzasWeCouldDeliver.stream().collect(Collectors.toSet());
        return delivererClone;
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
