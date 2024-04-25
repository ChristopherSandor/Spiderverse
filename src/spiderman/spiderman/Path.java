package spiderman;
import java.util.*;

public class Path {

    private Dimension dimOne;
    private Dimension dimTwo;
    private int connectionWeight;

    public Path (Dimension dimOne, Dimension dimTwo){

        this.dimOne = dimOne;
        this.dimTwo = dimTwo;
        connectionWeight = dimOne.getDimensionWeight() + dimTwo.getDimensionWeight();

    }

    public boolean areConnected(Dimension dimensionOne, Dimension dimensionTwo){
        if((dimensionOne.getDimensionNumber() == dimOne.getDimensionNumber()) && (dimensionTwo.getDimensionNumber() == dimTwo.getDimensionNumber())){
            return true;
        } else if ((dimensionOne.getDimensionNumber() == dimTwo.getDimensionNumber()) &&(dimensionTwo.getDimensionNumber() == dimOne.getDimensionNumber())){
            return true;
        }
        return false;
    }

    public Dimension getDimensionOne(){
        return dimOne;
    }

    public Dimension getDimensionTwo(){
        return dimTwo;
    }

    public int getPathWeight(){
        return connectionWeight;
    }

    // Returns the other Dimension in the Path Object.
    public Dimension otherDimension(Dimension given){
        if(given.getDimensionNumber() == dimOne.getDimensionNumber()){
            return dimTwo;
        } else {
            return dimOne;
        }
    }
    
}
