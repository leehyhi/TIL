package First;

public class ShopTest {

	public static void main(String[] args) {
		Shop shop = new Shop();
		shop.OrderNum = 201803120001L;
		shop.OrderID = "abc123";
		shop.OrderDate = "2018년 3월 2일";
		shop.OrderName = "Lhy";
		shop.OrderPN = "PD0345-12";
		shop.OrderAddr = "서울시 영등포구 여의도동 20번지";
		
		System.out.println(shop.OrderNum);
		System.out.println(shop.OrderID);
		System.out.println(shop.OrderDate);
		System.out.println(shop.OrderName);
		System.out.println(shop.OrderPN);
		System.out.println(shop.OrderAddr);

	}

}
