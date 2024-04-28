package spiderman;
import java.util.*;

public class Dijkstra {
    
    // Dimension Number, Distance from point
    private Hashtable<Integer, Integer> distance = new Hashtable<>();
    private Hashtable<Integer, Integer> previous = new Hashtable<>();
    private PriorityQueue<Dimension> pq = new PriorityQueue<>(new Dimension.DimensionComparator());
    private ArrayList<Dimension> Dvisited = new ArrayList<Dimension>();

    private Dimension current;
    private Dimension endDimension;

    public Dijkstra (Dimension startDimension, Dimension endDimension, Collider collider){
        this.current = startDimension;
        this.endDimension = endDimension;
        loadTable(collider);
        DijkstraDriver();
    }

    public void loadTable(Collider collider){
        Dimension[] array = collider.getList();
        for(int i = 0; i < array.length; i++){
            distance.put(array[i].getDimensionNumber(), Integer.MAX_VALUE);
            previous.put(array[i].getDimensionNumber(), 0);
        }
    }

    public void DijkstraDriver(){

        previous.put(current.getDimensionNumber(), -1);
        distance.put(current.getDimensionNumber(), 0);

        
        search(current);
    }

    public void search(Dimension s) {

        // Adds the source (aka: first element):
        pq.add(s);

        while(!pq.isEmpty()){

            Dimension m = pq.poll();
            Dvisited.add(m);

            for(Dimension w : m.getAdjacencyList()){

                if(Dvisited.contains(w)){
                    continue;
                }

                // Updates the path value if MAXED
                if(distance.get(w.getDimensionNumber()) == Integer.MAX_VALUE){
                    distance.put(w.getDimensionNumber(), (distance.get(m.getDimensionNumber()) + (w.getDimensionWeight() + m.getDimensionWeight())));
                    w.setDistance((distance.get(m.getDimensionNumber()) + (w.getDimensionWeight() + m.getDimensionWeight())));
                    pq.add(w);
                    previous.put(w.getDimensionNumber(), m.getDimensionNumber());
                } else if(distance.get(w.getDimensionNumber()) > (distance.get(m.getDimensionNumber()) + (w.getDimensionWeight() + m.getDimensionWeight()))){
                    distance.put(w.getDimensionNumber(), (distance.get(m.getDimensionNumber()) + (w.getDimensionWeight() + m.getDimensionWeight())));
                    w.setDistance((distance.get(m.getDimensionNumber()) + (w.getDimensionWeight() + m.getDimensionWeight())));
                    previous.put(w.getDimensionNumber(), m.getDimensionNumber());
                }

            }

        }

    }

    public ArrayList<Integer> createPath(Person p){

        ArrayList<Integer> path = new ArrayList<Integer>();
        int home = p.getSignature();

        int ptr = home;
        
        while(ptr != -1){
            path.add(0, ptr);
            ptr = previous.get(ptr);
        }

        return path;
    }

    // public class DimensionComparator implements Comparator<Dimension> {
    //     public int compare(Dimension d1, Dimension d2) {
    //         if (distance.get(d1.getDimensionNumber()) > distance.get(d2.getDimensionNumber())) return 1;
    //         else if (distance.get(d1.getDimensionNumber()) < distance.get(d2.getDimensionNumber())) return -1;
    //         else return 0;
    //     }
    // }

}

