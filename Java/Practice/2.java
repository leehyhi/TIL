//public class Student {
//
//	public static void main(String[] args) {
//		int studentID;
//		String studentName;
//		int grade;
//		String address;
//		
//		public String getStudentName() {
//			return studentName;
//		}
//		
//		public void setStudentName(String name) {
//			studentName = name;
//		}
//	}
//
//}

public class Student {
	public static void main(String[] args) {
		int num1 = 10;
		int num2 = 20;
		
		int sum = add(num1, num2);
		System.out.println(num1 + " + " + num2 + " = " + sum + "입니다.");
	}
	public static int add(int n1, int n2) {
		int result = n1 + n2;
		return result;
	}
}
