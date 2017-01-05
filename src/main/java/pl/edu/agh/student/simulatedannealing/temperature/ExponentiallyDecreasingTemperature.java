package pl.edu.agh.student.simulatedannealing.temperature;

/**
 * Created by pingwin on 02.01.17.
 */
public class ExponentiallyDecreasingTemperature implements Temperature {
    private double alpha;
    private double currentTemperature;

    public ExponentiallyDecreasingTemperature(double startingTemperature, double alpha) {
        this.currentTemperature = startingTemperature;
        this.alpha = alpha;
    }

    public ExponentiallyDecreasingTemperature() {
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setStartingTemperature(double startingTemperature) {
        this.currentTemperature = startingTemperature;
    }

    @Override
    public double getNextTemperature() {
        double temperatureToReturn = currentTemperature;
        currentTemperature *= alpha;
        return temperatureToReturn;
    }
}
