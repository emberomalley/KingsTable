
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;


public class KingsTableProgram extends Application {

    public static Board board = new Board();
    public static MenuScreen MenuScreen = new MenuScreen();
   // public static gameScreen gameScreen = new gameScreen();
    public static Node selected;
    public static int boardSize = board.getSize(); // always Odd# x Odd#, usually 11x11 or 13x13
    public static int tileSize = board.getTileSize(); // px size of the grid boxes
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    public static Color regSquareColor = Color.WHITE; // default colors
    public static Color kingSquareColor = Color.GRAY;
    public static Color textColor = Color.DARKGOLDENROD;
    public static String textFont = "Rockwell";
    public static int illegalPiece = 2;
    public static int doubleCaptureCounter = 0;
    public static int tripleCaptureCounter = 0;
    public static int pieceCaptureCounter = 0;
    public static int currentScore = 0;

    public static void game() {

    }

    public static void gameScreen() {

    }

    public static void MenuScreen() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Creates all screens
        MenuScreen.display(primaryStage);
        HelpScreen.display(primaryStage);
        GridPane gridPaneGAME = new GridPane(); //main game's grid pane
        // Initialize primary display
        primaryStage.setScene(Config.menu);
        primaryStage.setTitle("King's Table");
        primaryStage.setResizable(false);
        primaryStage.show();
        // Game Scene
        // set up background border pane (Top/Left/Right/Center/Bottom)
        BorderPane gameBorder = new BorderPane();
        HBox hboxBOTTOM = new HBox();
        Label userScore = new Label("Score: " + KingsTableProgram.board.score);

        // Screen Size
        int gameWidth = 1000;
        int gameHeight = 700;

        Image dpImage = new Image("defenderPiece.jpg");
        Image apImage = new Image("attackerPiece.jpg");

        // Background Image--------------
        StackPane gameBackgroundImgContainer = new StackPane();
        ImageView gameBgImage = new ImageView("stoneBG.jpg");
        gameBgImage.setFitHeight(gameHeight + 100);
        gameBgImage.setFitWidth(gameWidth + 500);
        gameBackgroundImgContainer.getChildren().addAll(gameBgImage, gameBorder);
        Config.game = new Scene(gameBackgroundImgContainer, gameWidth, gameHeight);

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
        //click button and go back to menu screen
        buttonMenu.setOnAction(event -> {
            primaryStage.setTitle("Kings Table");
            primaryStage.setScene(Config.menu);
            MenuScreen.timeline.pause();
            KingsTableProgram.illegalPiece = 2;

        });
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



     // LEFT (white Game Pieces graveyard)------------
        
        VBox vboxLeft = new VBox();
        vboxLeft.setSpacing(10);
        vboxLeft.setAlignment(Pos.BOTTOM_LEFT);
        vboxLeft.setPadding(new Insets(0, 0, 20, 200));// top,right,bottom,left
        Circle placeHolderD = new Circle(KingsTableProgram.tileSize / 3);
        placeHolderD.setFill(Color.TRANSPARENT);
        vboxLeft.getChildren().addAll(placeHolderD);
        //vboxLeft.setPrefHeight(100);
        // vboxLeft.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
        gameBorder.setLeft(vboxLeft);

        // RIGHT (black Game Pieces graveyard)------------
        VBox vboxRight = new VBox();
        vboxRight.setSpacing(10);
        vboxRight.setAlignment(Pos.BOTTOM_LEFT);
        vboxRight.setPadding(new Insets(0, 200, 20, 0));
        Circle placeHolderA = new Circle(KingsTableProgram.tileSize / 3);
        placeHolderA.setFill(Color.TRANSPARENT);
        vboxRight.getChildren().addAll(placeHolderA);
        //vboxRight.setPrefHeight(100);
        // vboxRight.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
        gameBorder.setRight(vboxRight);





        // Center Pause Menu/Game Over Screen ---------------------
        StackPane PauseScreen = new StackPane();
        VBox pauseScreenItems = new VBox();
        pauseScreenItems.setSpacing(20);
        //PauseScreen.setAlignment(Pos.TOP_LEFT);
        PauseScreen.setPadding(new Insets(15, 15, 15, 15));// top,right,bottom,left
        Label pauseScreenText = new Label();
        Label movesText = new Label();
        Label piecesCapturedText = new Label();
        Label timePassedText = new Label();
        Label totalScoreText = new Label();
        // Achievements
        Label achievementsText = new Label();
        Label winText = new Label();

        //Button buttonMenu = new Button("Menu"); // Menu Button
        pauseScreenText.setText("Pause Menu");
        Region pauseSpacer1 = new Region(); // spacer
        VBox.setVgrow(pauseSpacer1, Priority.ALWAYS);
        movesText.setText("Moves..............__.... x5pts");
        piecesCapturedText.setText("Pieces Captured....__.... x5pts");
        timePassedText.setText("Time...............__.... x5pts");
        totalScoreText.setText("TOTAL SCORE........__.... x5pts");
        movesText.setTextFill(KingsTableProgram.textColor);
        movesText.setFont(Font.font(KingsTableProgram.textFont, 20));
        piecesCapturedText.setTextFill(KingsTableProgram.textColor);
        piecesCapturedText.setFont(Font.font(KingsTableProgram.textFont, 20));
        timePassedText.setTextFill(KingsTableProgram.textColor);
        timePassedText.setFont(Font.font(KingsTableProgram.textFont, 20));
        totalScoreText.setTextFill(KingsTableProgram.textColor);
        totalScoreText.setFont(Font.font(KingsTableProgram.textFont, 20));
        achievementsText.setTextFill(KingsTableProgram.textColor);
        achievementsText.setFont(Font.font(KingsTableProgram.textFont, 20));
        winText.setTextFill(KingsTableProgram.textColor);
        winText.setFont(Font.font(KingsTableProgram.textFont, 20));
        pauseScreenText.setTextFill(KingsTableProgram.textColor);
        pauseScreenText.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 50));
        PauseScreen.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        Region pauseSpacer2 = new Region();
        VBox.setVgrow(pauseSpacer2, Priority.ALWAYS);
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
        exitButton.setOnAction(event -> primaryStage.getScene().getWindow().hide());

        Button restartButton = new Button("Restart"); // Menu Button
        //restartButton.resize(50, 50);
        restartButton.setStyle("-fx-background-color: #B8860B");
        restartButton.setOnMouseEntered(event -> { //
            // highlight
        	restartButton.setStyle("-fx-background-color: #FFD700");
        });
        restartButton.setOnMouseExited(event -> {
        	restartButton.setStyle("-fx-background-color: #B8860B");
        });
        restartButton.setOnAction(clickToGame -> { PauseScreen.setVisible(false);
        											//gridPaneGAME.getChildren().remove(PauseScreen);
        											gameBorder.setCenter(gridPaneGAME); //TODO
        											for (int i=0; i<KingsTableProgram.boardSize; i++) {
        												for (int j=0; j<KingsTableProgram.boardSize; j++) {
        													if ((KingsTableProgram.board.getPieceType(i, j)) > 0) {
        														gridPaneGAME.getChildren().remove(getPieceAtPosition(i, j, gridPaneGAME));
        													}
        												}
        											}
        											board = new Board();
        											userScore.setText("Score: " + KingsTableProgram.board.score);
        											for (int i = 0; i < KingsTableProgram.boardSize; i++) {
        									            for (int j = 0; j < KingsTableProgram.boardSize; j++) {
        									                if (KingsTableProgram.board.boardState[i][j] != 0) {
        									                    Circle piece = new Circle(KingsTableProgram.tileSize / 3);
        									                    //Defender
        									                    if (KingsTableProgram.board.boardState[i][j] == 1) {
        									                        piece.setFill(new ImagePattern(dpImage));
        									                        //Attacker
        									                    } else if (KingsTableProgram.board.boardState[i][j] == 2) {
        									                        piece.setFill(new ImagePattern(apImage));
        									                        //King
        									                    } else if (KingsTableProgram.board.boardState[i][j] == 3) {
        									                        piece.setRadius(KingsTableProgram.tileSize / 2);
        									                        piece.setFill(new ImagePattern(dpImage));
        									                    }
        									                    GridPane.setRowIndex(piece, i);
        									                    GridPane.setColumnIndex(piece, j);
        									                    GridPane.setHalignment(piece, HPos.CENTER);
        									                    piece.setId("piece");
        									                    piece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK)); // Radius, offsetX, offsetY, color

        									                    //Determine which pieces, if any, the player cannot control.
        									                    piece.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will
        									                        // highlight
        									                        //Change this condition to specify which pieces can be highlighted.
        									                        if (KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece
        									                                && KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece + 2) {
        									                            piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
        									                        }
        									                    });
        									                    // Click this piece, save to a global last clicked value
        									                    // Remove last clicked image location replace on new clicked location
        									                    piece.setOnMouseClicked(event -> {
        									                        if (selected == piece) { //piece is already selected
        									                            selected = null;
        									                            piece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
        									                            //Change this condition to specify which pieces can be selected
        									                        } else if (selected == null && (KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece)
        									                                && KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece + 2) {
        									                            selected = piece;
        									                            piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
        									                        }
        									                    });
        									                    piece.setOnMouseExited(event -> {
        									                        if (selected != piece) {
        									                            piece.setEffect(new InnerShadow(+6d, 0d, 0d, Color.BLACK));
        									                        }
        									                    });
        									                    gridPaneGAME.getChildren().addAll(piece);}}}
        											
        											System.out.println("Restarted Game");});
        exitButton.setMaxSize(200, 150);
        restartButton.setMaxSize(200, 150);

        pauseScreenItems.getChildren().addAll(pauseScreenText,
        										pauseSpacer1,
        										movesText,
        										piecesCapturedText,
        										timePassedText,
        										winText,
        										achievementsText,
        										totalScoreText,
        										pauseSpacer2,
        										restartButton,
        										exitButton);
        PauseScreen.getChildren().addAll(pauseScreenItems);
        pauseScreenItems.setAlignment(Pos.TOP_CENTER);




        // CENTER (Game Table)-----------
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
                square.setOnMouseClicked((MouseEvent event) -> {
                    String[] coordinates = square.getId().split(",");
                    if (selected != null) {
                        //movePiece function verifies the move and updates board state.
                        if (KingsTableProgram.board.movePiece(GridPane.getRowIndex(selected), GridPane.getColumnIndex(selected), Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) {
                            System.out.println("Piece " + KingsTableProgram.board.getPieceType(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])) + " moved from (" + GridPane.getRowIndex(selected) + "," + GridPane.getColumnIndex(selected) + ") to (" + coordinates[0] + "," + coordinates[1] + ").");
                            KingsTableProgram.board.moves++;

                            //This stuff updates the display.
                            //if Piece is King
                            if (KingsTableProgram.board.getPieceType(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])) == 3) {
                                //if King Moved to Kings Square
                                if ((Integer.parseInt(coordinates[0]) == 0 && (Integer.parseInt(coordinates[1]) == 0 || Integer.parseInt(coordinates[1]) == (boardSize - 1)))
                                        || (Integer.parseInt(coordinates[0]) == (boardSize - 1) && (Integer.parseInt(coordinates[1]) == 0 || Integer.parseInt(coordinates[1]) == (boardSize - 1)))) {
                                    System.out.println("Defenders Win!");
                                    pauseScreenText.setText("Defenders Win!");
                                    winText.setText("Victory!..................... +500pts");
                                    movesText.setText("Moves.............."+ KingsTableProgram.board.moves +".... x5pts");
                                    piecesCapturedText.setText("Pieces Captured...."+ pieceCaptureCounter +".... x10pts");
                                    int timeScore = 0;
                                    if((MenuScreen.timeDifference/1000)/60 <= 2) {
                                    	timeScore = 100;
                                    }
                                    else if((MenuScreen.timeDifference/1000)/60 <= 5) {
                                    	timeScore = 80;
                                    }
                                    else if((MenuScreen.timeDifference/1000)/60 <= 10) {
                                    	timeScore = 60;
                                    }
                                    else if((MenuScreen.timeDifference/1000)/60 <= 15) {
                                    	timeScore = 40;
                                    }
                                    else if((MenuScreen.timeDifference/1000)/60 <= 20) {
                                    	timeScore = 20;
                                    }
                                    timePassedText.setText("Time..............."+(MenuScreen.timeDifference/1000)/60+"mins.... +"+timeScore+"pts");

                                    if(doubleCaptureCounter > 0 || tripleCaptureCounter > 0) {
                                    	//Achievements
                                    	achievementsText.setText("Multiple Captures......."+
                                    						(doubleCaptureCounter+tripleCaptureCounter)+
                                    						"....+"+((doubleCaptureCounter * 100)+(tripleCaptureCounter * 200))+
                                    						"pts");
                                    }
                                    totalScoreText.setText("TOTAL SCORE........"+(500+(KingsTableProgram.board.moves * 5)+KingsTableProgram.board.score + timeScore+(doubleCaptureCounter*100)+(tripleCaptureCounter*200)));
                                    gridPaneGAME.getChildren().addAll(PauseScreen);
                                    gameBorder.setCenter(PauseScreen);
                                    MenuScreen.timeline.stop();
                                    // Clear Graveyards and re-add the placeholder pieces
                                    vboxLeft.getChildren().clear();
									vboxRight.getChildren().clear();
							        vboxLeft.getChildren().addAll(placeHolderD);
							        vboxRight.getChildren().addAll(placeHolderD);

                                }
                            }

                            gridPaneGAME.getChildren().remove(selected);
                            GridPane.setRowIndex(selected, Integer.parseInt(coordinates[0]));
                            GridPane.setColumnIndex(selected, Integer.parseInt(coordinates[1]));
                            GridPane.setHalignment(selected, HPos.CENTER);
                            gridPaneGAME.getChildren().add(selected);
                            selected.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                            selected = null;

                            //Check captures.
                            int count = 0;
                            // Must be kept separate if statements to allow for multiple captures at once
                            if (KingsTableProgram.board.checkCapture(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), "right")) {
                                gridPaneGAME.getChildren().remove(getPieceAtPosition(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]) + 1, gridPaneGAME));
                                KingsTableProgram.board.score += 10;
                                pieceCaptureCounter++;
                                Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                graveYardPiece.setFill(new ImagePattern(apImage));
                                graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                vboxRight.getChildren().addAll(graveYardPiece);
                                currentScore = KingsTableProgram.board.getScore();
                                userScore.setText("Score: " + KingsTableProgram.board.score);
                                //TODO
                                count++;
                            }
                            if (KingsTableProgram.board.checkCapture(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), "left")) {
                                gridPaneGAME.getChildren().remove(getPieceAtPosition(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]) - 1, gridPaneGAME));
                                KingsTableProgram.board.score += 10;
                                pieceCaptureCounter++;
                                Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                graveYardPiece.setFill(new ImagePattern(apImage));
                                graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                vboxRight.getChildren().addAll(graveYardPiece);
                                currentScore = KingsTableProgram.board.getScore();
                                userScore.setText("Score: " + KingsTableProgram.board.score);
                                count++;
                            }
                            if (KingsTableProgram.board.checkCapture(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), "down")) {
                                gridPaneGAME.getChildren().remove(getPieceAtPosition(Integer.parseInt(coordinates[0]) + 1, Integer.parseInt(coordinates[1]), gridPaneGAME));
                                KingsTableProgram.board.score += 10;
                                pieceCaptureCounter++;
                                Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                graveYardPiece.setFill(new ImagePattern(apImage));
                                graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                vboxRight.getChildren().addAll(graveYardPiece);
                                currentScore = KingsTableProgram.board.getScore();
                                userScore.setText("Score: " + KingsTableProgram.board.score);
                                count++;
                            }
                            if (KingsTableProgram.board.checkCapture(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), "up")) {
                                gridPaneGAME.getChildren().remove(getPieceAtPosition(Integer.parseInt(coordinates[0]) - 1, Integer.parseInt(coordinates[1]), gridPaneGAME));
                                KingsTableProgram.board.score += 10;
                                pieceCaptureCounter++;
                                Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                graveYardPiece.setFill(new ImagePattern(apImage));
                                graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                vboxRight.getChildren().addAll(graveYardPiece);
                                currentScore = KingsTableProgram.board.getScore();
                                userScore.setText("Score: " + KingsTableProgram.board.score);
                                count++;
                            }

                            //Additional points for multiple captures.
                            if (count == 2) {
                                KingsTableProgram.board.score += 100;
                                doubleCaptureCounter++;
                                currentScore = KingsTableProgram.board.getScore();
                                userScore.setText("Score: " + KingsTableProgram.board.score);
                                System.out.println("Double Capture! +5 points.");
                            }
                            else if (count == 3){
                                KingsTableProgram.board.score += 200;
                                tripleCaptureCounter++;
                                userScore.setText("Score: " + KingsTableProgram.board.score);
                                currentScore = KingsTableProgram.board.getScore();
                                System.out.println("Triple Capture!! +10 points.");

                            }

                            //Check King Capture
                            if (KingsTableProgram.board.checkKingCapture(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) {
                                //Attackers win.
                                System.out.println("Attackers Win!");
                                pauseScreenText.setText("Attackers Win!");
                                movesText.setText("Moves.............."+ KingsTableProgram.board.moves +".... x5pts");
                                piecesCapturedText.setText("Pieces Captured...."+ pieceCaptureCounter +".... x10pts");
                                int timeScore = 0;
                                if((MenuScreen.timeDifference/1000)/60 <= 2) {
                                	timeScore = 100;
                                }
                                else if((MenuScreen.timeDifference/1000)/60 <= 5) {
                                	timeScore = 80;
                                }
                                else if((MenuScreen.timeDifference/1000)/60 <= 10) {
                                	timeScore = 60;
                                }
                                else if((MenuScreen.timeDifference/1000)/60 <= 15) {
                                	timeScore = 40;
                                }
                                else if((MenuScreen.timeDifference/1000)/60 <= 20) {
                                	timeScore = 20;
                                }
                                timePassedText.setText("Time..............."+(MenuScreen.timeDifference/1000)/60+"mins.... +"+timeScore+"pts");

                                if(doubleCaptureCounter > 0 || tripleCaptureCounter > 0) {
                                	//Achievements
                                	achievementsText.setText("Multiple Captures......."+
                                						(doubleCaptureCounter+tripleCaptureCounter)+
                                						"....+"+((doubleCaptureCounter * 100)+(tripleCaptureCounter * 200))+
                                						"pts");
                                }
                                totalScoreText.setText("TOTAL SCORE........"+((KingsTableProgram.board.moves * 5)+KingsTableProgram.board.score + timeScore+(doubleCaptureCounter*100)+(tripleCaptureCounter*200)));
                                gridPaneGAME.getChildren().addAll(PauseScreen);
                                gameBorder.setCenter(PauseScreen);
                                MenuScreen.timeline.stop();
                            }

                            //Check if the user is playing the AI here.
                            if (primaryStage.getTitle() == "Kings Table: One Player Mode") {
                                //This returns the coordinates of the piece to move and the coordinates of where to move it.
                                List<Integer> coords = KingsTableProgram.board.moveAttacker();
                                if (coords != null) {
                                    selected = getPieceAtPosition(coords.get(0), coords.get(1), gridPaneGAME);
                                    System.out.println("Piece " + KingsTableProgram.board.getPieceType(coords.get(2), coords.get(3)) + " moved from (" + GridPane.getRowIndex(selected) + "," + GridPane.getColumnIndex(selected) + ") to (" + coords.get(2) + "," + coords.get(3) + ").");
                                    GridPane.setRowIndex(selected, coords.get(2));
                                    GridPane.setColumnIndex(selected, coords.get(3));
                                    selected = null;
                                } else {
                                    System.out.println("The attackers are unable to move.");
                                }

                                //Check captures.
                                if (KingsTableProgram.board.checkCapture(coords.get(2), coords.get(3), "right")) {
                                	Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                    graveYardPiece.setFill(new ImagePattern(dpImage));
                                    graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                    vboxLeft.getChildren().addAll(graveYardPiece);
                                    gridPaneGAME.getChildren().remove(getPieceAtPosition(coords.get(2), coords.get(3) + 1, gridPaneGAME));
                                }
                                if (KingsTableProgram.board.checkCapture(coords.get(2), coords.get(3), "left")) {
                                	Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                    graveYardPiece.setFill(new ImagePattern(dpImage));
                                    graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                    vboxLeft.getChildren().addAll(graveYardPiece);
                                    gridPaneGAME.getChildren().remove(getPieceAtPosition(coords.get(2), coords.get(3) - 1, gridPaneGAME));
                                }
                                if (KingsTableProgram.board.checkCapture(coords.get(2), coords.get(3), "down")) {
                                	Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                    graveYardPiece.setFill(new ImagePattern(dpImage));
                                    graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                    vboxLeft.getChildren().addAll(graveYardPiece);
                                    gridPaneGAME.getChildren().remove(getPieceAtPosition(coords.get(2) + 1, coords.get(3), gridPaneGAME));
                                }
                                if (KingsTableProgram.board.checkCapture(coords.get(2), coords.get(3), "up")) {
                                	Circle graveYardPiece = new Circle(KingsTableProgram.tileSize / 3);
                                    graveYardPiece.setFill(new ImagePattern(dpImage));
                                    graveYardPiece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                                    vboxLeft.getChildren().addAll(graveYardPiece);
                                    gridPaneGAME.getChildren().remove(getPieceAtPosition(coords.get(2) - 1, coords.get(3), gridPaneGAME));
                                }

                                //Check King Capture
                                if (KingsTableProgram.board.checkKingCapture(coords.get(2), coords.get(3))) {
                                    //Attackers win.
                                    System.out.println("Attackers Win!");
                                    pauseScreenText.setText("Attackers Win!");
                                    gridPaneGAME.getChildren().addAll(PauseScreen);
                                    gameBorder.setCenter(PauseScreen);
                                    MenuScreen.timeline.stop();
                                }
                            } //Switch Turns
                            else if (KingsTableProgram.illegalPiece == 1) {
                                KingsTableProgram.illegalPiece = 2;
                            } else {
                                KingsTableProgram.illegalPiece = 1;
                            }

                            //Display the text board for testing.
                            KingsTableProgram.board.printBoard();

                        } else {
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

        // BOTTOM (High Score, Timer, Player's Score)------------
        
        hboxBOTTOM.setAlignment(Pos.BOTTOM_CENTER);
        hboxBOTTOM.setPadding(new Insets(25, 10, 25, 20));// top,right,bottom,left
        // hboxBOTTOM.setStyle("-fx-background-color: #D3D3D3;"); //for visual testing
        Label highScore = new Label("High Score");
        Label timer = new Label("Timer:");
        //TODO2
        //Label userScore = new Label("Score: " + KingsTableProgram.board.score);
        int currentScore = KingsTableProgram.board.getScore();
        Region region1 = new Region(); // spacer
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        highScore.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK)); // Radius, offsetX, offsetY, color;
        highScore.setTextFill(KingsTableProgram.textColor);
        highScore.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
        timer.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
        timer.setTextFill(KingsTableProgram.textColor);
        timer.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
        MenuScreen.timeLabel.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
        MenuScreen.timeLabel.setTextFill(KingsTableProgram.textColor);
        MenuScreen.timeLabel.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
        userScore.setEffect(new DropShadow(+10d, 0d, 3d, Color.BLACK));
        userScore.setTextFill(KingsTableProgram.textColor);
        userScore.setFont(Font.font(KingsTableProgram.textFont, FontWeight.BOLD, 20));
        //THIS IS IT! THE UPDATE UI
        hboxBOTTOM.getChildren().addAll(highScore, region1, timer, MenuScreen.timeLabel, region2, userScore);
        gameBorder.setBottom(hboxBOTTOM);

        // Show Game ------------
        // Draw Pieces

        if (KingsTableProgram.board.score > currentScore ) {
        	// logic for incrementing score in GUI
        	currentScore = KingsTableProgram.board.getScore();
        	userScore.setText("Score: " + KingsTableProgram.board.score);
        	//hboxBOTTOM.getChildren().addAll(highScore, region1, timer, MenuScreen.timeLabel, region2, userScore);
        }

        for (int i = 0; i < KingsTableProgram.boardSize; i++) {
            for (int j = 0; j < KingsTableProgram.boardSize; j++) {
                if (KingsTableProgram.board.boardState[i][j] != 0) {
                    Circle piece = new Circle(KingsTableProgram.tileSize / 3);
                    //Defender
                    if (KingsTableProgram.board.boardState[i][j] == 1) {
                        piece.setFill(new ImagePattern(dpImage));
                        //Attacker
                    } else if (KingsTableProgram.board.boardState[i][j] == 2) {
                        piece.setFill(new ImagePattern(apImage));
                        //King
                    } else if (KingsTableProgram.board.boardState[i][j] == 3) {
                        piece.setRadius(KingsTableProgram.tileSize / 2);
                        piece.setFill(new ImagePattern(dpImage));
                    }
                    GridPane.setRowIndex(piece, i);
                    GridPane.setColumnIndex(piece, j);
                    GridPane.setHalignment(piece, HPos.CENTER);
                    piece.setId("piece");
                    piece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK)); // Radius, offsetX, offsetY, color

                    //Determine which pieces, if any, the player cannot control.
                    piece.setOnMouseEntered(event -> { // we can add a thing here where if it is the player's piece it will
                        // highlight
                        //Change this condition to specify which pieces can be highlighted.
                        if (KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece
                                && KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece + 2) {
                            piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
                        }
                    });
                    // Click this piece, save to a global last clicked value
                    // Remove last clicked image location replace on new clicked location
                    piece.setOnMouseClicked(event -> {
                        if (selected == piece) { //piece is already selected
                            selected = null;
                            piece.setEffect(new InnerShadow(+10d, 0d, 0d, Color.BLACK));
                            //Change this condition to specify which pieces can be selected
                        } else if (selected == null && (KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece)
                                && KingsTableProgram.board.boardState[GridPane.getRowIndex(piece)][GridPane.getColumnIndex(piece)] != KingsTableProgram.illegalPiece + 2) {
                            selected = piece;
                            piece.setEffect(new InnerShadow(+30d, 0d, 0d, Color.GOLD));
                        }
                    });
                    piece.setOnMouseExited(event -> {
                        if (selected != piece) {
                            piece.setEffect(new InnerShadow(+6d, 0d, 0d, Color.BLACK));
                        }
                    });
                    gridPaneGAME.getChildren().addAll(piece);
                }
            }
        }

    }

    //Function to get the the piece at a given position in the GridPane.
    public Node getPieceAtPosition(int x, int y, GridPane gridPane) {
        Node piece = null;
        for (Node node : gridPane.getChildren()) {
            if (gridPane.getRowIndex(node) == x && gridPane.getColumnIndex(node) == y && node.getId() == "piece") {
                piece = node;
                break;
            }
        }
        return piece;
    }

}
