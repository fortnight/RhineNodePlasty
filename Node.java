import java.util.ArrayList;
public class Node<T>{

  private T value;
  private ArrayList<T> neighbors;
  private int id;
  
  public Node(T value, int id){
    this.setValue(value);
    this.neighbors = new ArrayList<T>();
    this.setId(id);
  }

  public void setValue(T value){
    this.value = value;
  }

  public void setId(int id){
    this.id = id;
  }
 
  public T getValue(){
    return value;
  }

  public int getId(){
    return this.id;
  }

  public void addNeighbor(T neighbor){
    neighbors.add(neighbor);
  }

  public ArrayList<T> getNeighbors(){
    return neighbors;
  }
  
  @Override
  public String toString(){
    return "[I: "+id+", Value: "+value+", Neighbors: "+neighbors+"]";
  }

}
