package pl.edu.agh.student.simulatedannealing.temperature;

/**
 * Created by Szymek on 2017-01-02.
 */
public class LinearlyDecreasingTemperature implements Temperature{
    private final double decrement;
    private double currentTemperature;

    public LinearlyDecreasingTemperature(double startingTemperature, double decrement) {
        this.currentTemperature = startingTemperature;
        this.decrement = decrement;
    }

    @Override
    public double getNextTemperature() {
        double temperatureToReturn = currentTemperature;
        if (currentTemperature > decrement)
            currentTemperature -= decrement;
        return temperatureToReturn;
    }
}
