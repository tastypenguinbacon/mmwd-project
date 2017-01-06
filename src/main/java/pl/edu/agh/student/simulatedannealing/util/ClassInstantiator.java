package pl.edu.agh.student.simulatedannealing.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pingwin on 05.01.17.
 */
public class ClassInstantiator<T> {
    private Class clas;

    public ClassInstantiator(String className) {
        try {
            clas = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Error("Class has invalid name: " + className);
        }
    }

    public Set<String> getSetterParameters() {
        return Arrays.stream(clas.getMethods()).filter(method -> method.getName().startsWith("set"))
                .map(Method::getName).map(this::disassembleSetterName)
                .collect(Collectors.toSet());
    }

    private String disassembleSetterName(String name) {
        String parameterName = name.substring(3);
        List<String> separatedStrings = new LinkedList<>();
        int previousOccuranceOfUpperCase = 0;
        for (int i = 1; i < parameterName.length(); i++) {
            if (Character.isUpperCase(parameterName.charAt(i))) {
                String singleWord = parameterName.substring(previousOccuranceOfUpperCase, i);
                previousOccuranceOfUpperCase = i;
                separatedStrings.add(singleWord);
            }
        }
        separatedStrings.add(parameterName.substring(previousOccuranceOfUpperCase));
        StringBuilder builder = new StringBuilder();
        separatedStrings.forEach(string -> builder.append(string).append(" "));
        return builder.toString().trim();
    }

    @SuppressWarnings("unchecked")
    public T createObject(Map<String, String> parameters) {
        try {
            Object object = clas.getConstructor().newInstance();
            parameters.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    getMethod(key).invoke(object, Double.valueOf(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method getMethod(String key) {
        String[] split = key.split(" ");
        StringBuilder builder = new StringBuilder("set");
        for (int i = 0; i < split.length; i++) {
            builder.append(split[i]);
        }
        String methodName = builder.toString();
        List<Method> collect = Arrays.stream(clas.getMethods())
                .filter(method -> method.getName().matches(methodName))
                .collect(Collectors.toList());
        return collect.get(0);
    }
}
