package menu;

import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.control.*; 
import javafx.scene.layout.*; 
import javafx.stage.Stage; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.canvas.*; 
import javafx.scene.web.*; 
import javafx.scene.layout.*; 
import javafx.scene.image.*; 
import java.io.*; 
import javafx.geometry.*; 
import javafx.scene.Group; 
  
public class Menu extends Application { 
  
    // launch the application 
    public void start(Stage stage) 
    { 
  
        try { 
  
            // set title for the stage 
            stage.setTitle("Viking Table"); 
 
  
            // create a button 
            Button button = new Button("Play Game"); 
  
            // add the label, text field and button 
            HBox hbox = new HBox(button); 
  
            // set spacing 
            hbox.setSpacing(10); 
  
            // set alignment for the HBox 
            hbox.setAlignment(Pos.CENTER); 
  
            // create a scene 
            Scene scene = new Scene(hbox, 800, 800); 
  
            // create a input stream 
            FileInputStream input = new FileInputStream("viking_burn.jpeg"); 
  
            // create a image 
            Image image = new Image(input); 
  
            // create a background image 
            BackgroundImage backgroundimage = new BackgroundImage(image,  
                                             BackgroundRepeat.NO_REPEAT,  
                                             BackgroundRepeat.NO_REPEAT,  
                                             BackgroundPosition.DEFAULT,  
                                                BackgroundSize.DEFAULT); 
  
            // create Background 
            Background background = new Background(backgroundimage); 
  
            // set background 
            hbox.setBackground(background); 
  
            // set the scene 
            stage.setScene(scene); 
  
            stage.show(); 
        } 
  
        catch (Exception e) { 
  
            System.out.println(e.getMessage()); 
        } 
    } 
  
    // Main Method 
    public static void main(String args[]) 
    { 
  
        // launch the application 
        launch(args); 
    } 
} 
