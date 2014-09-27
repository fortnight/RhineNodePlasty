import java.util.TreeMap;
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

  public double[][] getMatrix(){
    return adjMatrix;
  }
}
