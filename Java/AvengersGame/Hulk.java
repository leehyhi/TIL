package Final;

public class Hulk extends Hero {
	
	public Hulk(int i) {
		super(i);
		}

	public void attack()
	{
		System.out.println("Attack : Punch");
		setHP(-3);
		}
	
	public void defence()
	{
		System.out.println("Attack : Shoulder");
		setHP(-2);
		}
}
