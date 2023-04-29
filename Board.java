import java.util.ArrayList;

public class Board {

    private Cell[][] board = new Cell[8][8];
    private ArrayList<Pieces> white;
    private ArrayList<Pieces> black;

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j, 'B');
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        board[i][j].setColor('W');
                    } else {
                        board[i][j].setColor('B');
                    }
                } else {
                    if (j % 2 == 0) {
                        board[i][j].setColor('B');
                    } else {
                        board[i][j].setColor('W');
                    }
                }
            }
        }
    }

    public final String toString() {
        final char WHITE = 'W';
        final char BLACK = 'B';
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++)
                if (board[row][col].getColor() == 'W')
                    result.append(WHITE);
                else
                    result.append(BLACK);
            result.append("\n");
        }
        return result.toString();
    }

    public void printBoard() {
        System.out.println(toString());
    }

    protected void initialise() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getColor() == 'B') {
                    Pawn pawn = new Pawn(board[i][j], 'P', 'W');
                    white.add(pawn);
                }
            }
        }
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getColor() == 'B') {
                    Pawn pawn = new Pawn(board[i][j], 'P', 'B');
                    black.add(pawn);
                }
            }
        }
    }

    public boolean isCaptureAvailable(boolean player) {
        if (player) {
            for (int i = 0; i < white.size(); i++) {
                int currRow = white.get(i).getCurrentCell().getRow();
                int currCol = white.get(i).getCurrentCell().getCol()
                if (currRow - 1 >= 0) {
                    if (currCol - 1 >=0){
                        if(isEmpty(currRow - 1, currCol - 1) != null){
                            if(isEmpty(currRow - 1, currCol - 1).getColor() == 'B'){
                                if (currRow - 2 >= 0 && currCol - 2 >= 0) {
                                    if(isEmpty(currRow - 2, currCol - 2) == null){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    if (currCol + 1 <= 7 ){
                        if(isEmpty(currRow - 1, currCol + 1) != null){
                            if(isEmpty(currRow - 1, currCol + 1).getColor() == 'B'){
                                if (currRow - 2 >= 0 && currCol + 2 <= 7) {
                                    if(isEmpty(currRow - 2, currCol + 2) == null){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < black.size(); i++) {
                int currRow = black.get(i).getCurrentCell().getRow();
                int currCol = black.get(i).getCurrentCell().getCol()
                if (currRow + 1 <=7 ) {
                    if (currCol + 1 <= 7){
                        if(isEmpty(currRow + 1, currCol + 1) != null){
                            if(isEmpty(currRow + 1, currCol + 1).getColor() == 'W'){
                                if (currRow + 2 <= 7 && currCol + 2 <= 7) {
                                    if(isEmpty(currRow + 2, currCol + 2) == null){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    if (currCol - 1 >= 0 ){
                        if(isEmpty(currRow + 1, currCol - 1) != null){
                            if(isEmpty(currRow + 1, currCol - 1).getColor() == 'W'){
                                if (currRow + 2 <= 7 && currCol - 2 >= 0) {
                                    if(isEmpty(currRow + 2, currCol - 2) == null){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isMoveValid(Cell start, Cell destination, boolean player){
        if(player){
            if(isEmpty(start.getRow(), start.getCol()) != null){
                if(isEmpty(start.getRow(), start.getCol()).getColor()!='W')
                return false;
            }
            else 
                return false;
            if(destination.getRow() < start.getRow()){
                return false;
            }

        }
        else{
            if(isEmpty(start.getRow(), start.getCol()) != null){
                if(isEmpty(start.getRow(), start.getCol()).getColor()!='B')
                return false;
            }
            else 
                return false;
            if(destination.getRow() > start.getRow()){
                return false;
            }
        }

        if(isEmpty(destination.getRow(), destination.getCol()) != null){
            return false;
        }
        if(destination.getColor() == 'W'){
            return false;
        }

    }

    public Piece isEmpty(int x, int y) {
        for (int i = 0; i < white.size(); i++) {
            if (white.get(i).getCurrentCell().equals(x, y)) {
                return white.get(i);
            }
        }
        for (int i = 0; i < black.size(); i++) {
            if (black.get(i).getCurrentCell().equals(x, y)) {
                return black.get(i);
            }
        }
        return null;
    }   

    public static void main(String[] args) {
        Board newBoard = new Board();
        newBoard.printBoard();
    }
}
