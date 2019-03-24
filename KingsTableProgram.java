import java.awt.*;
import javax.swing.*;
import javafx.application.Applicaiton;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KingsTableProgram extends Application
{
   public static int boardSize = 11; //always Odd# x Odd#, usually 11x11 or 13x13
   public static int titleSize = 100; //px size of the grid boxes
   private Tile[][] board = new Tile[boardSize][boardSize];
   
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
      pane.setLayout(new GridLayout(rows,cols));
      for(int i=0; i<rows; i++){
         for(int j=0; j<cols; j++){
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            if((i==0 && (j==0 || j==(cols-1))) || (i==(cols-1) && (j==0 || j==(cols-1))) || (i==(cols/2) && j==(cols/2)))
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