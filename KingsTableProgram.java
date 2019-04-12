import javafx.application.Application;
import javafx.concurrent.Task;

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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;


public class KingsTableProgram extends Application {
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

Scene menu, game, help, dWin, aWin;       
	@Override
	public void start(Stage primaryStage) throws Exception {
                //Menu Scene
		// set up background border pane (Top/Left/Right/Center/Bottom)
		BorderPane menuBorder = new BorderPane();
                menuBorder.setPadding(new Insets(15, 520, 100, 150));
		// Screen Size
		int menuWidth = 1000;
		int menuHeight = 700;

                StackPane menuBackgroundImgContainer = new StackPane();
                ImageView menuBgImage = new ImageView("vikingFire.jpg");//background image
                menuBgImage.setFitHeight(menuHeight + 10);
                menuBgImage.setFitWidth(menuWidth + 10);
                menuBackgroundImgContainer.getChildren().addAll(menuBgImage,menuBorder);
                menu = new Scene(menuBackgroundImgContainer, menuWidth,menuHeight);
                
                Button buttonPlayGame = new Button("Play Game"); // play game button
                buttonPlayGame.resize(menuWidth*.05, menuHeight*.1);
                buttonPlayGame.setStyle("-fx-background-color: #B8860B");
                buttonPlayGame.setOnMouseEntered(highlightOnPlayGame -> { 
			// highlight
			buttonPlayGame.setStyle("-fx-background-color: #FFD700");
		});
		buttonPlayGame.setOnMouseExited(highlightOff -> {
			buttonPlayGame.setStyle("-fx-background-color: #B8860B");
		});
                Button buttonHelp = new Button("Help Screen"); // goes to help screen
                buttonHelp.resize(menuWidth*.05, menuHeight*.1);
                buttonHelp.setStyle("-fx-background-color: #B8860B");
                buttonHelp.setOnMouseEntered(highlightOnHelpScreen -> { 
			// highlight
			buttonHelp.setStyle("-fx-background-color: #FFD700");
		});
		buttonHelp.setOnMouseExited(highlightOff -> {
			buttonHelp.setStyle("-fx-background-color: #B8860B");
		});
                Text menuTitle = new Text("King's Table");
                menuTitle.setFont(new Font(textFont, 80));
                menuTitle.setFill(Color.ORANGERED);
                menuTitle.setStroke(Color.RED);
                buttonPlayGame.setOnAction(clickToGame -> primaryStage.setScene(game));//click button go to Game screen
                buttonHelp.setOnAction(clickToHelpScreen -> primaryStage.setScene(help));//click button go to Help screen
                VBox layout1 = new VBox(20);
                layout1.getChildren().addAll(buttonPlayGame, buttonHelp, menuTitle);
                menuBorder.setBottom(layout1);
                ///////////// end of menu scene

                // Help Scene
                // set up background border pane (Top/Left/Right/Center/Bottom)
                BorderPane helpBorder = new BorderPane();
                
                // Screen Size
                int helpWidth = 1000;
		int helpHeight = 700;
                
                // Background Image--------------
                StackPane helpBackgroundImgContainer = new StackPane();
                ImageView helpBgImage = new ImageView("torch.GIF");//background image
                helpBgImage.setFitHeight(helpHeight);
                helpBgImage.setFitWidth(helpWidth);
                helpBackgroundImgContainer.getChildren().addAll(helpBgImage, helpBorder);
                help = new Scene(helpBackgroundImgContainer, helpWidth,helpHeight);
                
                // TOP (Menu Button and Title of Help Scene)------------
                HBox hboxTOPHelp = new HBox();
                hboxTOPHelp.setAlignment(Pos.TOP_LEFT);
                // set up for HBox containing the title and the button: (Top/Left/Right/Center/Bottom)
                hboxTOPHelp.setPadding(new Insets(15,0,15,5));
                Button buttonBackToMenu = new Button("Menu");
                buttonBackToMenu.resize(50,50);
                buttonBackToMenu.setStyle("-fx-background-color: #FFFFFF");
		buttonBackToMenu.setOnMouseEntered(highlightOnMenu -> { //  
			// highlight
			buttonBackToMenu.setStyle("-fx-text-fill: #FFFFFF");
                        buttonBackToMenu.setStyle("-fx-border-color: red");
		});
		buttonBackToMenu.setOnMouseExited(highlightOff -> {
			buttonBackToMenu.setStyle("-fx-background-color: #FFFFFF");
		});
                Text helpTitle = new Text("Help");
                helpTitle.setFont(new Font(textFont, 80));//setting font and its size
                helpTitle.setFill(Color.WHITESMOKE);
                helpTitle.setStroke(Color.DARKGOLDENROD);
                buttonBackToMenu.setOnAction(backToMenu -> primaryStage.setScene(menu));//click button and go back to menu screen
                /*
                Text t = new Text(10,50, "Test");
                t.setText("TEST");
                t.setFont(Font.font("Verdana",20));
                t.setFill(Color.WHITESMOKE);
                */
                VBox layout2 = new VBox(20);
                layout2.getChildren().addAll(helpTitle, buttonBackToMenu);
                hboxTOPHelp.setSpacing(295);
                helpBorder.setTop(layout2);
                
                //end of Help scene
                
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
        game = new Scene(gameBackgroundImgContainer, gameWidth, gameHeight);
                
		// TOP (Menu Button and Title of Game Scene)------------
		HBox hboxTOP = new HBox();
		hboxTOP.setAlignment(Pos.TOP_LEFT);
		hboxTOP.setPadding(new Insets(15, 0, 15, 5));// top,right,bottom,left
		Button buttonMenu = new Button("Menu"); // Menu Button
		buttonMenu.resize(50, 50);
		buttonMenu.setStyle("-fx-background-color: #B8860B");
		buttonMenu.setOnMouseEntered(event -> { //  
			// highlight
			buttonMenu.setStyle("-fx-background-color: #FFD700");
		});
		buttonMenu.setOnMouseExited(event -> {
			buttonMenu.setStyle("-fx-background-color: #B8860B");
		});
                

		Text gameTitle = new Text("King's Table");
                buttonMenu.setOnAction(event -> primaryStage.setScene(menu));//click button and go back to menu screen
                VBox layout3 = new VBox(20);
                layout3.getChildren().addAll(buttonMenu);
                ////
		gameTitle.setFill(KingsTableProgram.textColor);
		gameTitle.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK)); // Radius, offsetX, offsetY, color
		gameTitle.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 50));
		hboxTOP.getChildren().addAll(buttonMenu, gameTitle);
////////                hboxTOP.getChildren().addAll(buttonMenu, buttonHelp, gameTitle);
		hboxTOP.setSpacing(295);
		gameBorder.setTop(hboxTOP);

		// Center Pause Menu/Game Over Screen ---------------------
		StackPane PauseScreen = new StackPane();
		BorderPane PauseScreenBorder = new BorderPane();
		VBox pauseScreenItems = new VBox(30);
		//PauseScreen.setAlignment(Pos.TOP_LEFT);
		PauseScreen.setPadding(new Insets(15, 15, 15, 15));// top,right,bottom,left
		Label pauseScreenText = new Label();
		pauseScreenText.setText("Pause Menu");
		pauseScreenText.setTextFill(KingsTableProgram.textColor);
		pauseScreenText.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 50));
		PauseScreen.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
		Button exitButton = new Button("Exit"); // Menu Button
		//exitButton.resize(50, 50);
		exitButton.setStyle("-fx-background-color: #B8860B");
		exitButton.setOnMouseEntered(event -> { //  
			// highlight
			exitButton.setStyle("-fx-background-color: #FFD700");
		});
		exitButton.setOnMouseExited(event -> {
			exitButton.setStyle("-fx-background-color: #B8860B");
		});
		Button restartButton = new Button("Restart"); // Menu Button
		//exitButton.resize(50, 50);
		restartButton.setStyle("-fx-background-color: #B8860B");
		restartButton.setOnMouseEntered(event -> { //  
			// highlight
			restartButton.setStyle("-fx-background-color: #FFD700");
		});
		restartButton.setOnMouseExited(event -> {
			restartButton.setStyle("-fx-background-color: #B8860B");
		});
		Button mainMenuButton = new Button("Main Menu"); // Menu Button
		//exitButton.resize(50, 50);
		mainMenuButton.setStyle("-fx-background-color: #B8860B");
		mainMenuButton.setOnMouseEntered(event -> { //  
			// highlight
			mainMenuButton.setStyle("-fx-background-color: #FFD700");
		});
		mainMenuButton.setOnMouseExited(event -> {
			mainMenuButton.setStyle("-fx-background-color: #B8860B");
		});
		exitButton.setOnAction(event -> primaryStage.getScene().getWindow().hide());
		pauseScreenItems.getChildren().addAll(pauseScreenText,mainMenuButton,restartButton);
		//PauseScreenBorder.getChildren().addAll(pauseScreenItems,exitButton);
		PauseScreenBorder.setCenter(pauseScreenItems);
		PauseScreenBorder.setBottom(exitButton);
		exitButton.setAlignment(Pos.BOTTOM_CENTER);
		PauseScreen.getChildren().addAll(PauseScreenBorder);
		pauseScreenItems.setAlignment(Pos.TOP_CENTER);
		
		
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
					square.setEffect(new InnerShadow(10d, 0d, 0d, Color.BLACK));
				});

                                // Gabe - Give every box an id
				square.setId(i + "," + j);
				StackPane imageContainer = new StackPane();
				square.setOnMouseClicked(event -> {
					//System.out.println("Clicked a tile" + square);
                                        String[] coordinates = square.getId().split(",");
					if(selected!=null) {
                                                //movePiece function verifies the move and updates board state.
                                                if (KingsTableProgram.board.movePiece(GridPane.getRowIndex(selected), GridPane.getColumnIndex(selected),Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]))){
                                                    System.out.println("Piece " + KingsTableProgram.board.getPieceType(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])) + " moved from (" +GridPane.getRowIndex(selected) +"," + GridPane.getColumnIndex(selected) +  ") to (" +coordinates[0]+","+coordinates[1]+").");
                                                    //Display the text board for testing.
                                                    KingsTableProgram.board.printBoard();
                                                    //This stuff updates the display.
                                                    //if Piece is King
                                                    if(KingsTableProgram.board.getPieceType(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))==3) {
                                                    	//if King Moved to Kings Square
                                                    	if((Integer.parseInt(coordinates[0]) == 0 && (Integer.parseInt(coordinates[1]) == 0 || Integer.parseInt(coordinates[1]) == (boardSize - 1)))
                                        						|| (Integer.parseInt(coordinates[0]) == (boardSize - 1) && (Integer.parseInt(coordinates[1]) == 0 || Integer.parseInt(coordinates[1]) == (boardSize - 1)))) {
                                                    		System.out.println("Defenders Win!");
                                                    		pauseScreenText.setText("Defenders Win!");
                                                    		gridPaneGAME.getChildren().addAll(PauseScreen);
                                                    		gameBorder.setCenter(PauseScreen);
                                                    	}
                                                    }
                                                    
                                                    
                                                    gridPaneGAME.getChildren().remove(selected);
                                                    GridPane.setRowIndex(selected,Integer.parseInt(coordinates[0]) );
                                                    GridPane.setColumnIndex(selected, Integer.parseInt(coordinates[1]));
                                                    GridPane.setHalignment(selected, HPos.CENTER);
                                                    gridPaneGAME.getChildren().add(selected);
                                                    selected.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                                    selected = null;
                                                }
                                                else{
                                                    System.out.println("This move is invalid.");
                                                }
  
					}

				});
                                
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
		highScore.setFill(KingsTableProgram.textColor);
		highScore.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
		timer.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
		timer.setFill(KingsTableProgram.textColor);
		timer.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 30));
		userScore.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
		userScore.setFill(KingsTableProgram.textColor);
		userScore.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
		hboxBOTTOM.getChildren().addAll(highScore, region1, timer, region2, userScore);
		gameBorder.setBottom(hboxBOTTOM);

		// Show Game ------------
		primaryStage.setScene(menu);
		primaryStage.setTitle("King's Table");
		primaryStage.setResizable(false);
		primaryStage.show();

		// Draw Pieces
                Image dpImage = new Image("defenderPiece.jpg");
		Image apImage = new Image("attackerPiece.jpg");
                
                for (int i = 0; i < KingsTableProgram.boardSize; i++) {
			for (int j = 0; j < KingsTableProgram.boardSize; j++) {
                            if (KingsTableProgram.board.boardState[i][j]!= 0){
                                Circle piece = new Circle(KingsTableProgram.tileSize / 3);
                                //Defender
                                if (KingsTableProgram.board.boardState[i][j] == 1) {
                                        piece.setFill(new ImagePattern(dpImage));
                                //Attacker
                                } else if (KingsTableProgram.board.boardState[i][j] == 2){
                                        piece.setFill(new ImagePattern(apImage));
                                //King
                                }else if (KingsTableProgram.board.boardState[i][j] == 3){
                                        piece.setRadius(KingsTableProgram.tileSize / 2);
                                        piece.setFill(new ImagePattern(dpImage));       
                                }
                                GridPane.setRowIndex(piece,i);
                                GridPane.setColumnIndex(piece,j);
                                GridPane.setHalignment(piece, HPos.CENTER);
                                piece.setId(i+","+j);
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
                            			//System.out.println("Uncliked");
                            		}
                            		else if(selected==null){ //selecting new piece (Does not let you select a piece if you've already selected something)
                            			selected = piece;
                            			piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
                            			//System.out.println("clicked piece" + piece);
                            		}
                                });
                                piece.setOnMouseExited(event -> {
                                	if(selected!=piece) {
                            			piece.setEffect(new InnerShadow(+6d, 0d, 0d, Color.BLACK));
                            		}
                                });
                                gridPaneGAME.getChildren().addAll(piece);
                            }
		}
            }
		
	}
	
	public static void createPiece(int team, int level, int selected) { //Player (0=attacker,1=defender) | Level (0=normal,1=king) | selected(0=no,1=yes)
		if(level==1) {
			Circle piece = new Circle(KingsTableProgram.tileSize / 2);
		}
		else {
			Circle piece = new Circle(KingsTableProgram.tileSize / 3);
		}
		if (team==1) {
			//piece.setFill(new ImagePattern(dpImage)); //make dpImage available here
		} else {
			//piece.setFill(new ImagePattern(apImage));
		}
	}

}
