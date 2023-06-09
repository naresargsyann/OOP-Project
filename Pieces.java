public abstract class Pieces {
    private Cell currentCell;
    private char name;
    private char color;

    public Pieces(){
        currentCell = new Cell();
        name = 'P';
        color = 'B';
    }

    public Pieces(Cell currentCell, char name, char color){
        this.currentCell = currentCell;
        this.name = name;
        this.color = color;
    }

    public Cell getCurrentCell(){
        return currentCell;
    }

    public char getName(){
        return name;
    }

    public char getColor(){
        return color;
    }

    public void setCurrentCell(Cell currentCell){
        this.currentCell = currentCell;
    }

    public void setName(char name){
        this.name = name;
    }

    public void setColor(char color){
        this.color = color;
    }
    protected abstract void move();
}
