package pl.edu.agh.student.simulatedannealing.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created by pingwin on 05.01.17.
 */
public class ClassInstantiatorTest {
    private Object object;

    @Before
    public void setUp() {
        object = new TestClassWithSetters();
    }

    @Test
    public void givenClassDisassemblesSettersToSingleWordsByUpercase() {
        ClassInstantiator<Object> classInstantiator = new ClassInstantiator<>(object.getClass().getName());
        Set<String> set = new HashSet<>();
        set.add("First Param");
        set.add("Second Param");

        Set<String> setterParameters = classInstantiator.getSetterParameters();
        assertEquals(set, setterParameters);
    }

    @Test
    public void givenParametersAssemblesTheObject() {
        ClassInstantiator<TestClassWithSetters> classInstantiator = new ClassInstantiator<>(object.getClass().getName());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("First Param", "Cudo");
        parameters.put("Second Param", "3");
        TestClassWithSetters testClass = classInstantiator.createObject(parameters);
        assertEquals("Cudo", testClass.firstParam);
        assertEquals(3, testClass.secondParam, 0.001);
    }

    public static class TestClassWithSetters {
        private String firstParam;
        private double secondParam;

        public TestClassWithSetters() {

        }

        public void setFirstParam(String param) {
            this.firstParam = param;
        }

        public void setSecondParam(double param) {
            this.secondParam = param;
        }
    }
}