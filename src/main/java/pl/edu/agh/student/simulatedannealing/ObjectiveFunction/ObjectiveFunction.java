package pl.edu.agh.student.simulatedannealing.ObjectiveFunction;

import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;

import java.util.function.Function;

/**
 * Created by pingwin on 06.01.17.
 */
public class ObjectiveFunction implements Function<ComputationState, Double> {
    @Override
    public Double apply(ComputationState computationState) {
        int summOfPizzasDelivered = 0;
        for (PizzaDeliverer deliverer : computationState.getPizzaDeliverers()) {
            if (!deliverer.isAbleToCollectThePizzas()) {
                return 0.0;
            }
            summOfPizzasDelivered += deliverer.getAssignedPizzas().size();
        }
        return (double) summOfPizzasDelivered;
    }
}
