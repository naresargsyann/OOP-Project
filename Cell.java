
public class Cell {
    private int row;
    private int col;
    private char color;

    public Cell(int row, int col, char color) {
        this.row = row;
        this.col = col;
        this.color = color;

    }

    public Cell() {
        this.row = 0;
        this.col = 0;
        this.color = 'B';

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getColor() {
        return color;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public boolean equals(int x, int y){
       if(this.row == x && this.col = y){
        return true;
       }
       return false;
       
    }
}