////////////////////////////////////////////////////////////////////////////////
// RomBrowser.java
// ============
// Main method and Start method for RomBrowser
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-03-19
// UPDATED: 2018-03-30
////////////////////////////////////////////////////////////////////////////////

package rombrowser;

import java.awt.Toolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RomBrowser extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        //creates JavaFX using SceneBuilder Model
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        //gets the CSS file to style the frame
        String css = this.getClass().getResource("/CSS/JavaCSS.css").toExternalForm();

        //creates scene with the given parent
        Scene scene = new Scene(root);
        //adds CSS to the scene
        scene.getStylesheets().add(css);

        //set default browser size = full screen
        stage.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        stage.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());

        //sets minimum size of window
        stage.setMinWidth(500);
        stage.setMinHeight(300);

        //adds scene to the stage
        stage.setScene(scene);
        stage.getIcons().add(new Image("/img/icon.jpg"));

        stage.setTitle("RuffBrowser");
        stage.show();
    }

    //main method to launch the JavaFX
    public static void main(String[] args) {
        launch(args);
    }

}
