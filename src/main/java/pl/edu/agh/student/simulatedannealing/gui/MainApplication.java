package pl.edu.agh.student.simulatedannealing.gui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.agh.student.simulatedannealing.ObjectiveFunction.ObjectiveFunction;
import pl.edu.agh.student.simulatedannealing.model.Pizza;
import pl.edu.agh.student.simulatedannealing.model.PizzaDeliverer;
import pl.edu.agh.student.simulatedannealing.mutator.Mutator;
import pl.edu.agh.student.simulatedannealing.solver.ComputationState;
import pl.edu.agh.student.simulatedannealing.solver.SimulatedAnnealingSolver;
import pl.edu.agh.student.simulatedannealing.statistics.StatisticPoint;
import pl.edu.agh.student.simulatedannealing.statistics.Statistics;
import pl.edu.agh.student.simulatedannealing.temperature.Temperature;
import pl.edu.agh.student.simulatedannealing.util.ClassInstantiator;
import pl.edu.agh.student.simulatedannealing.util.JsonLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

import static java.lang.Math.exp;
import static java.lang.Math.sin;

/**
 * Created by pingwin on 10.12.16.
 */
public class MainApplication extends Application {
    private Stage primaryStage;

    private StatisticsPlot statisticsPlot;
    private OutputOfAlgorithm algorithmOutput;
    private VBox controlPane;
    private TestCases testCases;

    private Map<String, String> temperatureClassMappings;
    private Map<String, String> mutatorClassMappings;
    private Mutator<ComputationState> mutator;
    private Temperature temperature;
    private Collection<PizzaDeliverer> pizzaDeliverers;
    private Collection<Pizza> pizzasToDeliver;

    private Statistics computationStatistics;
    private ComputationState finalState;

    public static void main(String... args) {
        launch(args);
    }

    public void redrawAlgorithmOutput() {
        loadPizzasAndDeliverers();
        algorithmOutput.setPizzaDeliverers(new LinkedList<>(pizzaDeliverers));
        algorithmOutput.setPizzasToDeliver(new LinkedList<>(pizzasToDeliver));
        algorithmOutput.redraw();
    }

    @Override
    public void start(final Stage stage) {
        primaryStage = stage;
        initialize();
        HBox verticalLayout = new HBox(20);
        verticalLayout.getChildren().add(controlPane);
        verticalLayout.getChildren().add(algorithmOutput);
        verticalLayout.getChildren().add(statisticsPlot);
        stage.setScene(new Scene(verticalLayout));
        stage.setResizable(false);
        stage.show();
    }

    private void initialize() {
        loadProperties();
        statisticsPlot = getStatisticsOfObjectiveFunction();
        algorithmOutput = getAlgorithmOutput();
        controlPane = getControlPane();
    }

    private void loadProperties() {
        JsonLoader jsonLoader = new JsonLoader();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("temperatures.json");
        temperatureClassMappings = jsonLoader.getProperties(inputStream);
        inputStream = getClass().getClassLoader().getResourceAsStream("mutators.json");
        mutatorClassMappings = jsonLoader.getProperties(inputStream);
    }

    private VBox getControlPane() {
        VBox controlPane = new VBox(50);
        controlPane.setPadding(new Insets(50, 20, 50, 20));
        controlPane.getChildren().add(getSampleTestCases());
        controlPane.getChildren().add(getTemperatures());
        controlPane.getChildren().add(getMutators());
        controlPane.getChildren().add(getStartButton());
        return controlPane;
    }

    private TestCases getSampleTestCases() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resource_mappings.json");
        testCases = new TestCases(inputStream, this);
        return testCases;
    }

    private Node getTemperatures() {
        VBox temperaturePanel = new VBox(10);
        temperaturePanel.getChildren().add(new Label("Temperature:"));
        ObservableList<String> temperatures = FXCollections.observableArrayList(temperatureClassMappings.keySet());
        ComboBox<String> comboBox = new ComboBox<>(temperatures);

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (temperatureClassMappings.containsKey(newValue)) {
                ClassInstantiator<Temperature> classInstantiator = new ClassInstantiator<>(temperatureClassMappings.get(newValue));
                Set<String> setterParameters = classInstantiator.getSetterParameters();
                InputBox inputBox = new InputBox(new LinkedList<>(setterParameters));
                if (setterParameters.size() != 0) {
                    inputBox.showAndWait();
                }
                Map<String, String> inputs = inputBox.getInputs();
                this.temperature = classInstantiator.createObject(inputs);
            }
        });
        temperaturePanel.getChildren().add(comboBox);
        return temperaturePanel;
    }

    private Node getMutators() {
        VBox mutatorPanel = new VBox(10);
        mutatorPanel.getChildren().add(new Label("Mutator:"));
        ObservableList<String> temperatures = FXCollections.observableArrayList(mutatorClassMappings.keySet());
        ComboBox<String> comboBox = new ComboBox<>(temperatures);
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mutatorClassMappings.containsKey(newValue)) {
                ClassInstantiator<Mutator<ComputationState>> classInstantiator = new ClassInstantiator<>(mutatorClassMappings.get(newValue));
                Set<String> setterParameters = classInstantiator.getSetterParameters();
                InputBox inputBox = new InputBox(new LinkedList<>(setterParameters));
                if (setterParameters.size() != 0) {
                    inputBox.showAndWait();
                }
                Map<String, String> inputs = inputBox.getInputs();
                this.mutator = classInstantiator.createObject(inputs);
            }
        });
        mutatorPanel.getChildren().add(comboBox);
        return mutatorPanel;
    }

    private Button getStartButton() {
        MenuItem compute = new MenuItem();
        compute.setText("Compute");

        compute.setOnAction(event -> {
            pizzaDeliverers = algorithmOutput.getPizzaDeliverers();
            pizzasToDeliver = algorithmOutput.getPizzasToDeliver();
            if (parametersAreNotNull()) {
                SimulatedAnnealingSolver<ComputationState> solver;
                mutator.addPizzasToDistribute(pizzasToDeliver);
                temperature.reset();
                solver = new SimulatedAnnealingSolver<>(mutator, temperature, new ObjectiveFunction());
                ComputationState startingPoint = new ComputationState(pizzaDeliverers);
                InputBox inputBox = new InputBox(Arrays.asList("Iteration Count"));
                inputBox.showAndWait();
                Map<String, String> inputs = inputBox.getInputs();
                int iterationCount = Integer.valueOf(inputs.get("Iteration Count"));
                finalState = solver.solve(startingPoint, iterationCount);
                computationStatistics = solver.getStatistics();
                updateCharts();
                printStatisticsToFile();
            } else {
                new ErrorDialog().show();
            }
        });
        return compute;
    }

    private void printStatisticsToFile(){
        //path
        String outDir = "./output/test/";
        String timestamp = new SimpleDateFormat("YYYY-MM-dd'T'HHmmss").format(new Date());
        String baseName = "statistics";
        String extension = ".csv";
        String fileName = baseName + "_" + timestamp + extension;

        Path path = Paths.get(outDir + fileName);
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        Charset encoding = StandardCharsets.UTF_8;
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(
                    Files.newBufferedWriter(
                            path,
                            encoding,
                            StandardOpenOption.CREATE_NEW
                    )
            );

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }


        //header
        printWriter.println("mutator: " + mutator);
        printWriter.println("temperature: " + temperature);
        printWriter.println("iteration,value");
        //statistics
        List<StatisticPoint> statistics = computationStatistics.getStatistics();
        for ( StatisticPoint dataPoint : statistics) {
            printWriter.println(dataPoint.getIteration() + "," + dataPoint.getValue());
        }
        printWriter.flush();
        printWriter.close();
    }

    private void updateCharts() {
        statisticsPlot.update(computationStatistics);
        algorithmOutput.update(finalState);
        primaryStage.show();
    }

    private void loadPizzasAndDeliverers() {
        pizzaDeliverers = testCases.getDeliverers();
        pizzasToDeliver = testCases.getPizzas();
        if (pizzaDeliverers == null || pizzasToDeliver == null) {
            pizzaDeliverers = algorithmOutput.getPizzaDeliverers();
            pizzasToDeliver = algorithmOutput.getPizzasToDeliver();
        }
    }

    private boolean parametersAreNotNull() {
        return !(mutator == null || temperature == null || pizzaDeliverers == null);
    }

    private StatisticsPlot getStatisticsOfObjectiveFunction() {
        Function<Integer, Double> function;
        function = (Integer x) -> exp(-(double) x / 200) * sin((double) x / 20);
        Statistics statistics = new Statistics();
        for (int i = 0; i < 1000; i++) {
            statistics.add(i, function.apply(i));
        }
        StatisticsPlot statisticsPlot = new StatisticsPlot(statistics);
        statisticsPlot.setTitle("Objective function");
        return statisticsPlot;
    }

    private OutputOfAlgorithm getAlgorithmOutput() {
        return new OutputOfAlgorithm(primaryStage);
    }
}
