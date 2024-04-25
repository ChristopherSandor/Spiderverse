package spiderman;
import java.util.ArrayList;

public class Collider {

    private Dimension[] adjacencyList;

    public Collider (){
        this.adjacencyList = new Dimension[0];
    }

    public Dimension[] getList(){
        return adjacencyList;
    }

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }

        Collider object = new Collider();
        object.createCollider(args[0], args[1]);

    }

    public void createCollider(String dimenisionFile, String peopleFile){

        StdIn.setFile(dimenisionFile);
        
        // Grabs some numbers:
        int numberOfDimensions = StdIn.readInt();
        int initialSize = StdIn.readInt();
        double capacityThreshold = StdIn.readDouble();

        // Creates shit:
        Clusters clusterObject = new Clusters(initialSize, capacityThreshold);
        clusterObject.createUniverise(numberOfDimensions);

        // Creates Collider Object and the list:
        adjacencyList = new Dimension[clusterObject.getAddedDimensions()];
        populateList(clusterObject);

        // Adds people:
        addPeople(peopleFile);

        // Print Out:
        print("collider.out");

    }

    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // Adds Person(s) to each dimenision guestList in the Collider Array List:
    // 
    // ***********
    //
    public void addPeople(String fileName){
        StdIn.setFile(fileName);

        int numberOfPeopletoAdd = StdIn.readInt();

        for(int i = 0; i < numberOfPeopletoAdd; i++){


            // Reading Standard Inputs from File:
            int currentDimension = StdIn.readInt();
            String name = StdIn.readString();
            int signature = StdIn.readInt();

            // Creating a new person Object:
            Person person = new Person(name, currentDimension, signature);

            // Adds the person:
            adjacencyList[getPositionIndex(currentDimension)].addGuest(person);
        }
    }
    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // Adds dimenisions to each dimenision adjacencyList in the Collider Array List:
    // 
    // ***********
    //
    public void populateList(Clusters universe){

        Queue<Dimension> queueToAdd = queueDimensions(universe);
        Dimension[] clusterList = universe.getClusterList();

        // Adds the Dimenision we are looking for at the front:
        for(int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = (queueToAdd.dequeue());
        }

        // goes through each cluster head and adds to its adjacencyList:
        for(int i = 0; i < clusterList.length; i++){
            
            Dimension pointer = clusterList[i];
            int mainLocation = getPositionIndex(pointer.getDimensionNumber());

            while(pointer != null){
                if(pointer.getDimensionNumber() != clusterList[i].getDimensionNumber()){
                    addLink(adjacencyList[mainLocation], pointer);
                }
                pointer = pointer.getNextNode();
            }

            int indexLinkOne = ((clusterList.length + i + -1) % clusterList.length); 
            int indexLinkTwo = ((clusterList.length + i + -2) % clusterList.length); 

            Dimension dimensionOne = clusterList[indexLinkOne];
            Dimension dimensionTwo = clusterList[indexLinkTwo];

            addLink(adjacencyList[mainLocation], dimensionOne);
            addLink(adjacencyList[mainLocation], dimensionTwo);
        }

    }
    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // Returns the position of the dimension
    // 
    // ***********
    //
    public int getPositionIndex(int dimensionNumber){

        for(int position = 0; position < adjacencyList.length; position++){
            if(adjacencyList[position].getDimensionNumber() == dimensionNumber){
                return position;
            }
        }

        return 0;
    }
    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // Adds Links between two dimensions to the list:
    // 
    // ***********
    //
    private void addLink(Dimension dimensionOne, Dimension dimensionTwo){

        int dOneLocation = getPositionIndex(dimensionOne.getDimensionNumber());
        int dTwoLocation = getPositionIndex(dimensionTwo.getDimensionNumber());

        adjacencyList[dTwoLocation].addToAdjacencyList(dimensionOne);
        adjacencyList[dOneLocation].addToAdjacencyList(dimensionTwo);

        Path path = new Path(dimensionOne, dimensionTwo);

        dimensionOne.addPath(path);

    }
    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // Simply just gets and returns a queue of all dimensions in the universe (aka clusterList):
    // 
    // ***********
    //
    private Queue<Dimension> queueDimensions(Clusters universe){
        Queue<Dimension> queueToAdd = new Queue<Dimension>();

        Dimension[] clusterList = universe.getClusterList();

        for(int i = 0; i < clusterList.length; i++){
            Dimension pointer = clusterList[i];

            // Cycles through list to add to queue:
            while(pointer != null){
                queueToAdd.enqueue(pointer);
                pointer = pointer.getNextNode();
            }
        }

        return queueToAdd;
    }
    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // returns if the two vertices are connected to each other:
    // 
    // ***********
    //
    public boolean hasEdge(Dimension x, Dimension y){
        // *ORDERED DOES NOT MATTER*

        // Checks x dimension adjgency List and sees if they are connected:
        for(int i = 0; i < x.getAdjacencyList().size(); i++){
            if(x.getAdjacencyList().get(i).getDimensionNumber() == y.getDimensionNumber()){
                return true;
            }
        }

        return false;
    }

    // 
    // 
    // 
    // 
    // 
    // ***********
    // 
    // Prints the Collider chart and the Dimenisions ArrayLists of Adgency Lists:
    // 
    // ***********
    //
    private void print(String outputFile){
        
        StdOut.setFile(outputFile);

        for(int i = 0; i < adjacencyList.length; i++){

            int totalEdges = adjacencyList[i].getAdjacencyList().size();
            int firstDimensionNumber = adjacencyList[i].getDimensionNumber();

            StdOut.print(firstDimensionNumber);

            for(int j = 0; j < totalEdges; j++){

                int x = adjacencyList[i].getAdjacencyList().get(j).getDimensionNumber();

                StdOut.print(" " + x);

            }

            // Skips to the next line:
            StdOut.println();

        }



    }

}