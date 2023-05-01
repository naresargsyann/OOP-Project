public class Pawn extends Pieces {
    // move;
    public Pawn(Cell cell, char name, char color) {
        super(cell, name, color);
    }

    protected void move(int destinationX, int destinationY){
        this.getCurrentCell().setRow(destinationX);
        this.getCurrentCell().setCol(destinationY);
    }
}
