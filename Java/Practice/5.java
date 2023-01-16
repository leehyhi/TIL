//package One;
//
//class BirthDay {
//	int day;
//	int month;
//	int year;
//	
//	public void setYear(int year) {
//		this.year = year;
//	}
//	
//	public void printThis() {
//		System.out.println(this);
//	}
//}
//
//public class ThisExample {
//	public static void main(String[] args) {
//		BirthDay bDay = new BirthDay();
//		bDay.setYear(2000);
//		System.out.println(bDay);
//		bDay.printThis();
//	}
//}

package One;

class Person {
	String name;
	int age;
	
	person() {
		this("이름 없음", 1);
	}
	
	Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

public class CallAnotherConst {
	public static void main(String[] args) {
		Person noName = new Person();
		System.out.println(noName.name);
		System.out.println(noName.age);
	}
}
