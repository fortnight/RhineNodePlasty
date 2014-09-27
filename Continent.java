import java.util.TreeMap;
import java.util.ArrayList;
public class Continent{

  private TreeMap<Integer, Node<String>> graph;
  private RhineIo rhine;
  private double adjMatrix[][];

  public Continent(String apiKey){
    graph = new TreeMap<Integer, Node<String>>();
    rhine = new RhineIo(apiKey);
  }

  public void populate(String entity, int maxNode){
    String[] countries = rhine.closestEntities(entity);
    int length = countries.length;
    Node<String> capital = new Node<String>(entity, 0);
    this.graph.put(0, capital);
    for(int i = 0; i<length;i++){
      if( i == maxNode){
          i = length;
      }else{
        String s = countries[i];
        int id = this.getSize();;
        Node<String> country = new Node<String>(s, id);
        this.graph.put(id,country);
      }
    }
    int dim = this.getSize();
    adjMatrix = new double[dim][dim];
  }
 
  public TreeMap<Integer, Node<String>> getGraph(){
    return this.graph;
  }

  public int getSize(){
    return this.graph.size();
  }
  
  private void setDistance(int start, int end){
    String startPoint = graph.get(start).getValue();
    String endPoint = graph.get(end).getValue();
    double distance = rhine.distance(startPoint, endPoint);
    adjMatrix[start][end] = distance;
    adjMatrix[end][start] = distance;
  }

  public void findDistances(){
    for(int row = 0; row < getSize(); row++){
      for(int col = row; col <getSize(); col++){
        if(row != col){
          setDistance(row,col);
        }
      }
    }
  }

  private void setNeighbors(int start, int end){
    graph.get(start).addNeighbor(end);
    graph.get(end).addNeighbor(start);
  }

  public void addNewNeighbors(){
    addNewNeighbors(1);
  }

  public void addNewNeighbors(int denom){
    for(int row = 0; row <getSize()/denom; row++){
      int neighborToAdd = -1;
      double distance = 9999;
      ArrayList<Integer> neighbors = graph.get(row).getNeighbors();
      for(int col = 0; col < getSize()/denom; col++){
        //System.out.println("addNewNeighbors: col");
        double newDistance = this.getMatrix()[row][col];
        if(col != row && !neighbors.contains(col) && newDistance <= distance){
          distance = newDistance; 
          neighborToAdd = col;
        }
      }
      if(neighborToAdd != -1){
        setNeighbors(row, neighborToAdd);  
      }
    }
  }

  public double[][] getMatrix(){
    return adjMatrix;
  }
}
