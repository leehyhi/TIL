package Final;

public class Thor extends Hero {
	
	public Thor(int i) {
		super(i);
		}
	
	public void attack()
	{
		System.out.println("Attack : Hammer");
		setHP(-2);
		}
	
	public void defence()
	{
		System.out.println("Defence : Hammer");
		setHP(-3);
		}
}
