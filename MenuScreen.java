
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuScreen {
    // Menu Scene

    public static KingsTableProgram KingsTableProgram = new KingsTableProgram();
    public static Timeline timeline = new Timeline();
    public static Label timeLabel = new Label();
    public static long startTime = -1;
    public static long timeDifference = 0;
    public static int playerMode = 0;

    public static void display(Stage primaryStage) {

        BorderPane menuBorder = new BorderPane();
        menuBorder.setPadding(new Insets(15, 520, 100, 150));
        // Screen Size
        int menuWidth = 1000;
        int menuHeight = 700;

        StackPane menuBackgroundImgContainer = new StackPane();
        ImageView menuBgImage = new ImageView("vikingFire.jpg");//background image
        menuBgImage.setFitHeight(menuHeight + 10);
        menuBgImage.setFitWidth(menuWidth + 10);
        menuBackgroundImgContainer.getChildren().addAll(menuBgImage, menuBorder);
        Config.menu = new Scene(menuBackgroundImgContainer, menuWidth, menuHeight);

        Button button1Player = new Button("Play 1 player"); // 1 player game button
        button1Player.resize(menuWidth * .05, menuHeight * .1);
        button1Player.setStyle("-fx-background-color: #B8860B");
        button1Player.setOnMouseEntered(highlightOnPlayGame -> {
            // highlight
            button1Player.setStyle("-fx-background-color: #FFD700");
        });
        button1Player.setOnMouseExited(highlightOff -> {
            button1Player.setStyle("-fx-background-color: #B8860B");
        });
        Button button2Player = new Button("Play 2 player"); // 2 player game button
        button2Player.resize(menuWidth * .05, menuHeight * .1);
        button2Player.setStyle("-fx-background-color: #B8860B");
        button2Player.setOnMouseEntered(highlightOnPlayGame -> {
            // highlight
            button2Player.setStyle("-fx-background-color: #FFD700");
        });
        button2Player.setOnMouseExited(highlightOff -> {
            button2Player.setStyle("-fx-background-color: #B8860B");
        });

        Button buttonHelp = new Button("Help Screen"); // goes to help screen
        buttonHelp.resize(menuWidth * .05, menuHeight * .1);
        buttonHelp.setStyle("-fx-background-color: #B8860B");
        buttonHelp.setOnMouseEntered(highlightOnHelpScreen -> {
            // highlight
            buttonHelp.setStyle("-fx-background-color: #FFD700");
        });
        buttonHelp.setOnMouseExited(highlightOff -> {
            buttonHelp.setStyle("-fx-background-color: #B8860B");
        });
        Text menuTitle = new Text("King's Table");
        menuTitle.setFont(new Font(Config.textFont, 80));
        menuTitle.setFill(Color.ORANGERED);
        menuTitle.setStroke(Color.RED);

        button1Player.setOnAction(clickToGame -> { // button goes to gameScreen and starts timer
            primaryStage.setScene(Config.game);
            if (startTime == -1) {
                startTime = System.currentTimeMillis();
            }

            DateFormat timeFormat = new SimpleDateFormat("mm:ss");

            if (!(MenuScreen.timeline.getStatus().valueOf("PAUSED") == MenuScreen.timeline.getStatus())) {

                MenuScreen.timeline = new Timeline(
                        new KeyFrame(
                                Duration.seconds(1), // increments every second
                                event -> {
                                    long currentTime = System.currentTimeMillis();// stores system time into the currentTime variable
                                    final long diff = currentTime - startTime;//math for time
                                    MenuScreen.timeDifference = diff; // sets time difference to diff
                                    if (diff <= 0) {
                                        timeLabel.setText("00:00");
                                        timeLabel.setText(timeFormat.format(0));
                                    } else {
                                        timeLabel.setText(timeFormat.format(diff));
                                    }

                                }));
                timeline.setCycleCount(Timeline.INDEFINITE);

            }
            timeline.play();//starts timer
            primaryStage.setTitle("Kings Table: One Player Mode");
            playerMode = 1;
            
        });//click button go to Game screen for 2 player
        button2Player.setOnAction(clickToGame -> {
            primaryStage.setScene(Config.game);
            primaryStage.setTitle("Kings Table: Two Player Mode");
            timeLabel.setText("");
            playerMode = 2;
        });//click button go to Game screen for now
        buttonHelp.setOnAction(clickToHelpScreen -> primaryStage.setScene(Config.help));//click button go to Help screen
        VBox titleMenuBox = new VBox(20);
        HBox boxOfButtons = new HBox(20);
        boxOfButtons.getChildren().addAll(button1Player, button2Player, buttonHelp);
        titleMenuBox.getChildren().addAll(menuTitle, boxOfButtons);
        menuBorder.setBottom(titleMenuBox);
    }

}
///////////// end of menu scene