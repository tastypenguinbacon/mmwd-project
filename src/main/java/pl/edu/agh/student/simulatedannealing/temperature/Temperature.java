package pl.edu.agh.student.simulatedannealing.temperature;

/**
 * Created by pingwin on 02.01.17.
 */
@FunctionalInterface
public interface Temperature {
    double getNextTemperature();
}
