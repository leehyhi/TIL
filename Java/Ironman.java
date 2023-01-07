package Final;

public class Ironman extends Hero {
	public Ironman(int i) {
		super(i);
	}

	public void attack()
	{
		System.out.println("Attack : Beam");
		setHP(-1);
		}
	
	public void defence()
	{
		System.out.println("Defence : shield");
		setHP(-2);
		}
}
