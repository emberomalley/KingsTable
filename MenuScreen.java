import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuScreen {
	// Menu Scene
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
	         button1Player.resize(menuWidth*.05, menuHeight*.1);
	         button1Player.setStyle("-fx-background-color: #B8860B");
	         button1Player.setOnMouseEntered(highlightOnPlayGame -> {
			      // highlight
			        button1Player.setStyle("-fx-background-color: #FFD700");
	         });
	         button1Player.setOnMouseExited(highlightOff -> {
			         button1Player.setStyle("-fx-background-color: #B8860B");
	         });
	         Button button2Player = new Button("Play 2 player"); // 2 player game button
	         button2Player.resize(menuWidth*.05, menuHeight*.1);
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
	        button1Player.setOnAction(clickToGame -> primaryStage.setScene(Config.game));//click button go to Game screen for now
	        button2Player.setOnAction(clickToGame -> primaryStage.setScene(Config.game));//click button go to Game screen for now
	        buttonHelp.setOnAction(clickToHelpScreen -> primaryStage.setScene(Config.help));//click button go to Help screen
	        VBox layout1 = new VBox(20);
	        layout1.getChildren().addAll(button1Player, button2Player, buttonHelp, menuTitle);
	        menuBorder.setBottom(layout1);
	}
	///////////// end of menu scene
}