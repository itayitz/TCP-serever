package Algorithems;

import Matrix.Index;
import Traversable.Node;

public class NodesData<T> {
    private Node<T> node;
    private Integer distance;

    public NodesData(Node<T> node){
        this.node=node;
        this.distance= null; //present infinity
    }

    public NodesData(Node<T> node, int dist){
        this.node=node;
        this.distance=dist;
    }

    public Node<T> getNode() {
        return node;
    }

    public void setNode(Node<T> node) {
        this.node = node;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}