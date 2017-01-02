package pl.edu.agh.student.simulatedannealing.temperature;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ja on 2017-01-02.
 */
public class LinearlyDecreasingTemperatureTest {
    @Test
    public void temperatureIsPositive() {
        Temperature temperature = new LinearlyDecreasingTemperature(100, 0.05);
        double T;
        for (int i = 0; i < 10000; i++) {
            T = temperature.getNextTemperature();
            assertTrue(T > 0);
        }
    }

    @Test
    public void temperatureIsNonstrictlyDecreasing() {
        Temperature temperature = new LinearlyDecreasingTemperature(100, 0.01);
        double previousT = temperature.getNextTemperature();
        double T = temperature.getNextTemperature();
        for (int i = 0; i < 100000; i++) {
            assertTrue(T <= previousT);
            previousT = T;
            T = temperature.getNextTemperature();
        }
    }
}