
public class Country{

  private String name;
  private String continent;

  public Country(String name, String continent){
    this.setName(name);
    this.setContinent(continent);
  }

  public void setName(String name){
    this.name = name;
  }

  public void setContinent(String continent){
    this.continent = continent;
  }

  public String getName(){
    return name;
  }

  public String getContinent(){
    return continent;
  }

  @Override
  public String toString(){
    return "[Name: "+getName()+", Continent: "+getContinent()+"]";
  }

}
