import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KingsTableProgram extends Application
{
   public static Board board = new Board();
   
   public static int boardSize = board.getSize(); //always Odd# x Odd#, usually 11x11 or 13x13
   public static int tileSize = board.getTileSize(); //px size of the grid boxes
   
   private Group tileGroup = new Group();
   private Group pieceGroup = new Group();
   
   public static Color c1 = Color.WHITE; //default colors
   public static Color c2 = Color.GRAY;
   
   public static void game() {	   
	   
   }
   
   public static void main(String[] args)
   {
	   launch(args);
   }

	@Override
	public void start(Stage primaryStage) throws Exception {
		//set up background border pane (Top/Left/Right/Center/Bottom)
		BorderPane border = new BorderPane();
		
		//border.setPadding(new Insets(0,0,0,0)); //top,right,bottom,left
		
		//Screen Size
		int height = 1000;
		int width = 800;
		
		// Background Image
		StackPane backgroundImgContainer = new StackPane();
		ImageView bgimage = new ImageView("stoneBG.jpg");
		bgimage.setFitHeight(height+100);
		bgimage.setFitWidth(width+500);
		backgroundImgContainer.getChildren().addAll(bgimage,border);
		Scene scene = new Scene(backgroundImgContainer,height,width);
		
		//TOP (Menu Button and Title)------------
		HBox hboxTOP = new HBox();
		hboxTOP.setPadding(new Insets(25,0,15,0));//top,right,bottom,left
		//hboxTOP.setStyle("-fx-background-color: #D3D3D3;"); //plain grey background
		Button buttonMenu = new Button("Menu");
		buttonMenu.resize(50,50);
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0f, 0f, 0f));
		Text gameTitle = new Text("King's Table");
		gameTitle.setEffect(ds);
		gameTitle.setCache(true);
		gameTitle.setFill(Color.DARKRED);
		gameTitle.setFont(Font.font("Rockwell", FontWeight.BOLD, 50));
		//gameTitle.setAlignment(Pos.CENTER);
		hboxTOP.getChildren().addAll(buttonMenu, gameTitle);
		hboxTOP.setSpacing(300);
		border.setTop(hboxTOP);
		
		//CENTER (Game Table)------------
		GridPane gridPaneGAME = new GridPane();
		for(int i=0; i<KingsTableProgram.boardSize; i++) {
			for(int j=0; j<KingsTableProgram.boardSize; j++) {
				Rectangle square = new Rectangle();
				square.setWidth(KingsTableProgram.tileSize);
				square.setHeight(KingsTableProgram.tileSize);
				square.setStroke(Color.BLACK);
				StackPane imageContainer = new StackPane();
				if((i==0 && (j==0 || j==(boardSize-1))) || (i==(boardSize-1) && (j==0 || j==(boardSize-1))) || (i==(boardSize/2) && j==(boardSize/2)))
	            {
					ImageView image = new ImageView("darkWood.jpg");
					image.setFitHeight(KingsTableProgram.tileSize);
					image.setFitWidth(KingsTableProgram.tileSize);
					square.setFill(KingsTableProgram.c2);
					imageContainer.getChildren().addAll(square,image);
	            }
	            else{
	            	ImageView image = new ImageView("lightWood.jpg");
	            	image.setFitHeight(KingsTableProgram.tileSize);
					image.setFitWidth(KingsTableProgram.tileSize);
	            	square.setFill(KingsTableProgram.c1);
	            	imageContainer.getChildren().addAll(square,image);
	            	}
				//Pane square = new Pane();
				//pane.setBorder(new Border(new BorderStroke(borderPaint, BoarderStrokeStyle.Solid, Co)))
				GridPane.setRowIndex(imageContainer,i);
				GridPane.setColumnIndex(imageContainer,j);
				gridPaneGAME.getChildren().addAll(imageContainer);
			}
		}
		border.setCenter(gridPaneGAME);
		
		//LEFT (white Game Pieces)------------
		VBox vboxLeft = new VBox();
		vboxLeft.setSpacing(10);
		vboxLeft.setPadding(new Insets(0,100,0,70));
		//vboxLeft.setStyle("-fx-background-color: #D3D3D3;");
		border.setLeft(vboxLeft);
		
		//RIGHT (black Game Pieces)------------
		VBox vboxRight = new VBox();
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(0,70,0,100));
		//vboxRight.setStyle("-fx-background-color: #D3D3D3;");
		border.setRight(vboxRight);
		
		//BOTTOM (High Score, Timer, Player's Score)------------
		HBox hboxBOTTOM = new HBox();
		hboxBOTTOM.setPadding(new Insets(25,10,25,20));//top,right,bottom,left
		//hboxBOTTOM.setStyle("-fx-background-color: #D3D3D3;");
		Text highScore = new Text("High Score");
		Text timer = new Text("Timer");
		Text userScore = new Text("Score");
		Region region1 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);
		highScore.setEffect(ds);
		highScore.setCache(true);
		highScore.setFill(Color.DARKRED);
		highScore.setFont(Font.font("Rockwell", FontWeight.BOLD, 20));
		timer.setEffect(ds);
		timer.setCache(true);
		timer.setFill(Color.DARKRED);
		timer.setFont(Font.font("Rockwell", FontWeight.BOLD, 30));
		userScore.setEffect(ds);
		userScore.setCache(true);
		userScore.setFill(Color.DARKRED);
		userScore.setFont(Font.font("Rockwell", FontWeight.BOLD, 20));
		hboxBOTTOM.getChildren().addAll(highScore, region1, timer, region2, userScore);
		border.setBottom(hboxBOTTOM);
		
		//Show Game ------------
		primaryStage.setScene(scene);
		primaryStage.setTitle("King's Table "+System.getProperty("user.dir"));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}