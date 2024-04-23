package spiderman;
import java.util.*;

import javax.sound.midi.Track;

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
 * SpotInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * Two integers (line seperated)
 *      i.    Line one: The starting dimension of Spot (int)
 *      ii.   Line two: The dimension Spot wants to go to (int)
 * 
 * Step 4:
 * TrackSpotOutputFile name is passed in through the command line as args[3]
 * Output to TrackSpotOutputFile with the format:
 * 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
 * 
 * @author Seth Kelley
 */

public class TrackSpot {
    
    private Collider colliderObject;
    private ArrayList<Dimension> marked;

    public TrackSpot(Collider colliderObject){
        this.colliderObject = colliderObject;
        this.marked = new ArrayList<Dimension>();
    }

    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        Collider colliderObject = new Collider();
        colliderObject.createCollider(args[0], args[1]);

        TrackSpot tracker = new TrackSpot(colliderObject);

        StdIn.setFile(args[2]);

        int startingLocation = StdIn.readInt();
        int endingLocation = StdIn.readInt();

        Dimension starting = colliderObject.getList()[colliderObject.getPositionIndex(startingLocation)];
        Dimension ending = colliderObject.getList()[colliderObject.getPositionIndex(endingLocation)];

        tracker.findPath(starting, ending);

        tracker.print("trackspot.out");
        
    }

    public void findPath (Dimension startingDimension, Dimension endDimension){

        if(startingDimension.getDimensionNumber() != endDimension.getDimensionNumber()){
            dfsPath(startingDimension, endDimension);
        }

    }

    private void dfsPath(Dimension currentDimension, Dimension endDimension){

        if (marked.contains(currentDimension)) {
            return;
        }

        marked.add(currentDimension);

        if (currentDimension.getDimensionNumber() == endDimension.getDimensionNumber()) {
            return;
        }

        for (Dimension adj : currentDimension.getAdjacencyList()) {
            if (!marked.contains(adj)) {
                if(!marked.contains(endDimension)){
                    dfsPath(adj, endDimension);
                }
            }
        }

    }

    private void print(String outputFile){
        StdOut.setFile(outputFile);

        StdOut.print(marked.get(0).getDimensionNumber());

        for(int i = 1; i < marked.size(); i++){
            StdOut.print(" " + marked.get(i).getDimensionNumber()) ;
        }
    }


}
