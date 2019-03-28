/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpscreen;

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
import static javafx.application.Application.launch;
import javafx.geometry.*; 
import javafx.scene.Group; 
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

  
public class HelpScreen extends Application { 
  
    // launch the application 
    public void start(Stage stage) 
    { 
  
        try { 
  
            // set title for the stage 
            stage.setTitle("Help Screen"); 
            
            HBox hboxTOP = new HBox();
            hboxTOP.setPadding(new Insets(25, 0, 15, 0));
            DropShadow ds = new DropShadow();
            ds.setOffsetY(3.0f);
            Text menuTitle = new Text("Help");
            menuTitle.setEffect(ds);
            menuTitle.setCache(true);
            menuTitle.setFill(Color.WHITESMOKE);
            menuTitle.setFont(Font.font("Rockwell", FontWeight.EXTRA_BOLD, 50));
            
            
            // create a button 
            Button gameButton = new Button("Go Back to Menu"); 
  
            // add the label, text field and button 
            HBox hbox = new HBox(gameButton); 
  
            // set spacing 
            hbox.setSpacing(10); 
  
            // set alignment for the HBox 
            hbox.setAlignment(Pos.CENTER); 
  
            // create a scene 
            Scene scene = new Scene(hbox, 800, 800); 
  
            // create a input stream 
            FileInputStream input = new FileInputStream("helpscreenbackground800x800.jpg"); 
  
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
