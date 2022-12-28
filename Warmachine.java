package Final;

public class Warmachine extends Ironman {
    
	int bomb;
	
	public Warmachine(int i) {
  super(i);
	}

	public void reloadBomb()
	{
  System.out.println("Reload Bomb");
	}
	
	public void attack()
	{
  System.out.println("Attack : Bombing");
	}
  
}

