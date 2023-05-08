public abstract class Piece {
    private Cell currentCell;
    private char name;
    private char color;

    protected Pieces() {
        this.currentCell = new Cell();
        this.name = 'P';
        color = 'B';

    }

    protected Pieces(Cell currentCell, char name, char color) {
        this.currentCell = currentCell;
        this.name = name;
        this.color = color;
    }

    protected Cell getCurrentCell() {
        return currentCell;
    }

    protected char getName() {
        return name;
    }

    protected char getColor() {
        return color;
    }

    protected void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    protected void setName(char name) {
        this.name = name;
    }

    protected void setColor(char color) {
        this.color = color;
    }

    protected abstract void move(int x, int y);

}
