import org.json.simple.*;
import java.util.TreeMap;
import java.io.FileWriter;
import java.io.IOException;
public class JsonContinent{

  private final String NAME = "name";
  private final String SOURCE = "source";
  private final String DESTINATION = "destination";

  public JSONObject createJSONContinent(Continent C){
    JSONObject cont = new JSONObject();
    cont.put("name", C.getName());
    JSONArray table = new JSONArray();
    TreeMap<Integer, Node<Country>> map = C.getGraph();
    for(int key: map.keySet()){
      Node<Country> value = map.get(key);
      String name = value.getValue().getName();
      String continent = value.getValue().getContinent();
      JSONObject entry = new JSONObject();
      entry.put("id", key);
      entry.put("country_name", name);
      entry.put("continent_name", continent);
      JSONArray neighbors = new JSONArray();
      for(int i: value.getNeighbors()){
        neighbors.add(i);
      }
      entry.put("neighbors",neighbors);
      table.add(entry);
    }
    cont.put("countries",table);
    return cont;
  }

  public JSONObject createJSONForGraph(Continent c){
    JSONArray nodes = new JSONArray();
    JSONArray linkes = new JSONArray();
    JSONObject graph = new JSONObject();
    for(int key =0;key< c.getGraph().size();key++){
      JSONObject entry = new JSONObject();
      Node<Country> node = c.getGraph().get(key);
      entry.put(NAME, node.getValue().getName());
      nodes.add(entry); 
      for(int neighbor: node.getNeighbors()){
        if(neighbor >= key){
          JSONObject line =new JSONObject();
          line.put(SOURCE, key);
          line.put(DESTINATION, neighbor);
          linkes.add(line);
        }
      }
    }
    graph.put("nodes", nodes);
     graph.put("links",linkes);
     return graph;

  }

  public void writeToFile(String filename, JSONObject obj){
    try{
      FileWriter file = new FileWriter(filename+".json");
      file.write(obj.toJSONString());
      file.flush();
      file.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  
  public static void main(String[] args){
    if(args.length>2){
       String apiKey = args[0];
       String entity = args[1];
       int max = Integer.parseInt(args[2]);
       Continent c = new Continent(apiKey);
       c.populate(entity, max);
       c.findDistances();
       c.addNewNeighbors();
       JsonContinent jc = new JsonContinent();
       JSONObject work = jc.createJSONForGraph(c);
       System.out.println(work);
       jc.writeToFile(entity, work);
   } 
  }
}

