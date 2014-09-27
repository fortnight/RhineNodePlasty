import org.json.simple.*;
public class JsonContinent{

  private String apiKey;
  private int max;
  private Continent c;

  public JsonContinent(String apiKey, String entity,int max)
  {
    this.apiKey = apiKey;
    this.max = max;
    this.c = new Continent(apiKey);
    c.populate(entity, max);
    c.addNewNeighbors();
    c.addNewNeighbors(2);
  }

  public Continent getContinent(){
    return c;
  }

  
  public static void main(String[] args){
    if(args.length>2){
      JsonContinent cont = new JsonContinent(args[0],args[1], Integer.parseInt(args[2]));
      String jsonText = JSONValue.toJSONString(cont.getContinent());
      System.out.println(jsonText);
   } 
  }
}

