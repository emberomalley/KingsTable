/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;


public class KingsTableTest extends Application {
	public static Board board = new Board();
	public static Node selected; 
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

Scene menu, game, help;       

	@Override
	public void start(Stage primaryStage) throws Exception {
                //////// Menu Scene
		// set up background border pane (Top/Left/Right/Center/Bottom)
		BorderPane menuBorder = new BorderPane();
                menuBorder.setPadding(new Insets(15, 520, 100, 150));
		// Screen Size
		int menuWidth = 1000;
		int menuHeight = 700;

                StackPane menuBackgroundImgContainer = new StackPane();
                ImageView menuBgImage = new ImageView("vikingFire.jpg");
                menuBgImage.setFitHeight(menuHeight + 10);
                menuBgImage.setFitWidth(menuWidth + 10);
                menuBackgroundImgContainer.getChildren().addAll(menuBgImage,menuBorder);
                menu = new Scene(menuBackgroundImgContainer, menuWidth,menuHeight);
                
                HBox menuHboxCENTER = new HBox();
                menuHboxCENTER.setAlignment(Pos.CENTER);
                
                Button buttonPlayGame = new Button("Play Game"); // play game button
                buttonPlayGame.resize(menuWidth*.05, menuHeight*.1);
                buttonPlayGame.setStyle("-fx-background-color: #B8860B");
                buttonPlayGame.setOnMouseEntered(event -> { 
			// highlight
			buttonPlayGame.setStyle("-fx-background-color: #FFD700");
		});
		buttonPlayGame.setOnMouseExited(event -> {
			buttonPlayGame.setStyle("-fx-background-color: #B8860B");
		});
                Text menuTitle = new Text("King's Table");
                menuTitle.setFont(new Font(textFont, 80));
                menuTitle.setFill(Color.ORANGERED);
                menuTitle.setStroke(Color.RED);
                Label label1 = new Label("King's Table");
                buttonPlayGame.setOnAction(e -> primaryStage.setScene(game));//click button go to Game screen
                VBox layout1 = new VBox(20);
                layout1.getChildren().addAll(label1, buttonPlayGame, menuTitle);
                menuBorder.setBottom(layout1);


//////////
                // Game Scene
                // set up background border pane (Top/Left/Right/Center/Bottom)
		BorderPane gameBorder = new BorderPane();
		// Screen Size
		int gameWidth = 1000;
		int gameHeight = 700;
		// Background Image--------------
		StackPane gameBackgroundImgContainer = new StackPane();
                ImageView gameBgImage = new ImageView("stoneBG.jpg");
                gameBgImage.setFitHeight(gameHeight + 100);
  		gameBgImage.setFitWidth(gameWidth + 500);
		gameBackgroundImgContainer.getChildren().addAll(gameBgImage, gameBorder);
		Scene gameScene = new Scene(gameBackgroundImgContainer, gameWidth, gameHeight);


		// TOP (Menu Button and Title of Game Scene)------------
		HBox hboxTOP = new HBox();
		hboxTOP.setAlignment(Pos.TOP_LEFT);
		hboxTOP.setPadding(new Insets(15, 0, 15, 5));// top,right,bottom,left
		Button buttonMenu = new Button("Menu"); // Menu Button
		buttonMenu.resize(50, 50);
		buttonMenu.setStyle("-fx-background-color: #B8860B");
		buttonMenu.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will
			// highlight
			buttonMenu.setStyle("-fx-background-color: #FFD700");
		});
		buttonMenu.setOnMouseExited(event -> {
			buttonMenu.setStyle("-fx-background-color: #B8860B");
		});
                

		Text gameTitle = new Text("King's Table");
                Label label2 = new Label("King's Table");
                buttonMenu.setOnAction(e -> primaryStage.setScene(menu));//click button and go back to menu screen
                VBox layout2 = new VBox(20);
                layout2.getChildren().addAll(label2, buttonMenu);
                ////
		gameTitle.setFill(KingsTableTest.textColor);
		gameTitle.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK)); // Radius, offsetX, offsetY, color
		gameTitle.setFont(Font.font(KingsTableTest.textFont, FontWeight.BOLD, 50));
		hboxTOP.getChildren().addAll(buttonMenu, gameTitle);
////////                hboxTOP.getChildren().addAll(buttonMenu, buttonHelp, gameTitle);
		hboxTOP.setSpacing(295);
		gameBorder.setTop(hboxTOP);

		// CENTER (Game Table)------------
		GridPane gridPaneGAME = new GridPane();
		gridPaneGAME.setAlignment(Pos.CENTER);
		for (int i = 0; i < KingsTableTest.boardSize; i++) {
			for (int j = 0; j < KingsTableTest.boardSize; j++) {
				String fileName = "lightWood.jpg";
				Rectangle square = new Rectangle();
				square.setWidth(KingsTableTest.tileSize);
				square.setHeight(KingsTableTest.tileSize);
				square.setStroke(Color.BLACK);
				square.setFill(Color.TRANSPARENT);
				square.setOnMouseEntered(event -> {
					square.setEffect(new InnerShadow(+50d, 0d, 0d, Color.GOLD));
				});
				square.setOnMouseExited(event -> {
					square.setEffect(new InnerShadow(10d, 0d, 0d, Color.BLACK));
				});

				// Gabe - Give every box an id
				square.setId(i + "," + j);
				square.setOnMouseClicked((MouseEvent event) -> {
					System.out.println("Clicled a tile" + square);
					if(selected!=null) {
						String[] coordinates = selected.getId().split(",");
						System.out.println("selected = " + selected);
						System.out.println("coordinates for selected " +coordinates[0]+" "+coordinates[1]);
						gridPaneGAME.getChildren().remove(selected);
						GridPane.setRowIndex(selected,Integer.parseInt(coordinates[0]) );
						GridPane.setColumnIndex(selected, Integer.parseInt(coordinates[1]));
						GridPane.setHalignment(selected, HPos.CENTER);
						gridPaneGAME.getChildren().add(selected);
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
				}
				//ImageView image = new ImageView(fileName);
				//image.setFitHeight(KingsTableProgram.tileSize);
				//image.setFitWidth(KingsTableProgram.tileSize);
				imageContainer.getChildren().addAll(square);
				if (i == KingsTableTest.board.getSelectedTileX()
						&& j == KingsTableTest.board.getSelectedTileY()) {
					Rectangle highlight = new Rectangle();
					highlight.setWidth(KingsTableTest.tileSize);
					highlight.setHeight(KingsTableTest.tileSize);
					highlight.setFill(Color.rgb(250, 255, 0, .4));
					imageContainer.getChildren().addAll(highlight);
				}
				gridPaneGAME.setEffect(new DropShadow(+30d, 0d, 0d, Color.BLACK));
				GridPane.setRowIndex(imageContainer, i);
				GridPane.setColumnIndex(imageContainer, j);
				gridPaneGAME.getChildren().addAll(imageContainer);
			}
		}
		gameBorder.setCenter(gridPaneGAME);

		// LEFT (white Game Pieces graveyard)------------
		VBox vboxLeft = new VBox();
		vboxLeft.setSpacing(10);
		vboxLeft.setPadding(new Insets(0, 100, 0, 70));
		// vboxLeft.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
		gameBorder.setLeft(vboxLeft);

		// RIGHT (black Game Pieces graveyard)------------
		VBox vboxRight = new VBox();
		vboxRight.setSpacing(10);
		vboxRight.setPadding(new Insets(0, 70, 0, 100));
		// vboxRight.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
		gameBorder.setRight(vboxRight);

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
		highScore.setFill(KingsTableTest.textColor);
		highScore.setFont(Font.font(KingsTableTest.textFont, FontWeight.BOLD, 20));
		timer.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
		timer.setFill(KingsTableTest.textColor);
		timer.setFont(Font.font(KingsTableTest.textFont, FontWeight.BOLD, 30));
		userScore.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
		userScore.setFill(KingsTableTest.textColor);
		userScore.setFont(Font.font(KingsTableTest.textFont, FontWeight.BOLD, 20));
		hboxBOTTOM.getChildren().addAll(highScore, region1, timer, region2, userScore);
		gameBorder.setBottom(hboxBOTTOM);

		// Show Game ------------
		primaryStage.setScene(menu);
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

			Circle piece = new Circle(KingsTableTest.tileSize / 3);

			if (i > 23) {
				piece.setFill(new ImagePattern(dpImage));
			} else {
				piece.setFill(new ImagePattern(apImage));
			}
			GridPane.setRowIndex(piece, yCoor[i]);
			GridPane.setColumnIndex(piece, xCoor[i]);
			GridPane.setHalignment(piece, HPos.CENTER);
			piece.setId(yCoor[i]+","+xCoor[i]);
			piece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK)); // Radius, offsetX, offsetY, color
			piece.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will
												// highlight
				piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
			});
			// Click this piece, save to a global last clicked value
			// Remove last clicked image location replace on new clicked location
			piece.setOnMouseClicked(event ->{
				if(selected==piece) { //piece is already selected
					selected = null;
					piece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
					System.out.println("Unclicked");
				}
				else if(selected==null){ //selecting new piece (Does not let you select a piece if you've already selected something)
					selected = piece;
					piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
					System.out.println("clicked piece" + piece);
				}
			});
			piece.setOnMouseExited(event -> {
				if(selected!=piece) {
					piece.setEffect(new InnerShadow(+6d, 0d, 0d, Color.BLACK));
				}
			});
			gridPaneGAME.getChildren().addAll(piece);
		}
		// King
		Circle king = new Circle(KingsTableTest.tileSize / 2);
		king.setFill(new ImagePattern(dpImage));
		GridPane.setRowIndex(king, 5);
		GridPane.setColumnIndex(king, 5);
		GridPane.setHalignment(king, HPos.CENTER);
		king.setOnMouseClicked(event ->{
			if(selected!=null) { //piece is already selected
				selected = null;
				king.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
				System.out.println("Uncliked");
			}
			else if(selected==null){ //selecting new piece (Does not let you select a piece if you've already selected something)
				selected = king;
				king.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
				System.out.println("clicked piece" + king);
			}
		});
		king.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will highlight
			king.setEffect(new InnerShadow(+50d, 0d, 0d, Color.GOLD));
		});
		king.setOnMouseExited(event -> {
			king.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
		});
		king.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK)); //Radius, offsetX, offsetY, color
		gridPaneGAME.getChildren().addAll(king);
	}
	
	
	public static void createPiece(int team, int level, int selected) { //Player (0=attacker,1=defender) | Level (0=normal,1=king) | selected(0=no,1=yes)
		if(level==1) {
			Circle piece = new Circle(KingsTableTest.tileSize / 2);
		}
		else {
			Circle piece = new Circle(KingsTableTest.tileSize / 3);
		}
		if (team==1) {
			//piece.setFill(new ImagePattern(dpImage)); //make dpImage available here
		} else {
			//piece.setFill(new ImagePattern(apImage));
		}
	}

}
