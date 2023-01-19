package One;

public class Texi {
	int texiNum;
	int passengerCount;
	int money;
	
	public Texi(int texiNum) {
		this.texiNum = texiNum;
	}
	
	public void take(int money) {
		this.money += money;
		passengerCount++;
	}
	
	public void showinfo() {
		System.out.println("택시 번호는 " + texiNum + " 택시 승객은 " + passengerCount + "명" + " 택시 요금은 " + money + "입니다.");
	}

}
