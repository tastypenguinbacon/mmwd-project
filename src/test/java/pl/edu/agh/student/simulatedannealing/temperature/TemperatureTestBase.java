package pl.edu.agh.student.simulatedannealing.temperature;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ja on 2017-01-03.
 */
public abstract class TemperatureTestBase<T extends Temperature> {
    private T instance;

    protected abstract T createInstance();

    @Before
    public void setUp() {
        instance = createInstance();
    }

    @Test
    public void temperatureIsPositive() {
        double T;
        for (int i = 0; i < 100000; i++) {
            T = instance.getNextTemperature();
            assertTrue(T > 0);
        }
    }

    @Test
    public void temperatureIsNonstrictlyDecreasing() {
        double previousT = instance.getNextTemperature();
        double T = instance.getNextTemperature();
        for (int i = 0; i < 100000; i++) {
            assertTrue(T <= previousT);
            previousT = T;
            T = instance.getNextTemperature();
        }
    }
}
