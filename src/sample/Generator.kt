package sample

import javafx.scene.paint.Color
import javafx.scene.paint.Paint

import java.util.Random

public class Generator {
    private val generator: Random

    init {
        generator = Random()
    }

    public fun getFloat(): Float {
        return generator.nextFloat()
    }

    public fun getRandomColor(): Paint {
        return Color(getFloat().toDouble(), getFloat().toDouble(), getFloat().toDouble(), 1.0)
    }

    public fun randInt(min: Int, max: Int): Int {
        return generator.nextInt((max - min) + 1) + min
    }
}
