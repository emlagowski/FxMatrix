package sample

import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.WindowEvent

import java.net.URL

public class Main : Application() {
    public val FXML_PATH: String = "matrix.fxml"
    public val TITLE: String = "Matrix generator"

    override fun start(primaryStage: Stage) {
        val fxmlUrl = javaClass.getResource(FXML_PATH)
        if (fxmlUrl != null) {
            val root = FXMLLoader.load<Parent>(fxmlUrl)
            primaryStage.setTitle(TITLE)
            primaryStage.setScene(Scene(root))
            primaryStage.show()
//            primaryStage.setOnCloseRequest()
        } else {
            println("FXML not found.")
        }
    }

    companion object {

        public fun main(args: Array<String>) {
            Application.launch(*args)
        }
    }
}

fun main(args: Array<String>) = Main.main(args)
