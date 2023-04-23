import java.util.ArrayList;

public class Board {
    private Cell[][] board = new Cell[8][8];
    private ArrayList<Pieces> white;
    private ArrayList<Pieces> black;

    public Board(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = new Cell(i, j, 'B');
                if(i % 2 == 0){
                    if(j % 2 == 0){
                        board[i][j].setColor('W');
                    }
                    else
                        board[i][j].setColor('B');
                } else {
                    if(j % 2 == 0){
                        board[i][j].setColor('B');
                    }
                    else
                        board[i][j].setColor('W');
                }
            } 
        }
    }

    public String toString() {
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

    public void printBoard(){
        System.out.print(toString());
    }

    protected void initialise(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getColor() == 'B'){
                    Pawn pawn = new Pawn(board[i][j], 'P', 'W');
                    white.add(pawn);
                }
            }
        }
        for(int i = 5; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getColor() == 'B'){
                    Pawn pawn = new Pawn(board[i][j], 'P', 'B');
                    black.add(pawn);
                }
            }
        }
    }

    public static void main(String[] args){
        Board b = new Board();
        b.printBoard();
    }
}
