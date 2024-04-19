package spiderman;
import java.util.ArrayList;

public class Person {
    private String name;
    private int locationNumber;
    private int signature;

    public Person(String name, int locationNumber, int signature){
        this.name = name;
        this.locationNumber = locationNumber;
        this.signature = signature;
    }

    public String getName(){
        return name;
    }
    public int getLocationNumber(){
        return locationNumber;
    }
    public void setLocationNumber(int x){
        locationNumber = x;
    }
    public int getSignature(){
        return signature;
    }

}
