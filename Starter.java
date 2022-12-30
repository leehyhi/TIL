package Final;

import java.util.ArrayList;
import java.util.HashMap;

public class Starter {
	
	public static void main(String[] args) {
  
  Ironman i = new Ironman(100);
  Thor t = new Thor(100);
  Warmachine w = new Warmachine(100);
  Hulk h = new Hulk(100);
  
  ArrayList<Hero> heroes = new ArrayList<Hero>();
  HashMap<String, Hero> m_heroes = new HashMap<String, Hero>();
  
  heroes.add(i);
  heroes.add(t);
  heroes.add(w);
  heroes.add(h);
  
  System.out.println("=== Use ArrayList Call Attack each Heroes ===");
  
  for(int k = 0;k< heroes.size();k++)
  {
  heroes.get(k).attack();
  }
  
  m_heroes.put("Ironman", i);
  m_heroes.put("Thor", t);
  m_heroes.put("Warmachine", w);
  m_heroes.put("Hulk", h);
  
  System.out.println("\n=== Use Hash Map Call Thor ===");
  m_heroes.get("Thor").attack();
  
  }

}
