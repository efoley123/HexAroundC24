package hexaround.movement;

import java.util.LinkedList;
import hexaround.entity.*;

public class Node {

    private int x;
    private int y;

    private Node parent = null;

    public Node(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public void addPath(Node node) {
        this.parent = node;
    }
    public Node getPrevious() {
        return this.parent;
    }


    public int getNodeX() {
        return x;
    }
    public int getNodeY() {
        return y;
    }

}
