import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class gameScreen {
	
	//Constructor
	public gameScreen() {
		Board board = new Board();
		int boardSize = board.getSize(); //always Odd# x Odd#, usually 11x11 or 13x13
		   
		Color c1 = Color.WHITE; //default colors
		Color c2 = Color.GRAY;
		BorderPane border = new BorderPane();
		HBox hbox = new HBox();
		//hbox.setPadding(new Insets(15,12,15,12));
		hbox.setStyle("-fx-background-color: #336699;");
	    Button buttonMenu = new Button("///");
	    buttonMenu.setSize(50,50);
	    border.setTop(hbox);
	    JFrame kingsBoard = new JFrame();
		kingsBoard.setSize(800,800);
		kingsBoard.setTitle("King's Table");
		Container pane = kingsBoard.getContentPane();
		pane.setLayout(new GridLayout(boardSize,boardSize));
		
		for(int i=0; i<boardSize; i++){
			for(int j=0; j<boardSize; j++){
				JPanel panel = new JPanel();
				panel.setBorder(BorderFactory.createLineBorder(Color.black));
				if((i==0 && (j==0 || j==(boardSize-1))) || (i==(boardSize-1) && (j==0 || j==(boardSize-1))) || (i==(boardSize/2) && j==(boardSize/2)))
				{
					panel.setBackground(c2);
				}
				else{panel.setBackground(c1);}
				pane.add(panel);
			}
		}
		kingsBoard.setVisible(true);
	}
}
