package spiderman;
import java.util.ArrayList;

public class Clusters {

    private Dimension[] clusterList; 
    private double clusterListThreshold;
    private int numberOfDimensionsAdded;

    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }

        StdIn.setFile(args[0]);

        int numberOfDimensions = StdIn.readInt();
        int initialSize = StdIn.readInt();
        double capacityThreshold = StdIn.readDouble();

        Clusters clusterObject = new Clusters(initialSize, capacityThreshold);
        clusterObject.createUniverise(numberOfDimensions);

        clusterObject.printCluster(args[1]);

    }

    public void createUniverise(int numberOfDimensions){

        // Creates the Universe and clusters:
        for(int i = 0; i < numberOfDimensions; i++){

            int newDimensionNumber = StdIn.readInt();
            int newCannonEvents = StdIn.readInt();
            int newWeight = StdIn.readInt();

            addDimension(newDimensionNumber, newWeight, newCannonEvents);
        }

        // Connect Links and Clusters to each other:
        for(int i = 0; i < clusterList.length; i++){
            Dimension pointer = clusterList[i];

            int indexLinkOne = ((clusterList.length + i + -1) % clusterList.length); 
            int indexLinkTwo = ((clusterList.length + i + -2) % clusterList.length); 

            Dimension addLinkOne = clusterList[indexLinkOne];
            Dimension addLinkTwo = clusterList[indexLinkTwo];

            pointer.addClusterLink(addLinkOne);
            pointer.addClusterLink(addLinkTwo);

            clusterList[indexLinkOne].addClusterLink(pointer);
            clusterList[indexLinkTwo].addClusterLink(pointer);

        }

        printCluster("clusters.out");
        
    }


    // Default constructor:
    public Clusters(int size, double capacityThreshold){
        this.clusterList = new Dimension[size];
        this.clusterListThreshold = capacityThreshold;
        numberOfDimensionsAdded = 0;
    }



    // Adds dimensions:
    public void addDimension(int dimensionNumber, int dimensionWeight, int cannonEvents){
        // Gets Location to Add:
        int location = hashing(dimensionNumber, clusterList.length);

        // Creates a pointer for nextNode:
        Dimension dimensionPointer = null;

        // Checks to see if any Dimensions are located in this cluster:
        if(clusterList[location] != null){
            dimensionPointer = clusterList[location];
        }

        // Creates new Dimension to front of list and adds to cluster:
        Dimension newDimension = new Dimension(dimensionPointer, dimensionNumber, dimensionWeight, cannonEvents);
        clusterList[location] = newDimension;

        // Adds one to the total dimension count:
        numberOfDimensionsAdded++;

        // Will rehash table if Overflow:
        if(thresholdCheck() == true){
            rehashTable();
        }

    }



    // hasing function (gets locations):
    private int hashing(int DimensionNumber, int length){
        return(DimensionNumber % (length));
    }



    // TRUE FOR OVERFLOW:
    // FALSE FOR NO OVERFLOW:
    private boolean thresholdCheck(){

        // Checks to see if Overflow of threshold:
        if(numberOfDimensionsAdded/clusterList.length >= clusterListThreshold){
            return true;
        }

        // No OverFlow:
        return false;
    }
    


    // Rehashes table automatically:
    private void rehashTable(){
        int newSize = clusterList.length * 2;
        Dimension[] newClusterList = new Dimension[newSize];
        Queue<Dimension> queueToAdd = new Queue<Dimension>();

        // Resetting this Value (So we dont constantly rehash):
        numberOfDimensionsAdded = 0;

        for(int i = 0; i < clusterList.length; i++){
            Dimension pointer = clusterList[i];

            // Cycles through list to add to queue:
            while(pointer != null){
                queueToAdd.enqueue(pointer);
                pointer = pointer.getNextNode();
            }
        }

        // Adds all Dimenstions to this new cluster:
        while(queueToAdd.isEmpty() != true){

            // Removes Node from Queue to Add to new Cluster Array:
            Dimension dimensionToAdd = queueToAdd.dequeue();

            // Gets new location in the new clusters array we are making:
            int newLocation = hashing(dimensionToAdd.getDimensionNumber(), newSize);

            // Pointer to traverse and replace:
            Dimension dimensionPointer = null;

            // Checks to see if any Dimensions are located in this new cluster:
            if(newClusterList[newLocation] != null){
                dimensionPointer = newClusterList[newLocation];
            }
            
            // Updates the nodes previous which is still from the older cluster list:
            dimensionToAdd.setNextNode(dimensionPointer);

            // Adds the new Node to the position (front):
            newClusterList[newLocation] = dimensionToAdd;

            numberOfDimensionsAdded++;

        }

        // Replaces old list with new List:
        this.clusterList = newClusterList;


        // print the cluster table:
        
    }

    public void printCluster(String outputFile){

        StdOut.setFile(outputFile);

        for(int i = 0; i < clusterList.length; i++){

            Dimension pointer = clusterList[i];

            // Skips if null:
            if(pointer == null){
                continue;
            }

            StdOut.print(pointer.getDimensionNumber());

            if(pointer.getNextNode() == null){

                ArrayList<Dimension> clusterLink = pointer.getClusterLink();

                        int x = clusterLink.get(0).getDimensionNumber();
                        int y = clusterLink.get(1).getDimensionNumber();
                        StdOut.print(" " + x + " " + y);

            } else {

                pointer = pointer.getNextNode();

                while(pointer != null){
                    StdOut.print(" " + pointer.getDimensionNumber());

                    // Checks to see if its the end and print out the connection between clusters:
                    if(pointer.getNextNode() == null){
                        ArrayList<Dimension> clusterLink = clusterList[i].getClusterLink();

                        int x = clusterLink.get(0).getDimensionNumber();
                        int y = clusterLink.get(1).getDimensionNumber();
                        StdOut.print(" " + x + " " + y);
                    }

                    pointer = pointer.getNextNode();
                }

            }   
            // Skips to next Line:
            StdOut.println();
        }
    }

    public int getAddedDimensions(){
        return numberOfDimensionsAdded;
    }

    public Dimension[] getClusterList(){
        return clusterList;
    }
}
