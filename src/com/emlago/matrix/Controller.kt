package com.emlago.matrix

import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.scene.text.Font

import java.net.URL
import java.util.ResourceBundle

public class Controller : Initializable {
    public var mainPane: GridPane? = null
    public var runButton: ToggleButton? = null
    public var clearButton: Button? = null
    public var threadCounterField: TextField? = null

    private var generator: Generator? = null
    var font: Font? = null
    private var threadsRunningProperty: SimpleBooleanProperty? = null
    private var threadsCounterProperty: SimpleStringProperty? = null
    private var labelTable: Array<Array<Label?>>? = null

    override fun initialize(location: URL, resources: ResourceBundle) {
        generator = Generator()
        threadsRunningProperty = SimpleBooleanProperty(false)
        threadsCounterProperty = SimpleStringProperty("0")
        threadCounterField?.textProperty()?.bindBidirectional(threadsCounterProperty)
        runButton?.selectedProperty()?.bindBidirectional(threadsRunningProperty)

        labelTable = Array<Array<Label?>>(GRID_WIDTH, { arrayOfNulls<Label>(GRID_HEIGHT) })


        val fontUrl = javaClass.getResource(FONT_PATH)
        font = null
        if (fontUrl != null)
            font = Font.loadFont(fontUrl!!.toExternalForm(), FONT_SIZE.toDouble())

        for (i in 0..GRID_HEIGHT - 1) {
            for (j in 0..GRID_WIDTH - 1) {
                val label = Label(START_CHAR.toString())
                if (font != null) label.setFont(font)
                setSize(label, LABEL_WIDTH, LABEL_HEIGHT)
                labelTable!![i][j] = label
                addLabelToGrid(labelTable!![i][j]!!, i, j)
            }
        }


//        threadsRunningProperty!!.addListener()
    }

    private fun addLabelToGrid(label: Label, x: Int, y: Int) {
        //Platform.runLater(() -> mainPane.add(label, x, y));
        mainPane?.add(label, x, y)
    }

    private fun setSize(region: Region, x: Int, y: Int) {
        region.setMinSize(x.toDouble(), y.toDouble())
        region.setMaxSize(x.toDouble(), y.toDouble())
        region.setPrefSize(x.toDouble(), y.toDouble())
    }

    private fun threadRun() {
        while (threadsRunningProperty!!.getValue()) {
            setRandomChar()
            try {
                Thread.sleep(generator!!.randInt(0, SLEEP_TIME).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
    }

    private fun setRandomChar() {
        val x = generator!!.randInt(0, GRID_WIDTH - 1)
        val y = generator!!.randInt(0, GRID_HEIGHT - 1)
        val randomChar = (if ((generator!!.randInt(0, 100) / 100f) <= EMPTY_CHANCE) ' ' else generator!!.randInt(START_CHAR, END_CHAR))
        Platform.runLater({})
    }

    public fun clear() {
        for (x in 0..GRID_WIDTH - 1) {
            for (y in 0..GRID_HEIGHT - 1) {
                val finX = x
                val finY = y
                Platform.runLater({})
            }
        }
    }

    companion object {
        public val GRID_HEIGHT: Int = 50
        public val GRID_WIDTH: Int = 50
        public val LABEL_HEIGHT: Int = 20
        public val LABEL_WIDTH: Int = 20
        public val SLEEP_TIME: Int = 20
        public val FONT_SIZE: Int = 25
        public val FONT_PATH: String = "/MATRIX.ttf"
        public val START_CHAR: Int = 'a'.toInt()
        public val END_CHAR: Int = 'z'.toInt()
        public val EMPTY_CHANCE: Float = .25f
    }
}
