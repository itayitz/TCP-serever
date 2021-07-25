package Algorithems;

import Matrix.Index;
import Traversable.Node;
import Traversable.*;

import java.util.*;

public class BFSvisit <T>{

    Set<Node<T>> finished;     // set for finished nodes
    Set<NodesData<T>> result;  // set of NodesData for results
    Queue<NodesData<T>> discoveredQueue;  // queue of NodesData for discovered nodes

    public BFSvisit(){
        finished=new HashSet<Node<T>>();
        result=new HashSet<NodesData<T>>();
        discoveredQueue= new LinkedList<NodesData<T>>();

    }

    /**
     * @param node
     * @return NodesData of node
     */
    public NodesData<T> getConcreteDataBFS(T node){
        for(NodesData<T> nodesData : result){
            if(nodesData.getNode().getData().equals(node))
                return nodesData;
        }
        return null;
    }

    /**
     * Push to queue the starting node of our graph V
     * While queue is not empty: // there are nodes to handle V
     *     removed = poll operation V
     *     add to finish set V
     *      invoke getReachableNodes on the removed node V
     *     For each reachable node:
     *         if the current reachable node is not in finished set && checkList queue not include that node
     *         - set the popped node to parent of that node.
     *         - add that node to discovered queue with distance of his parent +1
     * @param partOfGraph
     * @return set of NodesData that include distances from start index to other indices
     */
    public Set<NodesData<T>> traverse(Traversable partOfGraph){
        Queue<Node<T>> checkList = new LinkedList<Node<T>>();
        Node<T> startIndex = partOfGraph.getOrigin();
        discoveredQueue.add(new NodesData<>(startIndex,0));
        checkList.add(startIndex);
        while (!checkList.isEmpty()){
            NodesData<T> poppedNode = discoveredQueue.poll();
            finished.add(checkList.poll());
            Collection<Node<T>> reachableNodes = partOfGraph.getReachableNodes(poppedNode.getNode());
            for(Node<T> node : reachableNodes){
                if(!finished.contains(node) && !checkList.contains(node)){
                    node.setParent(poppedNode.getNode());
                    discoveredQueue.add(new NodesData<>(node,poppedNode.getDistance() + 1));
                    checkList.add(node);
                }
            }
            result.add(poppedNode);
        }
        return result;
    }

}