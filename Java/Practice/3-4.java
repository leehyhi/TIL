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
		
		System.out.println(shop.getOrderNum());
		System.out.println(shop.getOrderID());
		System.out.println(shop.getOrderDate());
		System.out.println(shop.getOrderName());
		System.out.println(shop.getOrderPN());
		System.out.println(shop.getOrderAddr());

	}

}
