import java.util.TreeMap;
import java.util.ArrayList;
public class Continent{

  private TreeMap<Integer, Node<Country>> graph;
  private RhineIo rhine;
  private String name;
  private double adjMatrix[][];
  private int id;

  public Continent(String apiKey){
    this.graph = new TreeMap<Integer, Node<Country>>();
    this.rhine = new RhineIo(apiKey);
    this.id = 0;
  }

  public void populate(String entity, int maxNode){
    String[] countries = rhine.closestEntities(entity);
    int length = countries.length;
    this.name = entity;
    Country c= new Country(entity, entity);
    Node<Country> capital = new Node<Country>(c, 0);
    this.graph.put(0, capital);
    for(int i = 0; i<length;i++){
      if( i == maxNode){
          i = length;
      }else{
        String s = countries[i];
        int id = this.getSize();;
        c = new Country(s, entity);
        Node<Country> country = new Node<Country>(c, id);
        this.graph.put(id,country);
      }
    }
    int dim = this.getSize();
    adjMatrix = new double[dim][dim];
  }
 
  public TreeMap<Integer, Node<Country>> getGraph(){
    return this.graph;
  }

  public int getSize(){
    return this.graph.size();
  }
  
  private void setDistance(int start, int end){
    String startPoint = graph.get(start).getValue().getName();
    String endPoint = graph.get(end).getValue().getName();
    double distance = rhine.distance(startPoint, endPoint);
    adjMatrix[start][end] = distance;
    adjMatrix[end][start] = distance;
  }

  public void findDistances(){
    for(int row = 0; row < getSize(); row++){
      for(int col = row; col <getSize(); col++){
        if(row != col ){
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
    addNewNeighbors(0);
  }

  public void addNewNeighbors(int modifier){
    for(int row = 1; row <getSize(); row++){
      int neighborToAdd = -1;
      double distance = 9999;
      ArrayList<Integer> neighbors = graph.get(row).getNeighbors();
      for(int col = 1; col < getSize(); col++){
        //System.out.println("addNewNeighbors: col");
        double newDistance = this.getMatrix()[row][col];
        if( col != row && !neighbors.contains(col) && newDistance <= distance){
          distance = newDistance; 
          neighborToAdd = col;
        }
      }
      if(neighborToAdd != -1){
        setNeighbors(row, neighborToAdd);  
      }
    }
    for(int i: graph.keySet()){
      if(i !=0){
        setNeighbors(0,i);
      }
    }
    
  }

  public double[][] getMatrix(){
    return adjMatrix;
  }

  public void connectTo(Continent another, int modifier){
   double[][] bridge = new double[this.getSize()][another.getSize()];
   //find distances
   for(int myKey : this.getGraph().keySet()){
     for(int anotherKey : another.getGraph().keySet()){
       String firstValue = this.getGraph().get(myKey).getValue().getName();
       String secondValue = another.getGraph().get(anotherKey).getValue().getName();
       bridge[myKey][anotherKey] = this.rhine.distance(firstValue, secondValue);
     }
   }
   //find lowestdistance
   for(int row=0; row< this.getSize(); row++){
     double distance =999999;
     int lowest = -1;
     for(int col = 0; col < another.getSize(); col++){
       double newDistance = bridge[row][col];
       if(newDistance < distance){
         distance = newDistance;
         lowest = col;
       }
     }
     if(lowest != -1){
        //System.out.println("LOWEST: "+lowest);
        //System.out.println("MODIFIER: "+modifier);
        graph.get(row).addNeighbor(lowest+modifier);
        //graph.get(lowest).addNeighbor(row);
     }
   }
   
  }

  @Override
  public String toString(){
    return this.name+"'s Graph: "+ graph;
  }

  public String getName(){
    return this.name;
  }
}
