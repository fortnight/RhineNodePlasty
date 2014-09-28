import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Scanner;

public class RhineNodePlasty{

  private String apiKey;
  private ArrayList<Continent> world;
  private JsonContinent jsonTool;

  public RhineNodePlasty(String apiKey){
    this.apiKey = apiKey;
    this.jsonTool = new JsonContinent();
    this.world = new ArrayList<Continent>();
  }

  public void makeContinent(String entity, int max){
    Continent c = new Continent(apiKey);
    c.populate(entity, max);
    c.findDistances();
    c.addNewNeighbors();
    world.add(c);
  }
  
  public ArrayList<Continent> getWorld(){
    return world;
  }

  public void connectContinents(){
    int modifier = 0;
    for(int start =0; start< world.size(); start++){
      modifier += (world.get(start).getSize());
      System.out.println(modifier);
      for( int end = start+1; end<world.size(); end++){
       Continent startCont = world.get(start);
       Continent endCont = world.get(end);
       startCont.connectTo(endCont, modifier);        
      }
    }
  } 
  

  public void createJSONWorld(String fileName){
    JSONObject jsonWorld = jsonTool.createJSONForMany(world);
    jsonTool.writeToFile(fileName, jsonWorld);
  }

  public static void main(String[] args){
    if(args.length > 0){
      RhineNodePlasty model = new RhineNodePlasty(args[0]);
      Scanner reader = new Scanner(System.in);
      String command  = "not end";
      System.out.println("Please enter a command:\na: Add Continent \nd: Done");
      while(!command.equals("d")){
        command = reader.nextLine();
        if(command.equals("a")){
          System.out.println("What word would you like to find entities for");
          String entity = reader.nextLine();
          System.out.println("How many entities would you like to cap your search for?");
          int max = reader.nextInt();
          model.makeContinent(entity, max);    
          System.out.println("Continent "+ entity+" created.");    
          System.out.println("Please enter a command:\na: Add Continent \nd: Done");
        }/*else if(!command.equals("d")){
          System.out.println(command+" is not a command");
        }*/
       // System.out.println("Please enter a command:\na: Add Continent \nd: Done");
        
      }
      int worldSize = model.getWorld().size();
      if(worldSize>0){
        if(worldSize >1)
          model.connectContinents();
        model.createJSONWorld("RhineNodePlasty");
      }
    }
  }

}
