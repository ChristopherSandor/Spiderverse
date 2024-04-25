package spiderman;
import java.util.*;

public class Dijkstra {
    
    private ArrayList<Path> currentPath = new ArrayList<Path>();
    private ArrayList<Path> bestPath = null;
    private ArrayList<Path> currentMarked = new ArrayList<Path>();
    private Dimension current;
    private Dimension endDimension;

    public Dijkstra (Dimension startDimension, Dimension endDimension){
        this.current = startDimension;
        this.endDimension = endDimension;
        DijkstraDriver();
    }

    public void DijkstraDriver(){
        search(current);
    }


    public void search(Dimension x){

        for(Path p: x.getPathing()){
            if(!currentMarked.contains(p)){
                currentMarked.add(p);

                // Gets the next Dimenision on the other side of the path:
                Dimension connectedDim = p.otherDimension(x);

                // Checks to see If we found our desired Dimenision on the other side of the path:
                if(connectedDim.getDimensionNumber() == endDimension.getDimensionNumber()){

                    if(bestPath != null){
                        if(calcPathWeight(bestPath) < calcPathWeight(currentPath)){
                            bestPath = currentPath;
                        }
                    } else {
                        bestPath = currentPath;
                    }
                } else {
                // Checks the paths on the other dimenision:
                search(connectedDim);   
                }
            }
        }


    }


    // Returns Total weight of the path:
    private int calcPathWeight(ArrayList<Path> arry){

        int total = 0;

        for(Path p: arry){
            total += p.getPathWeight();
        }

        return total;
    }

}
