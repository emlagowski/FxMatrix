package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public GridPane mainPane;
    public ToggleButton runButton;
    public Button clearButton;
    public TextField threadCounterField;

    public final static int GRID_HEIGHT = 50;
    public final static int GRID_WIDTH = 50;
    public final static int LABEL_HEIGHT = 20;
    public final static int LABEL_WIDTH = 20;
    public final static int SLEEP_TIME = 20;
    public final static int FONT_SIZE = 25;
    public final static String FONT_PATH = "/MATRIX.ttf";
    public final static int START_CHAR = 'a';
    public final static int END_CHAR = 'z';
    public final static float EMPTY_CHANCE = .25f;

    private Generator generator;
    Font font;
    private SimpleBooleanProperty threadsRunningProperty;
    private SimpleStringProperty threadsCounterProperty;
    private Label[][] labelTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generator = new Generator();
        threadsRunningProperty = new SimpleBooleanProperty(false);
        threadsCounterProperty = new SimpleStringProperty("0");
        threadCounterField.textProperty().bindBidirectional(threadsCounterProperty);
        runButton.selectedProperty().bindBidirectional(threadsRunningProperty);

        labelTable = new Label[GRID_WIDTH][GRID_HEIGHT];


        URL fontUrl = getClass().getResource(FONT_PATH);
        font = null;
        if (fontUrl != null)
            font = Font.loadFont(fontUrl.toExternalForm(), FONT_SIZE);

        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                final Label label = new Label(String.valueOf(START_CHAR));
                if (font != null) label.setFont(font);
                setSize(label, LABEL_WIDTH, LABEL_HEIGHT);
                labelTable[i][j] = label;
                addLabelToGrid(labelTable[i][j], i, j);
            }
        }


        threadsRunningProperty.addListener(e -> {
            if (threadsRunningProperty.getValue()) {
                int count = Integer.parseInt(threadsCounterProperty.getValue());
                for (int i = 0; i < count; i++)
                    new Thread(this::threadRun).start();
                clearButton.setDisable(true);
            } else {
                clearButton.setDisable(false);
            }
        });
    }

    private void addLabelToGrid(Label label, int x, int y) {
        //Platform.runLater(() -> mainPane.add(label, x, y));
        mainPane.add(label, x, y);
    }

    private void setSize(Region region, int x, int y) {
        region.setMinSize(x, y);
        region.setMaxSize(x, y);
        region.setPrefSize(x, y);
    }

    private void threadRun() {
        while (threadsRunningProperty.getValue()) {
            setRandomChar();
            try {
                Thread.sleep(generator.randInt(0, SLEEP_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setRandomChar() {
        int x = generator.randInt(0, GRID_WIDTH - 1);
        int y = generator.randInt(0, GRID_HEIGHT - 1);
        char randomChar = (char) ((generator.randInt(0, 100) / 100f) <= EMPTY_CHANCE ? ' ' : generator.randInt(START_CHAR, END_CHAR));
        Platform.runLater(() -> {
            labelTable[x][y].setText(Character.toString(randomChar));
            labelTable[x][y].setTextFill(generator.getRandomColor());
        });
    }

    public void clear() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                final int finX = x;
                final int finY = y;
                Platform.runLater(() -> {
                    labelTable[finX][finY].setText(String.valueOf(START_CHAR));
                    labelTable[finX][finY].setTextFill(generator.getRandomColor());
                });
            }
        }
    }
}
