
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class KingsTableProgram extends Application {
	public static Board board = new Board();
	public static Circle selected;
	public static int boardSize = board.getSize(); // always Odd# x Odd#, usually 11x11 or 13x13
	public static int tileSize = board.getTileSize(); // px size of the grid boxes

	private Group tileGroup = new Group();
	private Group pieceGroup = new Group();

	public static Color regSquareColor = Color.WHITE; // default colors
	public static Color kingSquareColor = Color.GRAY;
	public static Color textColor = Color.DARKGOLDENROD;
	public static String textFont = "Rockwell";

	public static void game() {

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		// set up background border pane (Top/Left/Right/Center/Bottom)
		BorderPane border = new BorderPane();
		// border.setPadding(new Insets(0,0,0,0)); //top,right,bottom,left

		// Screen Size
		int width = 1000;
		int height = 700;

		// Background Image--------------
		StackPane backgroundImgContainer = new StackPane();
		ImageView bgimage = new ImageView("stoneBG.jpg");
		bgimage.setFitHeight(height + 100);
		bgimage.setFitWidth(width + 500);
		backgroundImgContainer.getChildren().addAll(bgimage, border);
		Scene scene = new Scene(backgroundImgContainer, width, height);

		// TOP (Menu Button and Title)------------
		HBox hboxTOP = new HBox();
		hboxTOP.setAlignment(Pos.TOP_LEFT);
		hboxTOP.setPadding(new Insets(15, 0, 15, 0));// top,right,bottom,left
		// hboxTOP.setStyle("-fx-background-color: #D3D3D3;"); //plain grey background
		Button buttonMenu = new Button("Menu"); // Menu Button
		buttonMenu.resize(50, 50);
		Text gameTitle = new Text("King's Table");
		gameTitle.setFill(KingsTableProgram.textColor);
		gameTitle.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK)); // Radius, offsetX, offsetY, color
		gameTitle.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 50));
		hboxTOP.getChildren().addAll(buttonMenu, gameTitle);
		hboxTOP.setSpacing(300);
		border.setTop(hboxTOP);

		// CENTER (Game Table)------------
		GridPane gridPaneGAME = new GridPane();
		gridPaneGAME.setAlignment(Pos.CENTER);
		for (int i = 0; i < KingsTableProgram.boardSize; i++) {
			for (int j = 0; j < KingsTableProgram.boardSize; j++) {
				String fileName = "lightWood.jpg";
				Rectangle square = new Rectangle();
				square.setWidth(KingsTableProgram.tileSize);
				square.setHeight(KingsTableProgram.tileSize);
				square.setStroke(Color.BLACK);
				square.setFill(Color.TRANSPARENT);
				square.setOnMouseEntered(event -> {
					square.setEffect(new InnerShadow(+50d, 0d, 0d, Color.GOLD));
				});
				square.setOnMouseExited(event -> {
					square.setEffect(new InnerShadow(0d, 0d, 0d, Color.TRANSPARENT));
				});

				// Gabe - Give every box an id
				square.setId(i + "," + j);
				square.setOnMouseClicked(event -> {
					System.out.println("Clicled a tile" + square);
					if(selected!=null) {
						String[] coordinates = selected.getId().split(",");
						System.out.println("coordinates for selected " +coordinates[0]+" "+coordinates[1]);
						gridPaneGAME.getChildren().remove(selected);
						GridPane.setRowIndex(selected,Integer.parseInt(coordinates[0]) );
						GridPane.setColumnIndex(selected, Integer.parseInt(coordinates[1]));
						GridPane.setHalignment(selected, HPos.CENTER);
//						gridPaneGAME.getChildren().add(selected);
						selected = null;						
					}
					//TODO update overlay just clicked, remove previous
					
//							Rectangle square1 = new Rectangle();
//							square.setWidth(KingsTableProgram.tileSize);
//							square.setHeight(KingsTableProgram.tileSize);
//							square.setStroke(Color.BLACK);
//							ImageView image = new ImageView("file:darkWood.jpg");
//							imageContainer.set
//							imageContainer.getChi.getChildren().setAll(square1,image);

				});
				StackPane imageContainer = new StackPane();
				if ((i == 0 && (j == 0 || j == (boardSize - 1)))
						|| (i == (boardSize - 1) && (j == 0 || j == (boardSize - 1)))
						|| (i == (boardSize / 2) && j == (boardSize / 2))) {
					fileName = "darkWood.jpg";
				}
				ImageView image = new ImageView(fileName);
				image.setFitHeight(KingsTableProgram.tileSize);
				image.setFitWidth(KingsTableProgram.tileSize);
				imageContainer.getChildren().addAll(image, square);
				if (i == KingsTableProgram.board.getSelectedTileX()
						&& j == KingsTableProgram.board.getSelectedTileY()) {
					Rectangle highlight = new Rectangle();
					highlight.setWidth(KingsTableProgram.tileSize);
					highlight.setHeight(KingsTableProgram.tileSize);
					highlight.setFill(Color.rgb(250, 255, 0, .4));
					imageContainer.getChildren().addAll(highlight);
				}
				gridPaneGAME.setEffect(new DropShadow(+30d, 0d, 0d, Color.BLACK));
				GridPane.setRowIndex(imageContainer, i);
				GridPane.setColumnIndex(imageContainer, j);
				gridPaneGAME.getChildren().addAll(imageContainer);
			}
		}
		border.setCenter(gridPaneGAME);

		// LEFT (white Game Pieces graveyard)------------
		VBox vboxLeft = new VBox();
		vboxLeft.setSpacing(10);
		vboxLeft.setPadding(new Insets(0, 100, 0, 70));
		// vboxLeft.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
		border.setLeft(vboxLeft);

		// RIGHT (black Game Pieces graveyard)------------
		VBox vboxRight = new VBox();
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(0, 70, 0, 100));
		// vboxRight.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
		border.setRight(vboxRight);

		// BOTTOM (High Score, Timer, Player's Score)------------
		HBox hboxBOTTOM = new HBox();
		hboxBOTTOM.setAlignment(Pos.BOTTOM_CENTER);
		hboxBOTTOM.setPadding(new Insets(25, 10, 25, 20));// top,right,bottom,left
		// hboxBOTTOM.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
		Text highScore = new Text("High Score");
		Text timer = new Text("Timer");
		Text userScore = new Text("Score");
		Region region1 = new Region(); // spacer
		HBox.setHgrow(region1, Priority.ALWAYS);
		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);
		highScore.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK)); // Radius, offsetX, offsetY, color;
		highScore.setFill(KingsTableProgram.textColor);
		highScore.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
		timer.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
		timer.setFill(KingsTableProgram.textColor);
		timer.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 30));
		userScore.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
		userScore.setFill(KingsTableProgram.textColor);
		userScore.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
		hboxBOTTOM.getChildren().addAll(highScore, region1, timer, region2, userScore);
		border.setBottom(hboxBOTTOM);

		// Show Game ------------
		primaryStage.setScene(scene);
		primaryStage.setTitle("King's Table");
		primaryStage.setResizable(false);
		primaryStage.show();

		// Piece GUI experiment
		// Set Up initial table
		// Attackers TOP | LEFT | RIGHT | BOTTOM
		int[] xCoor = new int[] { 3, 4, 5, 6, 7, 5, 0, 0, 0, 0, 0, 1, 10, 10, 10, 10, 10, 9, 3, 4, 5, 6, 7, 5,
				// Defenders
				5, 4, 5, 6, 3, 4, 6, 7, 4, 5, 6, 5 };
		int[] yCoor = new int[] { 0, 0, 0, 0, 0, 1, 3, 4, 5, 6, 7, 5, 3, 4, 5, 6, 7, 5, 10, 10, 10, 10, 10, 9,
				// Defenders
				3, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 7 };
		Image dpImage = new Image("defenderPiece.jpg");
		Image apImage = new Image("attackerPiece.jpg");
		for (int i = 0; i < xCoor.length; i++) {

			Circle piece = new Circle(KingsTableProgram.tileSize / 3);

			if (i > 23) {
				piece.setFill(new ImagePattern(dpImage));
			} else {
				piece.setFill(new ImagePattern(apImage));
			}
			GridPane.setRowIndex(piece, yCoor[i]);
			GridPane.setColumnIndex(piece, xCoor[i]);
			GridPane.setHalignment(piece, HPos.CENTER);
			piece.setId(yCoor[i]+","+xCoor[i]);
			piece.setEffect(new DropShadow(+10d, 0d, 0d, Color.BLACK)); // Radius, offsetX, offsetY, color
			piece.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will
												// highlight
				piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
			});
			// Click this piece, save to a global last clicked value
			// Remove last clicked image location replace on new clicked location
			piece.setOnMouseClicked(event ->{
				selected = piece;
				System.out.println("clicked");
			});
			piece.setOnMouseExited(event -> {
				piece.setEffect(new InnerShadow(0d, 0d, 0d, Color.TRANSPARENT));
			});
			gridPaneGAME.getChildren().addAll(piece);
		}
		// King
		Circle king = new Circle(KingsTableProgram.tileSize / 2);
		king.setFill(new ImagePattern(dpImage));
		GridPane.setRowIndex(king, 5);
		GridPane.setColumnIndex(king, 5);
		GridPane.setHalignment(king, HPos.CENTER);
		king.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will highlight
			king.setEffect(new InnerShadow(+50d, 0d, 0d, Color.GOLD));
		});
		king.setOnMouseExited(event -> {
			king.setEffect(new InnerShadow(0d, 0d, 0d, Color.TRANSPARENT));
		});
		king.setEffect(new DropShadow(+10d, 0d, 0d, Color.BLACK)); // Radius, offsetX, offsetY, color
		gridPaneGAME.getChildren().addAll(king);
	}

}
