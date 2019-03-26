import java.util.ArrayList;

public class Board {
    //Fields.
    private int size = 11; //Dimensions of the board.
    private int tileSize = 60; //Size of tile of the board in pixels.
    private int[][] Tile = new int[size][size]; //Change to be an array of pieces later/
    private int[] selectedTile = new int[2]; //Selected tile of the board.

    //Constructor
    public Board() {

    }
    
    //Methods
    
    //Updates the selected tile field with the coordinates of the tile at the offset position.
    //xOffset: Horizontal distance in pixels from the top left of the grid.
    //yOffset: Vertical distance in pixels from the top left of the grid.
    //Note: This method will utilize the tileSize field of the class.
    // The offset values can be computed based on the position of the cursor during a click.
    public void selectTile(int xOffset,int yOffset){
        
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
    
    
}
