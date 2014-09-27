import java.util.ArrayList;
public class Continent{

  private ArrayList<Node> graph;
  private RhineIo rhine;

  public Continent(String apiKey){
    graph = new ArrayList<Graph>();
    rhine = new RhineIo(apiKey);
  }

  public void populate(String entity){
    String[] countries = rhine.closestEntites(entity);
    if(countries.length > 0){
      for(String s: countries){
        Node<String> country = new Node<String>(s);
        graph.add(country);
      }
    }
  }
}
