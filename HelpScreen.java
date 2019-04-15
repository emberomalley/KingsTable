import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelpScreen {
	public static void display(Stage primaryStage) {
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
        Config.help = new Scene(helpBackgroundImgContainer, helpWidth, helpHeight);

        // TOP (Menu Button and Title of Help Scene)------------
        HBox hboxTOPHelp = new HBox();
        hboxTOPHelp.setAlignment(Pos.TOP_LEFT);
        // set up for HBox containing the title and the button: (Top/Left/Right/Center/Bottom)
        hboxTOPHelp.setPadding(new Insets(15, 0, 15, 5));
        Button buttonBackToMenu = new Button("Menu");
        buttonBackToMenu.resize(50, 50);
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
        helpTitle.setFont(new Font(Config.textFont, 80));//setting font and its size
        helpTitle.setFill(Color.WHITESMOKE);
        helpTitle.setStroke(Color.DARKGOLDENROD);
        buttonBackToMenu.setOnAction(backToMenu -> primaryStage.setScene(Config.menu));//click button and go back to menu screen
        //rules and instructions
        Text helpText = new Text(20,20, "The rules are as follows:\n" +
"●	There are two players, the attackers (24 fighter pieces, shown in black) and the defenders (12 fighter pieces and one king, shown in white)\n" +
"●	The attackers are trying to capture the king, and the defenders are trying move the King to a corner square.\n" +
"●	It is played typically on a 11x11 board, \n" +
"●	If a piece is flanked by two opposing pieces, it is removed from the board\n" +
"●	If the king is flanked on all sides by opposing pieces, that king loses. \n" +
"●	Pieces can only move in straight lines, but at whatever distance they want (like a rook in chess)\n" +
"●	Turns must alternate, and each player can only move one piece per turn.\n" +
"●	The King starts in the center, surrounded by its defenders. The attackers start along the edges of the board.");
              helpText.setFont(Font.font(Config.textFont, FontWeight.SEMI_BOLD, FontPosture.REGULAR,14));
              helpText.setFill(Color.WHITESMOKE);

              Text helpText2 = new Text(20,20, "User Interaction:\n"+
                      "The move pieces click on your desired piece and click on the square you want to move it to");
              helpText2.setFont(Font.font(Config.textFont, FontWeight.SEMI_BOLD, FontPosture.REGULAR,14));
              helpText2.setFill(Color.WHITESMOKE);

        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(helpTitle, buttonBackToMenu, helpText, helpText2);
        hboxTOPHelp.setSpacing(295);
        helpBorder.setTop(layout2);
        //end of Help scene
	}
}
