package hexaround.movement;

import hexaround.entity.Board;
import hexaround.entity.Creature;
import hexaround.entity.CreatureProperty;
import hexaround.entity.Player;
import hexaround.movement.Node;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Search {

    private LinkedList<Node> queue;
    private LinkedList<Node> visited;



    private LinkedList<Node> connectedQueue;
    private LinkedList<Node> connectedVisited;


    public Search() {
        this.queue = new LinkedList<Node>();
        this.visited = new LinkedList<Node>();

        this.connectedQueue = new LinkedList<Node>();
        this.connectedVisited = new LinkedList<Node>();
    }


    /**
     * Depending on the property of the creature gets the possible paths it can take to get to its goal location
     * @param board
     * @param node
     * @param creature
     * @param endNode
     * @return a LinkedList of possible paths a creature can take based on it's properties
     */
    public LinkedList<Node> getImmediateNodeNeighbors(Board board, Node node, Creature creature, Node endNode) {
        LinkedList<Node> list = new LinkedList<Node>();
        boolean node1add = false;
        boolean node2add = false;
        boolean node3add = false;
        boolean node4add = false;
        boolean node5add = false;
        boolean node6add = false;
        Node node1 = new Node(node.getNodeX(),node.getNodeY()+1);
        node1.addPath(node);
        Node node2 = new Node(node.getNodeX()+1,node.getNodeY());
        node2.addPath(node);
        Node node3 = new Node(node.getNodeX()-1,node.getNodeY());
        node3.addPath(node);
        Node node4 = new Node(node.getNodeX(),node.getNodeY()-1);
        node4.addPath(node);
        Node node5 = new Node(node.getNodeX()-1,node.getNodeY()+1);
        node5.addPath(node);
        Node node6 = new Node(node.getNodeX()+1,node.getNodeY()-1);
        node6.addPath(node);
        boolean walking=creature.propertyFound(CreatureProperty.WALKING);
        boolean flying = creature.propertyFound(CreatureProperty.FLYING);
        boolean kamikaze = creature.propertyFound(CreatureProperty.KAMIKAZE);

         if (flying==true) { //idk about this && kamikaze==false
             node1add=true;//don't need to be null for flying
            node2add=true;
            node3add=true;
            node4add=true;
            node5add=true;
            node6add=true;
        } else if (walking==true && kamikaze==true) {
            if (shareCommonEmptyHex(board,node,node1)&&(board.getCreatureAt(node1.getNodeX(),node1.getNodeY())==null || (node1.getNodeX()==endNode.getNodeX() && node1.getNodeY()==endNode.getNodeY()))) //seeing if location is empty which is needed for walking
                node1add=true;
            if (shareCommonEmptyHex(board,node,node2)&&(board.getCreatureAt(node2.getNodeX(),node2.getNodeY())==null || (node2.getNodeX()==endNode.getNodeX() && node2.getNodeY()==endNode.getNodeY())))
                node2add=true;
            if (shareCommonEmptyHex(board,node,node3)&&(board.getCreatureAt(node3.getNodeX(),node3.getNodeY())==null || (node3.getNodeX()==endNode.getNodeX() && node3.getNodeY()==endNode.getNodeY())))
                node3add=true;
            if (shareCommonEmptyHex(board,node,node4)&&(board.getCreatureAt(node4.getNodeX(),node4.getNodeY())==null || (node4.getNodeX()==endNode.getNodeX() && node4.getNodeY()==endNode.getNodeY())))
                node4add=true;
            if (shareCommonEmptyHex(board,node,node5)&& (board.getCreatureAt(node5.getNodeX(),node5.getNodeY())==null || (node5.getNodeX()==endNode.getNodeX() && node5.getNodeY()==endNode.getNodeY())))
                node5add=true;
            if (shareCommonEmptyHex(board,node,node6)&&(board.getCreatureAt(node6.getNodeX(),node6.getNodeY())==null || (node6.getNodeX()==endNode.getNodeX() && node6.getNodeY()==endNode.getNodeY())))
                node6add=true;
        } else if (walking==true && kamikaze==false){
             //for walking and kamikaze is false
             if (shareCommonEmptyHex(board,node,node1)&& board.getCreatureAt(node1.getNodeX(),node1.getNodeY())==null) //seeing if location is empty which is needed for walking
                 node1add=true;
             if (shareCommonEmptyHex(board,node,node2)&& board.getCreatureAt(node2.getNodeX(),node2.getNodeY())==null)
                 node2add=true;
             if (shareCommonEmptyHex(board,node,node3)&&board.getCreatureAt(node3.getNodeX(),node3.getNodeY())==null)
                 node3add=true;
             if (shareCommonEmptyHex(board,node,node4)&&board.getCreatureAt(node4.getNodeX(),node4.getNodeY())==null)
                 node4add=true;
             if (shareCommonEmptyHex(board,node,node5)&&board.getCreatureAt(node5.getNodeX(),node5.getNodeY())==null)
                 node5add=true;
             if (shareCommonEmptyHex(board,node,node6)&&board.getCreatureAt(node6.getNodeX(),node6.getNodeY())==null)
                 node6add=true;
         } else {//shouldn't get here for level 2
             node1add=true;//don't need to be null for flying
             node2add=true;
             node3add=true;
             node4add=true;
             node5add=true;
             node6add=true;
         }



        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getNodeY()==node1.getNodeY() &&queue.get(i).getNodeX()==node1.getNodeX())
                node1add=false;
            if (queue.get(i).getNodeY()==node2.getNodeY() &&queue.get(i).getNodeX()==node2.getNodeX())
                node2add=false;
            if (queue.get(i).getNodeY()==node3.getNodeY() &&queue.get(i).getNodeX()==node3.getNodeX())
                node3add=false;
            if (queue.get(i).getNodeY()==node4.getNodeY() &&queue.get(i).getNodeX()==node4.getNodeX())
                node4add=false;
            if (queue.get(i).getNodeY()==node5.getNodeY() &&queue.get(i).getNodeX()==node5.getNodeX())
                node5add=false;
            if (queue.get(i).getNodeY()==node6.getNodeY() &&queue.get(i).getNodeX()==node6.getNodeX())
                node6add=false;
        }
        for (int i = 0; i < visited.size(); i++) {
            if (visited.get(i).getNodeY()==node1.getNodeY() &&visited.get(i).getNodeX()==node1.getNodeX())
                node1add=false;
            if (visited.get(i).getNodeY()==node2.getNodeY() &&visited.get(i).getNodeX()==node2.getNodeX())
                node2add=false;
            if (visited.get(i).getNodeY()==node3.getNodeY() &&visited.get(i).getNodeX()==node3.getNodeX())
                node3add=false;
            if (visited.get(i).getNodeY()==node4.getNodeY() &&visited.get(i).getNodeX()==node4.getNodeX())
                node4add=false;
            if (visited.get(i).getNodeY()==node5.getNodeY() &&visited.get(i).getNodeX()==node5.getNodeX())
                node5add=false;
            if (visited.get(i).getNodeY()==node6.getNodeY() &&visited.get(i).getNodeX()==node6.getNodeX())
                node6add=false;
        }
        //it's connected and no creature is located on the step we are trying to take, it hasn't been looked through yet
        if (node1add==true) { //gotta check conntected each step tbh
            list.add(node1);
        }
        if (node2add==true ) {
            list.add(node2);
        }
        if (node3add==true) {
            list.add(node3);
        }
        if (node4add==true) {
            list.add(node4);
        }
        if (node5add==true) {
            list.add(node5);
        }
        //&& wholeConnectedness....
        //if (board.AnyCreatureNextDoor(node6.getNodeX(),node6.getNodeY())==false && found6==false && board.getCreatureAt(node6.getNodeX(),node6.getNodeY())==null) {
        if (node6add==true ) {
            list.add(node6);
        }
        return list;
    }

    /**
     * BFS method which determines the shortest path to the destination
     * @param board
     * @param creature
     * @param fromX
     * @param fromY
     * @param endX
     * @param endY
     * @return a list of nodes which is the path the creature should take
     */
    public List<Node> BFS2(Board board, Creature creature,int fromX,int fromY, int endX, int endY) {
        boolean kamikaze = creature.propertyFound(CreatureProperty.KAMIKAZE);
        if (kamikaze==false && board.getCreatureAt(endX,endY)!=null) {
            return null;//can't land there
        }

        Node startNode = new Node(fromX, fromY);
        Node endNode = new Node(endX, endY);
        startNode.addPath(null);
        queue.add(startNode);
        visited.add(startNode);


        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            // If the current node is the destination, reconstruct and return the path
            if (currentNode.getNodeX() == endX && currentNode.getNodeY() == endY) {
                return path(currentNode);
            }

            // Get neighbors of the current node
            List<Node> neighbors = getImmediateNodeNeighbors(board, currentNode, creature, endNode);

            // Visit each neighbor
            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        return null;
    }

    /**
     * Helper function which helps for giving the final path a creature should take
     * @param destination
     * @return a list of nodes
     */
    private List<Node> path(Node destination) {
        List<Node> path = new ArrayList<>();
        Node current = destination;

        // Traverse back from the destination to the starting node
        while (current != null) {
            path.add(current);
            current = current.getPrevious(); // Get the parent of the current node
        }

        Collections.reverse(path); // Reverse the path to get the correct order
        return path;
    }


    /**
     * Used to see at the end of a turn if the colony is still connected
     * @param board
     * @param oldX
     * @param oldY
     * @param newX
     * @param newY
     * @return a boolean value if the colony reamains connected at the end
     */
    public boolean wholeColonyConnected(Board board, int oldX, int oldY, int newX, int newY) {

        if (board.getCreatureAt(oldX,oldY).propertyFound(CreatureProperty.KAMIKAZE) && board.getCreatureAt(newX,newY)!=null) {
            return true;
        }

        int amountOnBoard = 0;
        LinkedList<Node> creaturesOnBoard = new LinkedList<Node>();
        int amountVisited = 0;
        for (Player player: board.getPlayers()) { //added for both players no longer just one player
            for (Creature creature : player.getCreatures()) {
                if (creature.getPlaced() == true) {
                    if (creature.getXlocation() == oldX && creature.getYlocation() == oldY) {
                        creaturesOnBoard.add(new Node(newX, newY));
                        amountOnBoard++;
                    } else {
                        creaturesOnBoard.add(new Node(creature.getXlocation(), creature.getYlocation()));
                        amountOnBoard++;
                    }
                }
            }
        }
        Node front = creaturesOnBoard.getFirst();
        connectedQueue.add(front);
        LinkedList<Node> paths = Connected(front,creaturesOnBoard);
        for (int i=0;i< paths.size(); i++) {
            connectedQueue.add(paths.get(i));
        }
        connectedVisited.add(front);
        connectedQueue.remove(front);


        while (connectedQueue.size()!=0) {//not so sure about any of this
            front = connectedQueue.getFirst();
            paths = Connected(front,creaturesOnBoard);
            for (int i=0;i< paths.size(); i++) {
                connectedQueue.add(paths.get(i));
            }
            connectedVisited.add(front);
            connectedQueue.remove(front);
        }
        amountVisited = connectedVisited.size();
        if (amountVisited==amountOnBoard) {
            connectedVisited = new LinkedList<Node>();
            connectedQueue = new LinkedList<Node>();
            return true;
        } else {
            connectedVisited = new LinkedList<Node>();
            connectedQueue = new LinkedList<Node>();
            return false; //i.e. not connected at end
        }
    }

    /**
     * Helper function which goes through all of the occupied neighbors of a node and returns them
     * @param startingFrom
     * @param list
     * @return a LinkedList<Node> of the occupied neighbors that haven't been searched through yet
     */
    public LinkedList<Node> Connected(Node startingFrom, LinkedList<Node> list) {

        Node node1 = new Node(startingFrom.getNodeX(),startingFrom.getNodeY()+1);
        Node node2 = new Node(startingFrom.getNodeX()+1,startingFrom.getNodeY());
        Node node3 = new Node(startingFrom.getNodeX()-1,startingFrom.getNodeY());
        Node node4 = new Node(startingFrom.getNodeX(),startingFrom.getNodeY()-1);
        Node node5 = new Node(startingFrom.getNodeX()-1,startingFrom.getNodeY()+1);
        Node node6 = new Node(startingFrom.getNodeX()+1,startingFrom.getNodeY()-1);
        boolean node1add = false;
        boolean node2add = false;
        boolean node3add = false;
        boolean node4add = false;
        boolean node5add = false;
        boolean node6add = false;
        LinkedList<Node> paths = new LinkedList<Node>();
        for (int i =0;i<list.size();i++) {
            //need to check to make sure it's not already in Visited or ConnectedQueue then we can add it to queue
            if (list.get(i).getNodeX()==node1.getNodeX() && list.get(i).getNodeY()==node1.getNodeY()) {
                node1add = true;
            } else if (list.get(i).getNodeX()==node2.getNodeX() && list.get(i).getNodeY()==node2.getNodeY()) {
                node2add = true;
            }else if (list.get(i).getNodeX()==node3.getNodeX() && list.get(i).getNodeY()==node3.getNodeY()) {
                node3add = true;
            }else if (list.get(i).getNodeX()==node4.getNodeX() && list.get(i).getNodeY()==node4.getNodeY()) {
                node4add = true;
            }else if (list.get(i).getNodeX()==node5.getNodeX() && list.get(i).getNodeY()==node5.getNodeY()) {
                node5add = true;
            }else if (list.get(i).getNodeX()==node6.getNodeX() && list.get(i).getNodeY()==node6.getNodeY()) {
                node6add = true;
            }
        }
        for (int i =0;i<connectedQueue.size();i++) {
            if (connectedQueue.get(i).getNodeX()==node1.getNodeX() && connectedQueue.get(i).getNodeY()==node1.getNodeY()) {
                node1add = false;
            } else if (connectedQueue.get(i).getNodeX()==node2.getNodeX() && connectedQueue.get(i).getNodeY()==node2.getNodeY()) {
                node2add = false;
            }else if (connectedQueue.get(i).getNodeX()==node3.getNodeX() && connectedQueue.get(i).getNodeY()==node3.getNodeY()) {
                node3add = false;
            }else if (connectedQueue.get(i).getNodeX()==node4.getNodeX() && connectedQueue.get(i).getNodeY()==node4.getNodeY()) {
                node4add = false;
            }else if (connectedQueue.get(i).getNodeX()==node5.getNodeX() && connectedQueue.get(i).getNodeY()==node5.getNodeY()) {
                node5add = false;
            }else if (connectedQueue.get(i).getNodeX()==node6.getNodeX() && connectedQueue.get(i).getNodeY()==node6.getNodeY()) {
                node6add = false;
            }
        }for (int i=0;i<connectedVisited.size();i++) {
            if (connectedVisited.get(i).getNodeX()==node1.getNodeX() && connectedVisited.get(i).getNodeY()==node1.getNodeY()) {
                node1add = false;
            } else if (connectedVisited.get(i).getNodeX()==node2.getNodeX() && connectedVisited.get(i).getNodeY()==node2.getNodeY()) {
                node2add = false;
            }else if (connectedVisited.get(i).getNodeX()==node3.getNodeX() && connectedVisited.get(i).getNodeY()==node3.getNodeY()) {
                node3add = false;
            }else if (connectedVisited.get(i).getNodeX()==node4.getNodeX() && connectedVisited.get(i).getNodeY()==node4.getNodeY()) {
                node4add = false;
            }else if (connectedVisited.get(i).getNodeX()==node5.getNodeX() && connectedVisited.get(i).getNodeY()==node5.getNodeY()) {
                node5add = false;
            }else if (connectedVisited.get(i).getNodeX()==node6.getNodeX() && connectedVisited.get(i).getNodeY()==node6.getNodeY()) {
                node6add = false;
            }
        }
        if (node1add==true) {
            paths.add(node1);
        }
        if (node2add==true) {
            paths.add(node2);
        }
        if (node3add==true) {
            paths.add(node3);
        }
        if (node4add==true) {
            paths.add(node4);
        }
        if (node5add==true) {
            paths.add(node5);
        }
        if (node6add==true) {
            paths.add(node6);
        }
        return paths;
    }

    /**
     * Method to handle jumping since BFS isn't needed
     * @param board
     * @param creature
     * @param fromX
     * @param fromY
     * @param endX
     * @param endY
     * @return a boolean if the jump can be made
     */
    public boolean jumpHandle(Board board, Creature creature, int fromX, int fromY, int endX, int endY) {
        if (fromX==endX) {
            return true;
        } else if (fromY==endY) {
            return true;
        }
        int absoluteValue = Math.abs(fromX-endX)+1;
        //if ((Math.abs(endX-fromX)+Math.abs(endY-fromY))==(Math.min(fromX,endX)+ Math.min(fromY,endY))) {
        if (Math.abs(endX-fromX)==Math.abs(endY-fromY)) {
            List<Node> list = BFS2(board,creature,fromX,fromY,endX,endY);
            if (list.size()==absoluteValue) {
                return true;
            }
           return false;
        }
        return false;
    }

    /**
     * is a Helper function for the BFS2's helper GetImmediateNodeNeighbors, this is specifically for walking draggability
     * @param board
     * @param startNode
     * @param pathNode
     * @return a boolean on whether two nodes share a common unoccupied hex
     */
    private boolean shareCommonEmptyHex(Board board, Node startNode, Node pathNode) {
        LinkedList<Node> startNodeNeighbors = new LinkedList<Node>();
        Node startnode1 = new Node(startNode.getNodeX(), startNode.getNodeY() + 1);
        Node startnode2 = new Node(startNode.getNodeX() + 1, startNode.getNodeY());
        Node startnode3 = new Node(startNode.getNodeX() - 1, startNode.getNodeY());
        Node startnode4 = new Node(startNode.getNodeX(), startNode.getNodeY() - 1);
        Node startnode5 = new Node(startNode.getNodeX() - 1, startNode.getNodeY() + 1);
        Node startnode6 = new Node(startNode.getNodeX() + 1, startNode.getNodeY() - 1);
        startNodeNeighbors.add(startnode1);
        startNodeNeighbors.add(startnode2);
        startNodeNeighbors.add(startnode3);
        startNodeNeighbors.add(startnode4);
        startNodeNeighbors.add(startnode5);
        startNodeNeighbors.add(startnode6);


        LinkedList<Node> pathNodeNeighbors = new LinkedList<Node>();
        Node pathnode1 = new Node(pathNode.getNodeX(), pathNode.getNodeY() + 1);
        Node pathnode2 = new Node(pathNode.getNodeX() + 1, pathNode.getNodeY());
        Node pathnode3 = new Node(pathNode.getNodeX() - 1, pathNode.getNodeY());
        Node pathnode4 = new Node(pathNode.getNodeX(), pathNode.getNodeY() - 1);
        Node pathnode5 = new Node(pathNode.getNodeX() - 1, pathNode.getNodeY() + 1);
        Node pathnode6 = new Node(pathNode.getNodeX() + 1, pathNode.getNodeY() - 1);
        pathNodeNeighbors.add(pathnode1);
        pathNodeNeighbors.add(pathnode2);
        pathNodeNeighbors.add(pathnode3);
        pathNodeNeighbors.add(pathnode4);
        pathNodeNeighbors.add(pathnode5);
        pathNodeNeighbors.add(pathnode6);

        for (int i = 0; i < startNodeNeighbors.size(); i++) {
            for (int j = 0; j < pathNodeNeighbors.size(); j++) {
                if (startNodeNeighbors.get(i).getNodeX() == pathNodeNeighbors.get(j).getNodeX() && startNodeNeighbors.get(i).getNodeY() == pathNodeNeighbors.get(j).getNodeY() && board.getCreatureAt(pathNodeNeighbors.get(j).getNodeX(),pathNodeNeighbors.get(j).getNodeY())==null) {
                    return true;
                }
            }
        }
        return false;
    }




}
