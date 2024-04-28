package spiderman;
import java.util.ArrayList;
import java.util.Comparator;

public class Dimension {

    private Dimension nextNode;
    private ArrayList<Dimension> clusterLink;
    private ArrayList<Dimension> adjacencyList = new ArrayList<Dimension>();
    private ArrayList<Dimension> bfsPath = new ArrayList<Dimension>();
    private ArrayList<Path> pathing = new ArrayList<Path>();
    private ArrayList<Person> guestList;
    private int dimensionNumber;
    private int dimensionWeight;
    private int cannonEvents;
    private boolean marked = false;
    private int distance = Integer.MAX_VALUE;

    private int numOfAnomalies = 0;
    private boolean containSpider = false;


    public Dimension(Dimension nextNode, int dimensionNumber, int dimensionWeight, int cannonEvents){
        this.nextNode = nextNode;
        this.dimensionNumber = dimensionNumber;
        this.dimensionWeight = dimensionWeight;
        this.clusterLink = new ArrayList<Dimension>();
        this.cannonEvents = cannonEvents;
        this.guestList = new ArrayList<Person>();
    }

    public int getCannonEvents(){
        return cannonEvents;
    }

    // nextNode:
    public Dimension getNextNode(){
        return nextNode;
    }

    public void setNextNode(Dimension nextNode){
        this.nextNode = nextNode;
    }


    // dimestionNumber:
    public int getDimensionNumber(){
        return dimensionNumber;
    }


    // dimestionWeight:
    public int getDimensionWeight(){
        return dimensionWeight;
    }



    // clusterLink:
    public void addClusterLink(Dimension link){
        this.clusterLink.add(link);
    }
    public void addClusterLink(int location, Dimension link){
        this.clusterLink.add(location, link);
    }

    public ArrayList<Dimension> getClusterLink(){
        return clusterLink;
    }



    // guestList:
    public void addGuest(Person person){
        guestList.add(person);
        statusUpdate();
    }

    public void removeGuest(Person person){
        for(int i = 0; i < guestList.size(); i++){
            if(guestList.get(i).getName().equals(person.getName())){
                guestList.remove(i);
                return;
            }
        }
        statusUpdate();
    }

    public boolean containSpider(){
        return containSpider;
    }

    public ArrayList<Person> getGuestList(){
        return guestList;
    }

    public ArrayList<Dimension> getAdjacencyList(){
        return adjacencyList;
    }
    public void addToAdjacencyList(Dimension item){
        adjacencyList.add(item);
    }

    public int getnumberOfAnomalies(){
        return numOfAnomalies;
    }

    public void statusUpdate(){
        if(guestList.size() == 0){
            numOfAnomalies = 0;
            containSpider = false;
        }

        if(guestList.size() > 0){
            for(Person p : guestList){
                if(p.getSignature() != dimensionNumber){
                    numOfAnomalies++;
                } else {
                    containSpider = true;
                }
            }
        }

    }

    public ArrayList<Dimension> getbfsPath(){
        return bfsPath;
    }
    public void addToBfsPath(ArrayList<Dimension> prevList){
        bfsPath.addAll(prevList);
    }
    public void addToBfsPath(Dimension prevDim){
        bfsPath.add(prevDim);
    }

    public void addPath(Path path){
        pathing.add(path);
        // p(path);
    }

    public void p(Path p){
 
        System.out.println("JOINING " + p.getDimensionOne().getDimensionNumber() + " with " + p.getDimensionTwo().getDimensionNumber() + " PATH WEIGHT: " + p.getPathWeight());
    }
    public ArrayList<Path> getPathing(){
        return pathing;
    }

    public int getDistance(){
        return distance;
    }


    public void setDistance(int i){
        this.distance = i;
    }


    public static class DimensionComparator implements Comparator<Dimension> {
        @Override
        public int compare(Dimension d1, Dimension d2) {
            if (d1.getDistance() > d2.getDistance()){
                return 1;
            } else if (d1.getDistance() < d2.getDistance()){
                return -1;
            } else {
                return 0;
            }
        }

    }
}
