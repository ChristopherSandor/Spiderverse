package spiderman;
import java.util.*;

public class Stack{

    private ArrayList<Dimension> stack;
    private Dimension top;
    private int index;

    public Stack(){
        stack = new ArrayList<Dimension>();
        top = null;
        index = 0;
    }

    public void push(Dimension item){
        stack.add(item);
        top = stack.get(index);
        index++;
    }
    public Dimension pop(){
        if(stack.size() != 0){
            Dimension temp = stack.get(index - 1);
            stack.remove(index - 1);
            index--;
            if(index != 0){
                top = stack.get(index - 1);
            } else {
                top = stack.get(0);
            }
            return temp;
        }
        return null;
    }

}