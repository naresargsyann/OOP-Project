import java.util.*;

public class Board {
    private Cell[][] board = new Cell[8][8];
    private ArrayList<Pieces> white = new ArrayList<>();
    private ArrayList<Pieces> black = new ArrayList<>();
    private ArrayList<String> moves = new ArrayList<>();

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
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++)
                if (isEmpty(row, col) == null)
                    result.append(" ");
                else
                    result.append(isEmpty(row, col).getColor());
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
                    Pawn pawn = new Pawn(board[i][j], 'P', 'B');
                    black.add(pawn);
                }
            }
        }
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getColor() == 'B') {
                    Pawn pawn = new Pawn(board[i][j], 'P', 'W');
                    white.add(pawn);
                }
            }
        }
    }

    private String Converter(int StartX, int StartY, int destinationX, int DestinationY){
        return StartX + "" + StartY + "" + destinationX + "" + DestinationY;
    }

    // private void Printer(int move){
    //     System.out.println(move);
    // }

    public boolean isCaptureAvailableForPiece(boolean player, int index) {
        boolean ToReturn = false;
        if (player) {
            int currRow = white.get(index).getCurrentCell().getRow();
            int currCol = white.get(index).getCurrentCell().getCol();
            if (currRow - 1 >= 0) {
                if (currCol - 1 >= 0) {
                    if (isEmpty(currRow - 1, currCol - 1) != null) {
                        if (isEmpty(currRow - 1, currCol - 1).getColor() == 'B') {
                            if (currRow - 2 >= 0 && currCol - 2 >= 0) {
                                if (isEmpty(currRow - 2, currCol - 2) == null) {
                                    moves.add(Converter(currRow, currCol, currRow - 2, currCol - 2));
                                    ToReturn = true;
                                }
                            }
                        }
                    }
                }
                if (currCol + 1 <= 7) {
                    if (isEmpty(currRow - 1, currCol + 1) != null) {
                        if (isEmpty(currRow - 1, currCol + 1).getColor() == 'B') {
                            if (currRow - 2 >= 0 && currCol + 2 <= 7) {
                                if (isEmpty(currRow - 2, currCol + 2) == null) {
                                    moves.add(Converter(currRow, currCol, currRow - 2, currCol + 2));
                                    ToReturn = true;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            int currRow = black.get(index).getCurrentCell().getRow();
            int currCol = black.get(index).getCurrentCell().getCol();
            if (currRow + 1 <= 7) {
                if (currCol + 1 <= 7) {
                    if (isEmpty(currRow + 1, currCol + 1) != null) {
                        if (isEmpty(currRow + 1, currCol + 1).getColor() == 'W') {
                            if (currRow + 2 <= 7 && currCol + 2 <= 7) {
                                if (isEmpty(currRow + 2, currCol + 2) == null) {
                                    moves.add(Converter(currRow, currCol, currRow + 2, currCol + 2));
                                    ToReturn = true;
                                }
                            }
                        }
                    }
                }
                if (currCol - 1 >= 0) {
                    if (isEmpty(currRow + 1, currCol - 1) != null) {
                        if (isEmpty(currRow + 1, currCol - 1).getColor() == 'W') {
                            if (currRow + 2 <= 7 && currCol - 2 >= 0) {
                                if (isEmpty(currRow + 2, currCol - 2) == null) {
                                    moves.add(Converter(currRow, currCol, currRow + 2, currCol - 2));
                                    ToReturn = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ToReturn;
    }
    
    public boolean isCaptureAvailable(boolean player) {
        boolean ToReturn = false;
        if (player) {
            for (int i = 0; i < white.size(); i++) {
                if(isCaptureAvailableForPiece(player, i)){
                    ToReturn = true;
                }
            } 
        } else {
            for (int i = 0; i < black.size(); i++) {
                if(isCaptureAvailableForPiece(player, i)){
                    ToReturn = true;
                }
            }
        }
        return ToReturn;
    }

    public boolean isMoveValid(int startX, int startY, int destinationX, int destinationY, boolean player) {
        if(destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7)
            return false;
        if (isEmpty(destinationX, destinationY) != null) {
            return false;
        }
        if (board[destinationX][destinationY].getColor() == 'W') {
            return false;
        }
        if (player) {
            if (isEmpty(startX, startY) != null) {
                if (isEmpty(startX, startY).getColor() != 'W')
                    return false;
            } else
                return false;
            if (destinationX > startX) {
                return false;
            }
            if(destinationX != startX - 1){
                return false;
            }
            if(destinationY != startY + 1 && destinationY != startY - 1){ // || replaced with &&
                return false;
            }

        } else {
            if (isEmpty(startX, startY) != null) {
                if (isEmpty(startX, startY).getColor() != 'B')
                    return false;
            } else
                return false;
            if (destinationX < startX) {
                return false;
            }
            if(destinationX != startX + 1){
                return false;
            }
            if(destinationY != startY + 1 && destinationY != startY - 1){// || replaced with &&
                return false;
            }
        }
        return true;
    }

    private String getInput(){
        Scanner sc = new Scanner(System.in);
        int startX = sc.nextInt();
        int startY = sc.nextInt();
        int destinationX = sc.nextInt();
        int destinationY = sc.nextInt();
        return Converter(startX, startY, destinationX, destinationY);
    }

    public void play(boolean player){
        String destination;
        while(!isGameOver(player)){
            printBoard();
            if(isCaptureAvailable(player)){
                System.out.println(moves.toString());
                destination = getInput();
                for(int i = 0; i < moves.size(); i++){
                    if(moves.get(i).equals(destination)){
                        isEmpty((destination.charAt(0)-'0'), (destination.charAt(1))-'0').move((destination.charAt(2)-'0'), (destination.charAt(3)-'0'));
                        capture(((destination.charAt(0)-'0')+(destination.charAt(2)-'0'))/2, (((destination.charAt(1))-'0')+(destination.charAt(3)-'0'))/2, !player);
                        player = !player;
                    }
                }
            }
            else{
                destination = getInput();
                if(isMoveValid((destination.charAt(0)-'0'), (destination.charAt(1)-'0'), (destination.charAt(2)-'0'), (destination.charAt(3)-'0'), player)){
                    isEmpty((destination.charAt(0)-'0'), (destination.charAt(1)-'0')).move((destination.charAt(2)-'0'), (destination.charAt(3)-'0'));
                    player = !player;
                }
            }
            moves.clear();
        }
        if(player){
            System.out.println("The black won!");
        } else{
            System.out.println("The white won!");
        }
    }

    public Pieces isEmpty(int x, int y) {
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

    private void capture(int captureCoordinateX, int captureCoordinateY, boolean player){
        if(player){
            white.remove(isEmpty(captureCoordinateX, captureCoordinateY));
        } else{
            black.remove(isEmpty(captureCoordinateX, captureCoordinateY));
        }
    }

    private boolean isGameOver(boolean player){
        if(!isCaptureAvailable(player)){
            if(player){
                for(int i = 0; i < white.size(); i++){
                    if(isMoveValid(white.get(i).getCurrentCell().getRow(), white.get(i).getCurrentCell().getCol(), white.get(i).getCurrentCell().getRow() - 1, white.get(i).getCurrentCell().getCol() - 1, player)
                        || isMoveValid(white.get(i).getCurrentCell().getRow(), white.get(i).getCurrentCell().getCol(), white.get(i).getCurrentCell().getRow() - 1, white.get(i).getCurrentCell().getCol() + 1, player)){
                        return false;
                    }
                }
            }
            else{
                for(int i = 0; i < black.size(); i++){
                    if(isMoveValid(black.get(i).getCurrentCell().getRow(), black.get(i).getCurrentCell().getCol(), black.get(i).getCurrentCell().getRow() + 1, black.get(i).getCurrentCell().getCol() - 1, player)
                        || isMoveValid(black.get(i).getCurrentCell().getRow(), black.get(i).getCurrentCell().getCol(), black.get(i).getCurrentCell().getRow() + 1, black.get(i).getCurrentCell().getCol() + 1, player)){
                        return false;
                    }
                }
            }
        }
        moves.clear(); // added this line as isCaptureAvailable is being called twice (from isGameOver and from the play)
        return false;// when capture is available, the game is not over for sure
    }

    public static void main(String[] args) {
        Board newBoard = new Board();
        newBoard.initialise();
        //newBoard.printBoard();
        newBoard.play(true);
    }
}
