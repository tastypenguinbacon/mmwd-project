package pl.edu.agh.student.simulatedannealing.temperature;

/**
 * Created by pingwin on 02.01.17.
 */
public class ExponentiallyDecreasingTemperature implements Temperature {
    private final double alpha;
    private double currentTemperature;

    public ExponentiallyDecreasingTemperature(double startingTemperature, double alpha) {
        this.currentTemperature = startingTemperature;
        this.alpha = alpha;
    }

    @Override
    public double getNextTemperature() {
        double temperatureToReturn = currentTemperature;
        currentTemperature *= alpha;
        return temperatureToReturn;
    }
}
