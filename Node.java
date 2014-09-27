import java.util.ArrayList;
public class Node<T>{

  private T contained;
  private ArrayList<T> neighbors;

  public Node(){
    this.neighbors = new ArrayList<T>();
  }

}
