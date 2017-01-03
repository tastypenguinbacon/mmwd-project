package pl.edu.agh.student.simulatedannealing.temperature;

/**
 * Created by Ja on 2017-01-02.
 */
public class LinearlyDecreasingTemperatureTest extends TemperatureTestBase<LinearlyDecreasingTemperature>{
    @Override
    protected LinearlyDecreasingTemperature createInstance() {
        return new LinearlyDecreasingTemperature(10000, 0.1);
    }
}