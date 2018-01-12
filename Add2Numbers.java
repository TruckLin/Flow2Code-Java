
import java.util.Scanner;
public class Add2Numbers {
   public static void main(String[] args) {
	   
	   
	   
      Scanner in = new Scanner(System.in);
      
     // System.out.println("Enter first number : ");
      int num1 = in.nextInt();
     // System.out.println("Enter second number : ");
      int num2 = in.nextInt();
      System.out.println(num1 + num2);
      
      in.close();
   }
}