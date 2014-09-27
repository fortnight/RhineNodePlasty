import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
public class RhineIo{

  private final String SPLIT = "\\\\\",\\\\\"";
  private String apiKey;
  private final String RHINEIO = "GET api.rhine.io/";
  public RhineIo(String apiKey){
    this.apiKey = apiKey;
  }

  public String[] closestEntities(String entity){
    InputStream is =null;
    InputStreamReader isr=null;
    BufferedReader br=null;
    String[] clearEntities = null;
    try{
      String command = RHINEIO+apiKey+"/closest_entities/"+entity;
      Runtime runtime = Runtime.getRuntime();
      Process process = runtime.exec(command);
      is = process.getInputStream();
      isr = new InputStreamReader(is);
      br = new BufferedReader(isr);
      String line;
      line = br.readLine();
      if(line !=null){
        String[] entities = line.split(":");
        String jumble = entities[1];
        clearEntities = jumble.split(SPLIT);
        String[] cleanFront = clearEntities[0].split("\\\\\"");
        clearEntities[0] = cleanFront[1];
        String[] cleanBack = clearEntities[clearEntities.length-1].split("\\\\");
        clearEntities[clearEntities.length-1] = cleanBack[0];
      }
    }catch(IOException io){
      io.printStackTrace();
    }finally{
      try{
      if(is !=null)
        is.close();
      if(isr !=null)
        isr.close();
      if(br !=null)
        br.close();
      }catch(IOException i){
        i.printStackTrace();
      }
      return clearEntities;
    }
  }
  public double distance(String entity1, String entity2){
    InputStream is =null;
    InputStreamReader isr=null;
    BufferedReader br=null;
    double distance= 0;
    try{
      String command = RHINEIO+apiKey+"/distance/"+entity1+"/"+entity2;
      Runtime runtime = Runtime.getRuntime();
      Process process = runtime.exec(command);
      is = process.getInputStream();
      isr = new InputStreamReader(is);
      br = new BufferedReader(isr);
      String line;
      line = br.readLine();
      //System.out.println(line);
      String[] removeFront = line.split(":\"");
      String [] removeEnd = removeFront[1].split("\"");
      distance = Double.parseDouble(removeEnd[0]);
    }catch(IOException io){
      io.printStackTrace();
    }finally{
      try{
      if(is !=null)
        is.close();
      if(isr !=null)
        isr.close();
      if(br !=null)
        br.close();
      }catch(IOException i){
        i.printStackTrace();
      }
      return distance;
    }
  }

  public static void main(String[] args){
    if(args.length>0){
      RhineIo rhine = new RhineIo(args[0]);
      String[] entities = rhine.closestEntities("Google");
      for(String s: entities){
        System.out.println(s);
      }
      double distance =rhine.distance("Cow", "Milk");
      System.out.println(distance);
    }
  }

}
