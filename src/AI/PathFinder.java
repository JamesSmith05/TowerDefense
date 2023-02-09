package AI;

import gameFolder.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>(); //list of available nodes
    public ArrayList<Node> pathList = new ArrayList<>(); //store the final paths to be read backwards
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    //assign node to each tile
    public void instantiateNodes() {

        node = new Node[gp.maxScreenCol][gp.maxScreenRow]; //instantiate 2d array for setting up node map

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){ // for loop for adding node to each tile of the map
            node[col][row] = new Node(col,row);

            col++;
            if (col == gp.maxScreenCol){
                col =0;
                row++;
            }
        }
    }

    //set all nodes to default
    public void resetNodes(){

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){

            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == gp.maxScreenCol){
                col =0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    //set the starting point for the pathfinding
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){

        resetNodes();

        //set start and end
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = gp.tileM.mapTileNum[col][row];
            if(gp.tileM.tile[tileNum].enemyCollision){ //sets it so that monsters that use the pathfinding cannot travel over anything but path
                node[col][row].solid = true;
            }
            // set cost
            getCost(node[col][row]);

            col++;
            if (col == gp.maxScreenCol){
                col =0;
                row++;
            }
        }
    }

    //calculate the cost of the node
    public void getCost(Node node){

        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.col - startNode.col);
        node.gCost = xDistance+yDistance;

        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.col - goalNode.col);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost+node.hCost;
    }

    //pathfind
    public boolean search(){

        while (!goalReached && step < 500){ // step <500 means that if there is no possible path the code doesn't crash and run forever
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            //opens the nodes in the 4 cardinal directions if there are withing the bounds
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            if(col - 1 >= 0){
                openNode(node[col-1][row]);
            }
            if(row + 1 < gp.maxScreenRow){
                openNode(node[col][row+1]);
            }
            if(col + 1 < gp.maxScreenCol){
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0; //set default values
            int bestNodefCost = 999; //set default values

            for (int i = 0; i < openList.size(); i++) {

                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i; // if new node is closer to goal then replace the best node index
                    bestNodefCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) { //if the fCost is the same then check the gCost
                        bestNodeIndex = i; //if gCost better replace index
                    }
                }
            }
            if(openList.size() == 0){
                break;
            }
            currentNode = openList.get(bestNodeIndex); //set new current node to the closest found node
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    //set node to open
    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode; //set parent node to be used to track the path backwards
            openList.add(node);
        }
    }

    //map the path out
    public void trackThePath(){
        Node current = goalNode;
        while (current != startNode){

            pathList.add(0,current); //adds current node and
            current = current.parent; //regresses to its parent node essentially following the breadcrumb trail back to the start
        }
    }
}
