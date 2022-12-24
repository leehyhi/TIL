package Final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Hero {
private int hp;
	
	public Hero()
	{
		this.hp = 0;
		}
	
	public Hero(int hp)
	{
		this.hp = hp;
		}
	
	abstract void attack();
	abstract void defence();
	
	public void rest()
	{
		System.out.println("Current HP : " + Integer.toString(hp));
		hp += 100;
		System.out.println("After Rest HP : " + Integer.toString(hp));
		}
	
	public void setHP(int hp)
	{
		this.hp += hp;
		}
	
	public int getHP()
	{
		return this.hp;
		}
}
