import java.util.ArrayList;
import java.lang.Math;


public class Board {
    //Fields.
    private int size = 11; //Dimensions of the board.
    private int tileSize = 45; //Size of tile of the board in pixels.
    public int[][] boardState = new int[size][size]; //Change to be an array of pieces later/
    private int selectedTileX = -1; //Selected tile of the board.
    private int selectedTileY = -1;
    //Constructor
    public Board() {
        //Set up the empty board.
        //These are positions for the 11x11 board. Modify later to depend on board dimensions.
        for (int i=3;i<=7;i++){
            boardState[i][0] = 2;
            boardState[0][i] = 2;
            boardState[10][i] = 2;
            boardState[i][10] = 2;  
        }
        boardState[5][1] = 2;
        boardState[1][5] = 2;
        boardState[9][5] = 2;
        boardState[5][9] = 2;
        //Defenders
        boardState[5][3] = 1;
        boardState[4][4] = 1;
        boardState[5][4] = 1;
        boardState[6][4] = 1;
        boardState[3][5] = 1;
        boardState[4][5] = 1;
        boardState[6][5] = 1;
        boardState[7][5] = 1;
        boardState[4][6] = 1;
        boardState[5][6] = 1;
        boardState[6][6] = 1;
        boardState[5][7] = 1;
        //King
        boardState[5][5] = 3;
    }
    
    //This method checks for a valid move and updates the board state.
    //Returns false if the move is invalid.
    public boolean movePiece(int x1,int y1,int x2,int y2){
        //Checks if the space is occupied.
        if (boardState[x2][y2] != 0){
            return false;
        }
        //Checks for rook movement.
        else if ((x1 != x2) && (y1 != y2)){
            return false;
        }
        //Checks for pieces in the way.
        else if (x1 != x2){
            int l = Math.min(x1,x2);
            int u = Math.max(x1,x2);
            for (int i = l+1; i < u;i++){
                if (boardState[i][y1] != 0){
                    return false;
                }
            }
        }
        else if (y1 != y2){
            int l = Math.min(y1,y2);
            int u = Math.max(y1,y2);
            for (int i = l+1; i < u;i++){
                if (boardState[x1][i] != 0){
                    return false;
                }
            }
        }
        //If all these checks pass then the move must be valid.
        //Update the board and return that the move is valid.
        boardState[x2][y2] = boardState[x1][y1];
        boardState[x1][y1] = 0;
        return true;
    }
    
    //Getters and Setters.
    //Set and get the size of the board.
    public void setSize(int size){
        this.size = size;
    }
    
    public int getSize(){
        return this.size;
    }
    
    //Set and get the size of a tile.
    public void setTileSize(int tileSize){
        this.tileSize = tileSize;
    }
    public int getTileSize(){
        return this.tileSize;
    }
    
    public int getSelectedTileX(){
        return this.selectedTileX;
    }
    public int getSelectedTileY(){
        return this.selectedTileY;
    }
    public void printBoard(){
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                System.out.print(boardState[i][j]);
            }
            System.out.println();
        }
    }
    
    
}
