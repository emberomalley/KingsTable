import java.awt.*;
import javax.swing.*;
//import javafx.application.Applicaiton; //I'm having an import error on my computer (which is effecting the below code) -Ember
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KingsTableProgram //extends Application
{
   public static Board board = new Board();
   
   public static int boardSize = board.getSize(); //always Odd# x Odd#, usually 11x11 or 13x13
   public static int tileSize = board.getTileSize(); //px size of the grid boxes
   
   private Group titleGroup = new Group();
   private Group pieceGroup = new Group();
   
   public static Color c1 = Color.WHITE; //default colors
   public static Color c2 = Color.GRAY;
   
   
   public static void main(String[] args)
   {
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