import java.util.Scanner;

//import java.util.Scanner;


public class ForLoop {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		 
		   int i;
		   for(i = 1; i <= 20; i++) {
			   System.out.println("i = " + i);
			 //  System.out.print("Enter your number : ");
			//   System.out.print(sc.nextLine() + "\n");
		   }
		   
		System.out.println("Enter a number : ");
		int j = sc.nextInt();
		System.out.println("the number entered is : " + j);
		
		sc.close();
		   
		   
		  /*
	      Scanner in = new Scanner(System.in);
	      System.out.println("Enter first number : ");
	      int num1 = in.nextInt();
	      System.out.println("Enter second number : ");
	      int num2 = in.nextInt();
	      System.out.println(num1 + num2);
	      
	      in.close();
	      */
	   }
}
