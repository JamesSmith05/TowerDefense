package AI;

public class Node {
    Node parent;
    public int col;
    public int row;
    int gCost;
    int fCost;
    int hCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node (int col, int row){ //object class for the A* pathfinding
        this.col = col;
        this.row = row;
    }
}
