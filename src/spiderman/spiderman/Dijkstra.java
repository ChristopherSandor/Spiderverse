package spiderman;
import java.util.*;

public class Dijkstra {
    
    private ArrayList<Path> currentPath = new ArrayList<Path>();
    private ArrayList<Path> bestPath = new ArrayList<Path>();
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

    public ArrayList<Path> getBestPath(){
        return bestPath;
    }

    public void search(Dimension x){

        ArrayList<Path> xPathSorted = sortList(x.getPathing());

        if(x.getDimensionNumber() == endDimension.getDimensionNumber()){
            if(calcPathWeight(currentPath) < calcPathWeight(bestPath)){
                bestPath = setNewList(currentPath);
                System.out.println("FOUND THIS MF");
            }
            return;
        }

        for(Path selectedPath : xPathSorted){

            if(!currentMarked.contains(selectedPath)){
                currentMarked.add(selectedPath);
                currentPath.add(selectedPath);

                Dimension next = selectedPath.otherDimension(x);

                System.out.println("VISTED: " + next.getDimensionNumber());
                search(next);

                currentMarked.remove(selectedPath);
                currentPath.remove(selectedPath);

            }
        }


    }

    private ArrayList<Path> sortList(ArrayList<Path> array) {
        // Create a copy of the original list to avoid modifying it directly
        ArrayList<Path> temp = new ArrayList<Path>(array);
        int n = temp.size();
    
        // One by one move the boundary of the unsorted subarray
        for (int i = 0; i < n-1; i++) {
            // Find the minimum element in the unsorted part
            int minIndex = i;
            for (int j = i+1; j < n; j++) {
                if (temp.get(j).getPathWeight() < temp.get(minIndex).getPathWeight()) {
                    minIndex = j;
                }
            }
    
            // Swap the found minimum element with the first element of the unsorted part
            Path lower = temp.get(minIndex);
            temp.set(minIndex, temp.get(i));
            temp.set(i, lower);
        }
    
        return temp;
    }
    
    private ArrayList<Path> setNewList(ArrayList<Path> p){
        ArrayList<Path> temp = new ArrayList<Path>();

        for(Path pp : p){
            temp.add(pp);
        }

        return temp;
    }

    // Returns Total weight of the path:
    private int calcPathWeight(ArrayList<Path> arry){

        if(arry.isEmpty()){
            return Integer.MAX_VALUE;
        }

        int total = 0;

        for(Path p: arry){
            total += p.getPathWeight();
        }

        return total;
    }

}
