package spiderman;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * HubInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * AnomaliesInputFile name is passed through the command line as args[3]
 * Read from the AnomaliesInputFile with the format:
 * 1. e (int): number of anomalies in the file
 * 2. e lines, each with:
 *      i.   The Name of the anomaly which will go from the hub dimension to their home dimension (String)
 *      ii.  The time allotted to return the anomaly home before a canon event is missed (int)
 * 
 * Step 5:
 * ReportOutputFile name is passed in through the command line as args[4]
 * Output to ReportOutputFile with the format:
 * 1. e Lines (one for each anomaly), listing on the same line:
 *      i.   The number of canon events at that anomalies home dimensionafter being returned
 *      ii.  Name of the anomaly being sent home
 *      iii. SUCCESS or FAILED in relation to whether that anomaly made it back in time
 *      iv.  The route the anomaly took to get home
 * 
 * @author Seth Kelley
 */

public class GoHomeMachine {

    private ArrayList<Dijkstra> totalPaths;
    private int numSendingBack = 0;
    private Collider col;
    private String outputFile;

    public GoHomeMachine(String inputFileName, String outputFileName, Collider collider, int startNumber){
        totalPaths = new ArrayList<Dijkstra>();
        col = collider;
        this.outputFile = outputFileName;

        readInput(inputFileName, startNumber);
    }
    public static void main(String[] args) {

        if ( args.length < 5 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.GoHomeMachine <dimension INput file> <spiderverse INput file> <hub INput file> <anomalies INput file> <report OUTput file>");
                return;
        }

        CollectAnomalies object = new CollectAnomalies();
        object.anomaliesDriver(args[0], args[1], args[2], "collected.out");


        String inputFileName = args[3];
        String outputFileName = args[4];

        GoHomeMachine machine = new GoHomeMachine(inputFileName, outputFileName, object.getCollider(), object.getStartNum());


        
    }

    private void readInput(String input, int startNumber){
        StdIn.setFile(input);
        this.numSendingBack = StdIn.readInt();
        Dimension[] adjList = col.getList();

        Dimension startDimension = adjList[col.getPositionIndex(startNumber)];
        

        Dijkstra searchObject = new Dijkstra(startDimension, null, col);

        ArrayList<String> outputs = new ArrayList<String>();

        for(int i = 0; i < numSendingBack; i++){

            String name = StdIn.readString();
            Person p = col.getPerson(name);
            int givenCost = StdIn.readInt();

            ArrayList<Integer> createdPath = searchObject.createPath(p);
            boolean verdict = (givenCost > findCost(createdPath));
            String verdictWords;
            
            int numOfCannonEvents = adjList[col.getPositionIndex(p.getSignature())].getCannonEvents();
            if(verdict == false){
                numOfCannonEvents--;
                verdictWords = "FAILED";
            } else {
                verdictWords = "SUCCESS";
            }

            String line = (numOfCannonEvents +  " " + p.getName() + " " + verdictWords);

            for(int j = 0; j < createdPath.size(); j++){
                line = line + " " + createdPath.get(j);
            }

            outputs.add(line);

        }

        readOutput(outputs);


    }


    private int findCost(ArrayList<Integer> path){

        ArrayList<Dimension> dArray = new ArrayList<Dimension>();
        Dimension[] adjList = col.getList();

        int cost = 0;

        for(int i = 0; i < path.size(); i++){
            dArray.add(adjList[col.getPositionIndex(i)]);
        }

        for(int i = 0; i < dArray.size(); i++){
            cost += 2*dArray.get(i).getDimensionWeight();            
        }

        cost = cost-dArray.get(0).getDimensionWeight() - dArray.get(dArray.size()-1).getDimensionWeight();

        return cost;
    }

    private void readOutput(ArrayList<String> list){
        StdOut.setFile(outputFile);

        for(int i = 0; i < list.size(); i++){
            StdOut.println(list.get(i));
        }
    }


}
