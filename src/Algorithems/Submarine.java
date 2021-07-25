package Algorithems;

import Matrix.*;
import Server.Strategy.ServerSubmarineStrategy;
import Traversable.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.*;

public class Submarine {

    /**
     * @param origin - indices that there value equal to 1
     * @param scc - concrete strongly connected component
     * @return origin without the indices of scc
     */
    public  HashSet<Index> deleteLastSCC (HashSet < Index > origin, HashSet < Index > scc){
        for (Index index : scc) {
            origin.remove(index);
        }
        return origin;
    }

    /**
     * that function find all parts of scc of this matrix.
     * initialize Hashset of indices with value 1 from this matrix
     * 1. it takes random index from relevant indices (indices with value 1)
     *   and find all indices of his scc with DFSvisit.traverse function.
     * 2. add that indices to List of Hashsets, that present single scc
     * 3. removes that scc indices from relevant
     *   repeat 1-3 until relevant indices are empty
     * @param matrix
     * @return all parts of scc of this matrix
     */
    public List<HashSet<Index>> getAllSCC (Matrix matrix){

        HashSet<Index> relevantIndices = matrix.getRelevantIndices();
        List<HashSet<Index>> SCC = new ArrayList<HashSet<Index>>();
        TraversableMatrix myTraversableMatrix = new TraversableMatrix(matrix);
        while (!relevantIndices.isEmpty()) {
            DFSvisit<Index> dfsVisit = new DFSvisit<Index>();
            myTraversableMatrix.setStartIndex(relevantIndices.iterator().next());
            HashSet<Index> tempScc = new HashSet<Index>((HashSet) dfsVisit.traverse(myTraversableMatrix));
            SCC.add((HashSet<Index>) tempScc.clone());
            relevantIndices = deleteLastSCC(relevantIndices, tempScc);
        }

        return SCC;
    }


    /**
     * that function check concrete scc and return if that scc present submarine or not
     * 1. submarine must to be minimum two indices with value 1 vertically
     * 2. submarine must to be minimum two indices with value 1 horizontally
     * 3. if there is two indices diagonal with value 1 so they must comply with sections 1 and 2
     *
     * if section 1 and 2 is false then return false or if 3 is false then return false
     *
     * @param matrix
     * @param indices
     * @return false if it is not submarine, and true if it is submarine
     */
    public boolean checkConnectedComponent (Matrix matrix,HashSet<Index> indices){
        if(indices.size()==1){return false;}
        TraversableCrossMatrix crossMatrix=new TraversableCrossMatrix(matrix);
        TraversableMatrix traversableMatrix=new TraversableMatrix(matrix);
        for(Index index:indices)
        {
            Collection<Node<Index>> reachableNodes =traversableMatrix.getReachableNodes(new Node(index));
            Collection<Index> indexCrossNeighbours= crossMatrix.getCrossNeighbors(index);
            for(Index concreteCrossNei:indexCrossNeighbours){
                if(matrix.getValue(concreteCrossNei)==1) {
                    if((concreteCrossNei.getRow()<index.getRow())&&(concreteCrossNei.getColumn()<index.getColumn())){
                        if(((matrix.getValue(new Index(index.getRow()-1,index.getColumn())))==0)
                        ||((matrix.getValue(new Index(index.getRow(),index.getColumn()-1)))==0)) return false ;

                    }
                    if((concreteCrossNei.getRow()<index.getRow())&&(concreteCrossNei.getColumn()>index.getColumn())){
                        if(((matrix.getValue(new Index(index.getRow()-1,index.getColumn())))==0)
                                ||((matrix.getValue(new Index(index.getRow(),index.getColumn()+1)))==0)) return false ;

                    }
                    if((concreteCrossNei.getRow()>index.getRow())&&(concreteCrossNei.getColumn()>index.getColumn())){
                        if(((matrix.getValue(new Index(index.getRow()+1,index.getColumn())))==0)
                                ||((matrix.getValue(new Index(index.getRow(),index.getColumn()+1)))==0)) return false ;

                    }
                    if((concreteCrossNei.getRow()>index.getRow())&&(concreteCrossNei.getColumn()<index.getColumn())){
                        if(((matrix.getValue(new Index(index.getRow()+1,index.getColumn())))==0)
                                ||((matrix.getValue(new Index(index.getRow(),index.getColumn()-1)))==0)) return false ;

                    }

                } else { if(reachableNodes.isEmpty())return false;

                }
            }
        }
        return true;
    }

    /**
     * this function check use checkConnectedComponent function and
     * find how many valid submarine we have in that matrix and return that
     * @param matrix
     * @return number of valid submarine in matrix
     */
    public int checkSubmarines (Matrix matrix){
        List<HashSet<Index>> SCC=new ArrayList<HashSet<Index>>();

        SCC=getAllSCC(matrix);
        int submarines = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(SCC.size());
        List<Future<Boolean>> results = new ArrayList<>();

        for(HashSet<Index> concreteList:SCC){
            Callable<Boolean> callableCheck = ()-> checkConnectedComponent(matrix,concreteList);
            results.add(executorService.submit(callableCheck));
        }

        for (Future<Boolean> future : results){
            try {
                if(future.get()){
                    submarines++;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return submarines;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ServerSubmarineStrategy serverSubmarineStrategy = new ServerSubmarineStrategy();
        Matrix matrix = serverSubmarineStrategy.readMatrixFromFile();
        Submarine submarine = new Submarine();
        System.out.println( submarine.checkSubmarines(matrix));
    }
}