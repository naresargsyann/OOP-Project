import java.util.*;

public class Game {
    private Cell[][] board = new Cell[8][8];
    private ArrayList<Piece> white = new ArrayList<>();
    private ArrayList<Piece> black = new ArrayList<>();
    protected ArrayList<String> moves = new ArrayList<>();
    public final char WHITECOLOR = 'W';
    public final char BLACKCOLOR = 'B';

    public Game() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = new Cell(row, col, BLACKCOLOR);
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        board[row][col].setColor(WHITECOLOR);
                    } else {
                        board[row][col].setColor(BLACKCOLOR);
                    }
                } else {
                    if (col % 2 == 0) {
                        board[row][col].setColor(BLACKCOLOR);
                    } else {
                        board[row][col].setColor(WHITECOLOR);
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

    protected void initialise() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].getColor() == BLACKCOLOR) {
                    Pawn pawn = new Pawn(board[row][col], 'P', BLACKCOLOR);
                    black.add(pawn);
                }
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col].getColor() == BLACKCOLOR) {
                    Pawn pawn = new Pawn(board[row][col], 'P', WHITECOLOR);
                    white.add(pawn);
                }
            }
        }
    }

    private String Converter(int StartX, int StartY, int destinationX, int DestinationY) {
        return StartX + "" + StartY + "" + destinationX + "" + DestinationY;
    }

    protected boolean isCaptureAvailableForPiece(boolean player, Piece piece) {
        int currRow = piece.getCurrentCell().getRow();
        int currCol = piece.getCurrentCell().getCol();
        int rowCoefficients = player ? -1 : 1;
        int[] colCoefficients = { -1, 1 };
        boolean toReturn = false;

        for (int colCoefficient : colCoefficients) {
            int destinationRow = currRow + 2 * rowCoefficients;
            int destinationCol = currCol + 2 * colCoefficient;
            int captureRow = currRow + rowCoefficients;
            int captureCol = currCol + colCoefficient;

            if (destinationRow >= 0 && destinationRow <= 7 && destinationCol >= 0 && destinationCol <= 7) {
                if (isEmpty(captureRow, captureCol) != null && isEmpty(destinationRow, destinationCol) == null) {
                    char destinationColor = player ? BLACKCOLOR : WHITECOLOR;
                    if (isEmpty(captureRow, captureCol).getColor() == destinationColor) {
                        moves.add(Converter(currRow, currCol, destinationRow, destinationCol));
                        toReturn = true;
                    }
                }
            }
        }

        return toReturn;
    }

    protected boolean isCaptureAvailable(boolean player) {
        boolean toReturn = false;
        if (player) {
            for (int index = 0; index < white.size(); index++) {
                if (isCaptureAvailableForPiece(player, white.get(index))) {
                    toReturn = true;
                }
            }
        } else {
            for (int index = 0; index < black.size(); index++) {
                if (isCaptureAvailableForPiece(player, black.get(index))) {
                    toReturn = true;
                }
            }
        }
        return toReturn;
    }

    protected boolean isMoveValid(int startX, int startY, int destinationX, int destinationY, boolean player) {
        if (destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7)
            return false;
        if (isEmpty(destinationX, destinationY) != null) {
            return false;
        }
        if (board[destinationX][destinationY].getColor() == WHITECOLOR) {
            return false;
        }
        if (player) {
            if (isEmpty(startX, startY) != null) {
                if (isEmpty(startX, startY).getColor() != WHITECOLOR)
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
                if (isEmpty(startX, startY).getColor() != BLACKCOLOR)
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

    protected Piece isEmpty(int x, int y) {
        for (int index = 0; index < white.size(); index++) {
            if (white.get(index).getCurrentCell().equals(x, y)) {
                return white.get(index);
            }
        }
        for (int index = 0; index < black.size(); index++) {
            if (black.get(index).getCurrentCell().equals(x, y)) {
                return black.get(index);
            }
        }
        return null;
    }

    protected void capture(int captureCoordinateX, int captureCoordinateY, boolean player) {
        if (player) {
            white.remove(isEmpty(captureCoordinateX, captureCoordinateY));
        } else {
            black.remove(isEmpty(captureCoordinateX, captureCoordinateY));
        }
    }

    protected boolean isGameOver(boolean player) {
        if (!isCaptureAvailable(player)) {
            if (player) {
                for (int index = 0; index < white.size(); index++) {
                    if (isMoveValid(white.get(index).getCurrentCell().getRow(),
                            white.get(index).getCurrentCell().getCol(),
                            white.get(index).getCurrentCell().getRow() - 1,
                            white.get(index).getCurrentCell().getCol() - 1,
                            player)
                            || isMoveValid(white.get(index).getCurrentCell().getRow(),
                                    white.get(index).getCurrentCell().getCol(),
                                    white.get(index).getCurrentCell().getRow() - 1,
                                    white.get(index).getCurrentCell().getCol() + 1, player)) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int index = 0; index < black.size(); index++) {
                    if (isMoveValid(black.get(index).getCurrentCell().getRow(),
                            black.get(index).getCurrentCell().getCol(),
                            black.get(index).getCurrentCell().getRow() + 1,
                            black.get(index).getCurrentCell().getCol() - 1,
                            player)
                            || isMoveValid(black.get(index).getCurrentCell().getRow(),
                                    black.get(index).getCurrentCell().getCol(),
                                    black.get(index).getCurrentCell().getRow() + 1,
                                    black.get(index).getCurrentCell().getCol() + 1, player)) {
                        return false;
                    }
                }
                return true;
            }
        }
        moves.clear(); // added this line as isCaptureAvailable is being called twice (from isGameOver
                       // and from the play)
        return false;// when capture is available, the game is not over for sure
    }
}