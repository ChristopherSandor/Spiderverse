package spiderman;
import java.util.*;


public class CollectAnomalies {
    
    private ArrayList<Person> anomalies;
    private ArrayList<Dimension> marked;
    private Queue<Dimension> order;
    private Collider adjencyList;
    private int startNumber = 0;

    public CollectAnomalies(){
        anomalies = new ArrayList<Person>();
        marked = new ArrayList<Dimension>();
        adjencyList = null;
        order = new Queue<Dimension>();
    }
    public int getStartNum(){
        return startNumber;
    }

    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
                return;
        }
        
        CollectAnomalies object = new CollectAnomalies();
        object.anomaliesDriver(args[0], args[1], args[2], args[3]);

    }

    public void anomaliesDriver(String dimenisionFile, String peopleFile, String anomaliesFile, String anomaliesOutput){

        // Creates a Collider Object and Dimesions Lists:
        Collider colliderObject = new Collider();
        colliderObject.createCollider(dimenisionFile, peopleFile);   

        // Transfers over adjencyList:
        adjencyList = colliderObject;

        // Getting starting location (Dimenision Number):
        StdIn.setFile(anomaliesFile);
        int startingLocation = StdIn.readInt();
        this.startNumber = startingLocation;

        // Creates Lists:
        collectAnomalies(startingLocation);

        // Outputs Lists:
        print(anomaliesOutput);

        

    }

    private void collectAnomalies(int startingLocation){

        // Gets the Dimenision
        int index = adjencyList.getPositionIndex(startingLocation);
        Dimension startingDimension = adjencyList.getList()[index];


        marked.add(startingDimension);

        for(Dimension dim: startingDimension.getAdjacencyList()){
            if(!marked.contains(dim)){
                dim.addToBfsPath(startingDimension.getbfsPath());
                dim.addToBfsPath(startingDimension);
                order.enqueue(dim);
                marked.add(dim);
            }
        }

        if (!order.isEmpty()) {
            Dimension x = order.dequeue();
            bfs(x);
        }

    }

    private void bfs(Dimension current){

        for(Dimension dim: current.getAdjacencyList()){
            if(!marked.contains(dim)){

                dim.addToBfsPath(current.getbfsPath());
                dim.addToBfsPath(current);

                order.enqueue(dim);
                marked.add(dim);
            }
        }

        if(current.getGuestList().size() > 0){

            if(current.getnumberOfAnomalies() > 0){
                if(current.containSpider() == false){
                    // WE FOUND AN ANOMALIES WITH NO HERO
                    // for(Person dim: current.getGuestList()){
                    //     System.out.print("NULL: " + dim.getName() + " " + current.getDimensionNumber() + " " + dim.getLocationNumber() + " " + dim.getSignature() + " " + current.getGuestList().size() + " ");
                    // }
                    // System.out.println();
                    // System.out.print("PATH ^: ");
                    // for(Dimension x: current.getbfsPath()){
                    //     System.out.print(x.getDimensionNumber() + " ");
                    // }
                    // System.out.print(current.getDimensionNumber());
                    // System.out.println();

                    toAndBack(current.getbfsPath(), current, current.getGuestList().get(0));

                    
                } else {
                    // WE FOUND ANOMALIES WITH A HERO (REVERSE ROOT HOME)
                    // for(Person dim: current.getGuestList()){
                    //     System.out.print("HERO: "+ dim.getName() + " " + current.getDimensionNumber() + " " + dim.getLocationNumber() + " " + dim.getSignature() + " " + current.getGuestList().size() + " ");
                    // }
                    // System.out.println();
                    // System.out.print("PATH ^: ");
                    // for(Dimension x: current.getbfsPath()){
                    //     System.out.print(x.getDimensionNumber() + " ");
                    // }
                    // System.out.print(current.getDimensionNumber());
                    // System.out.println();




                    // Gets the villian and the fist hero in this guestlist of the current dimenision:
                    Person villian = null;
                    Person hero = null;

                    for(int i = 0; i < current.getGuestList().size(); i++){
                        if(current.getGuestList().get(i).getamIspider() == false){
                            villian = current.getGuestList().get(i);
                        } else if (hero == null){
                            hero = current.getGuestList().get(i);
                        }

                        if(villian != null && hero != null){
                            continue;
                        }
                    }

                    bringEmHome(current.getbfsPath(), current, villian, hero);
                }
            }
        }

        if (!order.isEmpty()) {
            Dimension x = order.dequeue();
            bfs(x);
        }
    }

    // WE FOUND AN ANOMALIES WITH NO SPIDER
    private void toAndBack(ArrayList<Dimension> firstHalfPath, Dimension currentDimension, Person villian){

        ArrayList<Dimension> temp = new ArrayList<Dimension>();
        temp.addAll(firstHalfPath);
        temp.add(currentDimension);
        
        // Reverseing path to finish off going home:
        for(int i = firstHalfPath.size()-1; i >= 0; i--){
            temp.add(firstHalfPath.get(i));
        }

        villian.setPath(temp);
        addAnomalies(villian);
    }

    // WE FOUND ANOMALIES WITH A HERO (REVERSE ROOT HOME)
    private void bringEmHome(ArrayList<Dimension> rootReversed, Dimension currentDimension, Person villian, Person hero){
        villian.setHero(hero);
        
        // Reverseing path to go home:
        ArrayList<Dimension> temp = new ArrayList<Dimension>();
        temp.add(currentDimension);
        for(int i = rootReversed.size()-1; i >= 0; i--){
            temp.add(rootReversed.get(i));
        }

        villian.setPath(temp);
        addAnomalies(villian);
    }

    private void print(String filename){
        StdOut.setFile(filename);

        for(int i=0; i < anomalies.size(); i++){
            
            Person p = anomalies.get(i);

            if(p.hasHero() == false){
                // NO SPIDER:
                StdOut.print(p.getName());

                for(int j = 0; j < p.getPath().size(); j++){
                    StdOut.print(" " + p.getPath().get(j).getDimensionNumber());
                }

                StdOut.println();

            } else{
                StdOut.print(p.getName() + " " + p.returnHero().getName());

                for(int j = 0; j < p.getPath().size(); j++){
                    StdOut.print(" " + p.getPath().get(j).getDimensionNumber());
                }

                StdOut.println();
            }
            
            
        }



    }       
    public void addAnomalies(Person villain){
        this.anomalies.add(villain);
    }

    public ArrayList<Person> getAnomalies(){
        return anomalies;
    }

    public Collider getCollider(){
        return adjencyList;
    }
}
