package spiderman;
import java.util.*;

import javax.sound.midi.Track;

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

        tracker.print(args[3]);
        
    }

    public void TrackSpotDriver(String DimenisionFile, String PersonFile, String inputfileTrackSport){
        Collider colliderObject = new Collider();
        colliderObject.createCollider(DimenisionFile, PersonFile);   

        StdIn.setFile(inputfileTrackSport);

        int startingLocation = StdIn.readInt();
        int endingLocation = StdIn.readInt();

        Dimension starting = colliderObject.getList()[colliderObject.getPositionIndex(startingLocation)];
        Dimension ending = colliderObject.getList()[colliderObject.getPositionIndex(endingLocation)];

        findPath(starting, ending);

        print("trackspot.out");
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
