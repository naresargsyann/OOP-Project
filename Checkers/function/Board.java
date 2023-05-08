import java.util.*;

public class Board {
    private Cell[][] board = new Cell[8][8];
    private ArrayList<Piece> white = new ArrayList<>();
    private ArrayList<Piece> black = new ArrayList<>();
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
        this.initialise();
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

    private void printBoard() {
        System.out.println(toString());
    }

    private void initialise() {
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

    private String Converter(int StartX, int StartY, int destinationX, int DestinationY) {
        return StartX + "" + StartY + "" + destinationX + "" + DestinationY;
    }

    private boolean isCaptureAvailableForPiece(boolean player, Piece piece) {
        int currRow = piece.getCurrentCell().getRow();
        int currCol = piece.getCurrentCell().getCol();
        boolean toReturn = false;

        int rowModifier = player ? -1 : 1;
        int[] colModifiers = { -1, 1 };

        for (int colModifier : colModifiers) {
            int targetRow = currRow + 2 * rowModifier;
            int targetCol = currCol + 2 * colModifier;
            int captureRow = currRow + rowModifier;
            int captureCol = currCol + colModifier;

            if (targetRow >= 0 && targetRow <= 7 && targetCol >= 0 && targetCol <= 7) {
                if (isEmpty(captureRow, captureCol) != null && isEmpty(targetRow, targetCol) == null) {
                    char targetColor = player ? 'B' : 'W';
                    if (isEmpty(captureRow, captureCol).getColor() == targetColor) {
                        moves.add(Converter(currRow, currCol, targetRow, targetCol));
                        toReturn = true;
                    }
                }
            }
        }

        return toReturn;
    }

    private boolean isCaptureAvailable(boolean player) {
        boolean ToReturn = false;
        if (player) {
            for (int i = 0; i < white.size(); i++) {
                if (isCaptureAvailableForPiece(player, white.get(i))) {
                    ToReturn = true;
                }
            }
        } else {
            for (int i = 0; i < black.size(); i++) {
                if (isCaptureAvailableForPiece(player, black.get(i))) {
                    ToReturn = true;
                }
            }
        }
        return ToReturn;
    }

    private boolean isMoveValid(int startX, int startY, int destinationX, int destinationY, boolean player) {
        if (destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7)
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
            if (destinationX != startX - 1) {
                return false;
            }
            if (destinationY != startY + 1 && destinationY != startY - 1) { // || replaced with &&
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
            if (destinationX != startX + 1) {
                return false;
            }
            if (destinationY != startY + 1 && destinationY != startY - 1) {// || replaced with &&
                return false;
            }
        }
        return true;
    }

    private String getInput() {
        Scanner sc = new Scanner(System.in);
        int startX = sc.nextInt();
        int startY = sc.nextInt();
        int destinationX = sc.nextInt();
        int destinationY = sc.nextInt();
        return Converter(startX, startY, destinationX, destinationY);
    }

    public void play(boolean player) {
        String destination;
        while (!isGameOver(player)) {
            printBoard();
            if (isCaptureAvailable(player)) {
                System.out.println(moves.toString());
                destination = getInput();
                for (int i = 0; i < moves.size(); i++) {
                    if (moves.get(i).equals(destination)) {
                        isEmpty((destination.charAt(0) - '0'), (destination.charAt(1)) - '0')
                                .move((destination.charAt(2) - '0'), (destination.charAt(3) - '0'));
                        capture(((destination.charAt(0) - '0') + (destination.charAt(2) - '0')) / 2,
                                (((destination.charAt(1)) - '0') + (destination.charAt(3) - '0')) / 2, !player);
                        player = !player;
                    }
                }
            } else {
                destination = getInput();
                if (isMoveValid((destination.charAt(0) - '0'), (destination.charAt(1) - '0'),
                        (destination.charAt(2) - '0'), (destination.charAt(3) - '0'), player)) {
                    isEmpty((destination.charAt(0) - '0'), (destination.charAt(1) - '0'))
                            .move((destination.charAt(2) - '0'), (destination.charAt(3) - '0'));
                    player = !player;
                }
            }
            moves.clear();
        }
        if (player) {
            System.out.println("The black won!");
        } else {
            System.out.println("The white won!");
        }
    }

    private Piece isEmpty(int x, int y) {
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

    private void capture(int captureCoordinateX, int captureCoordinateY, boolean player) {
        if (player) {
            white.remove(isEmpty(captureCoordinateX, captureCoordinateY));
        } else {
            black.remove(isEmpty(captureCoordinateX, captureCoordinateY));
        }
    }

    private boolean isGameOver(boolean player) {
        if (!isCaptureAvailable(player)) {
            if (player) {
                for (int i = 0; i < white.size(); i++) {
                    if (isMoveValid(white.get(i).getCurrentCell().getRow(), white.get(i).getCurrentCell().getCol(),
                            white.get(i).getCurrentCell().getRow() - 1, white.get(i).getCurrentCell().getCol() - 1,
                            player)
                            || isMoveValid(white.get(i).getCurrentCell().getRow(),
                                    white.get(i).getCurrentCell().getCol(), white.get(i).getCurrentCell().getRow() - 1,
                                    white.get(i).getCurrentCell().getCol() + 1, player)) {
                        return false;
                    }
                }
            } else {
                for (int i = 0; i < black.size(); i++) {
                    if (isMoveValid(black.get(i).getCurrentCell().getRow(), black.get(i).getCurrentCell().getCol(),
                            black.get(i).getCurrentCell().getRow() + 1, black.get(i).getCurrentCell().getCol() - 1,
                            player)
                            || isMoveValid(black.get(i).getCurrentCell().getRow(),
                                    black.get(i).getCurrentCell().getCol(), black.get(i).getCurrentCell().getRow() + 1,
                                    black.get(i).getCurrentCell().getCol() + 1, player)) {
                        return false;
                    }
                }
            }
        }
        moves.clear(); // added this line as isCaptureAvailable is being called twice (from isGameOver
                       // and from the play)
        return false;// when capture is available, the game is not over for sure
    }

    public static void main(String[] args) {
        Board newBoard = new Board();
        newBoard.play(true);
    }
}
