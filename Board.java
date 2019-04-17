
import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {

    //Fields.
    private int size = 11; //Dimensions of the board.
    private int tileSize = 45; //Size of tile of the board in pixels.
    //boardState: 0 - Empty, 1 - Defender, 2 - Attacker, 3 - King.
    public int[][] boardState = new int[size][size]; //Change to be an array of pieces later/

    private int selectedTileX = -1; //Selected tile of the board.
    private int selectedTileY = -1;

    //Constructor
    public Board() {
        //Set up the empty board.
        //These are positions for the 11x11 board. Modify later to depend on board dimensions.
        for (int i = 3; i <= 7; i++) {
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
    public boolean movePiece(int x1, int y1, int x2, int y2) {
        //Checks if the space is occupied.
        if (boardState[x2][y2] != 0) {
            return false;
        } //Checks for rook movement.
        else if ((x1 != x2) && (y1 != y2)) {
            return false;
        } //Checks for pieces in the way.
        else if (x1 != x2) {
            int l = Math.min(x1, x2);
            int u = Math.max(x1, x2);
            for (int i = l + 1; i < u; i++) {
                if (boardState[i][y1] != 0) {
                    return false;
                }
            }
        } else if (y1 != y2) {
            int l = Math.min(y1, y2);
            int u = Math.max(y1, y2);
            for (int i = l + 1; i < u; i++) {
                if (boardState[x1][i] != 0) {
                    return false;
                }
            }
        }
        //Prevents pieces from moving to the corners.
        if (((x2 == size - 1 && y2 == size - 1) || (x2 == 0 && y2 == size - 1) || (x2 == size - 1 && y2 == 0) || (x2 == 0 && y2 == 0)) && boardState[x1][y1] != 3) {
            return false;
        }
        //If all these checks pass then the move must be valid.
        //Update the board and return that the move is valid.
        boardState[x2][y2] = boardState[x1][y1];
        boardState[x1][y1] = 0;
        return true;
    }

    //This method checks if the piece at position (x,y) is sandwiching any pieces
    //of the opposite color. Returns true or false and updates the boardState.
    public boolean checkCapture(int x, int y, String direction) {
        if (direction == "right") {
            if (y + 2 >= size) {
                return false;
            } else if (Math.abs(boardState[x][y] - boardState[x][y + 1]) == 1 && Math.abs(boardState[x][y] - boardState[x][y + 2]) != 1 && boardState[x][y + 1] != 0 && boardState[x][y + 1] != 3 && boardState[x][y + 2] != 0) {
                System.out.println("Piece " + boardState[x][y + 1] + " captured from (" + x + "," + (y + 1) + ").");
                boardState[x][y + 1] = 0;
                return true;
            }
        } else if (direction == "left") {
            if (y - 2 < 0) {
                return false;
            } else if (Math.abs(boardState[x][y] - boardState[x][y - 1]) == 1 && Math.abs(boardState[x][y] - boardState[x][y - 2]) != 1 && boardState[x][y - 1] != 0 && boardState[x][y - 1] != 3 && boardState[x][y - 2] != 0) {
                System.out.println("Piece " + boardState[x][y - 1] + " captured from (" + x + "," + (y - 1) + ").");
                boardState[x][y - 1] = 0;
                return true;
            }
        } else if (direction == "down") {
            if (x + 2 >= size) {
                return false;
            } else if (Math.abs(boardState[x][y] - boardState[x + 1][y]) == 1 && Math.abs(boardState[x + 2][y] - boardState[x][y]) != 1 && boardState[x + 1][y] != 0 && boardState[x + 1][y] != 3 && boardState[x + 2][y] != 0) {
                System.out.println("Piece " + boardState[x + 1][y] + " captured from (" + (x + 1) + "," + y + ").");
                boardState[x + 1][y] = 0;
                return true;
            }
        } else if (direction == "up") {
            if (x - 2 < 0) {
                return false;
            } else if (Math.abs(boardState[x][y] - boardState[x - 1][y]) == 1 && Math.abs(boardState[x - 2][y] - boardState[x][y]) != 1 && boardState[x - 1][y] != 0 && boardState[x - 1][y] != 3 && boardState[x - 2][y] != 0) {
                System.out.println("Piece " + boardState[x - 1][y] + " captured from (" + (x - 1) + "," + y + ").");
                boardState[x - 1][y] = 0;
                return true;
            }
        }
        return false;
    }

    //This method checks if the king has been captured.
    public boolean checkKingCapture(int x, int y) {
        //The king can only be captured if the most recent move was by an attacker.
        if (boardState[x][y] != 2) {
            return false;
        }
        //Determine if the most recent move was adjacent to the king.
        //If so, store the location of the king. Should be faster than iterating over the board.
        int kingX = -1;
        int kingY = -1;
        if (x != 0 && boardState[x - 1][y] == 3) {
            kingX = x - 1;
            kingY = y;
        } else if (x != size - 1 && boardState[x + 1][y] == 3) {
            kingX = x + 1;
            kingY = y;
        } else if (y != 0 && boardState[x][y - 1] == 3) {
            kingX = x;
            kingY = y - 1;
        } else if (y != size - 1 && boardState[x][y + 1] == 3) {
            kingX = x;
            kingY = y + 1;
        } else {
            return false;
        }

        //The most recent move was by an attacker and the atacker was moved adjacent to the king.
        //Check to see if the king is surrounded.
        if (kingX != 0) {
            if (boardState[kingX - 1][kingY] == 0) {
                return false;
            }
        }
        if (kingX != size - 1) {
            if (boardState[kingX + 1][kingY] == 0) {
                return false;
            }
        }
        if (kingY != 0) {
            if (boardState[kingX][kingY - 1] == 0) {
                return false;
            }
        }
        if (kingY != size - 1) {
            if (boardState[kingX][kingY + 1] == 0) {
                return false;
            }
        }
        //The King has been captured.
        return true;
    }

    //Getters and Setters.
    //Set and get the size of the board.
    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    //Set and get the size of a tile.
    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getSelectedTileX() {
        return this.selectedTileX;
    }

    public int getSelectedTileY() {
        return this.selectedTileY;
    }

    public int getPieceType(int i, int j) {
        return boardState[i][j];
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(boardState[i][j]);
            }
            System.out.println();
        }
    }

    //Randomly pick an enemy piece and move it.
    public List moveAttacker() {
        //This will be useful for AI.
        List<List<Integer>> enemyPositions = new ArrayList<List<Integer>>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardState[i][j] == 2) {
                    List<Integer> t = Arrays.asList(i, j);
                    enemyPositions.add(t);
                }
            }
        }

        List<Integer> returnCoords = Arrays.asList(0, 0, 0, 0);
        // Pick a random attacker. 
        while (enemyPositions.size() > 0) {
            Random rand = new Random();
            int index = rand.nextInt(enemyPositions.size());
            List<Integer> coords = enemyPositions.get(index);
            int i = coords.get(0);
            int j = coords.get(1);
            returnCoords.set(0, i);
            returnCoords.set(1, j);

            boolean movableUp = (i > 0 && boardState[i - 1][j] == 0);
            boolean movableDown = (i < size - 1 && boardState[i + 1][j] == 0);
            boolean movableRight = (j < size - 1 && boardState[i][j + 1] == 0);
            boolean movableLeft = (j > 0 && boardState[i][j - 1] == 0);
            boolean movable = movableUp || movableDown || movableRight || movableLeft;

            if (!movable) {
                enemyPositions.remove(coords);
            } 
            else {
                //Choose a direction to move the piece.
                ArrayList<Integer> directions = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
                while (directions.size() > 0) {
                    Random r = new Random();
                    int dIndex = rand.nextInt(directions.size());
                    int dChoice = directions.get(dIndex);

                    if (dChoice == 0 && movableLeft) {
                        returnCoords.set(2, i);
                        returnCoords.set(3, j - 1);
                        return returnCoords;
                    }
                    if (dChoice == 1 && movableRight) {
                        returnCoords.set(2, i);
                        returnCoords.set(3, j + 1);
                        return returnCoords;
                    }
                    if (dChoice == 2 && movableDown) {
                        returnCoords.set(2, i + 1);
                        returnCoords.set(3, j);
                        return returnCoords;
                    }
                    if (dChoice == 3 && movableUp) {
                        returnCoords.set(2, i - 1);
                        returnCoords.set(3, j);
                        return returnCoords;
                    }
                    
                    directions.remove(dIndex);                    
                }
            }

        }
        
        returnCoords.set(0, 0);
        returnCoords.set(1, 0);
        returnCoords.set(2, 0);
        returnCoords.set(3, 0);
        return returnCoords;
    }

    public void tests() {

    }
}
