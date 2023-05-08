
class Cell {
    private int row;
    private int col;
    private char color;

    protected Cell(int row, int col, char color) {
        this.row = row;
        this.col = col;
        this.color = color;

    }

    protected Cell() {
        this.row = 0;
        this.col = 0;
        this.color = 'B';

    }

    protected int getRow() {
        return row;
    }

    protected int getCol() {
        return col;
    }

    protected char getColor() {
        return color;
    }

    protected void setRow(int row) {
        this.row = row;
    }

    protected void setCol(int col) {
        this.col = col;
    }

    protected void setColor(char color) {
        this.color = color;
    }

    public boolean equals(int x, int y) {
        if (this.row == x && this.col == y) {
            return true;
        }
        return false;

    }
}