package spiderman;
import java.util.ArrayList;

public class Person {
    private String name;
    private int locationNumber;
    private int signature;
    private ArrayList<Dimension> path = new ArrayList<Dimension>();
    private boolean amIspider = false;
    private Person hero = null;

    public Person(String name, int locationNumber, int signature){
        this.name = name;
        this.locationNumber = locationNumber;
        this.signature = signature;
        spiderLogic();
    }

    private void spiderLogic(){
        if(signature == locationNumber){
            amIspider = true;
        } else {
            amIspider = false;
        }
    }

    public boolean getamIspider(){
        return amIspider;
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
    public ArrayList<Dimension> getPath(){
        return path;
    }
    public void addToPath(Dimension dim){
        path.add(dim);
    }
    public void setPath(ArrayList<Dimension> path){
        this.path = path;
    }
    public void setHero(Person person){
        this.hero = person;
    }
    public Person returnHero(){
        return hero;
    }
    public boolean hasHero(){
        if(hero == null){
            return false;
        }
        return true;
    }

}
