package pl.edu.agh.student.simulatedannealing.temperature;

/**
 * Created by Ja on 2017-01-03.
 */
public class ExponentiallyDecreasingTemperatureTest extends TemperatureTestBase<ExponentiallyDecreasingTemperature>{
    @Override
    protected ExponentiallyDecreasingTemperature createInstance() {
        return new ExponentiallyDecreasingTemperature(10000, 0.95);
    }
}
