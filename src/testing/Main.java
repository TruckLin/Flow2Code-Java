package testing;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		


		        int myNum;
		        int usersGuess;

		        myNum = 314;
		        System.out.println( "Welcome to our game! \n Enter your first guess :" );
		        Scanner sc1 = new Scanner(System.in);
		        usersGuess = sc1.nextInt();
		      //  sc1.close();
		        
		        while( usersGuess != myNum ) {

		            System.out.println( "Unfortunate! Try again : " );
		       //     Scanner sc2 = new Scanner(System.in);
		            usersGuess = sc1.nextInt();
		       //     sc2.close();
		        }
		        
		        System.out.println( "Congrats ! You've got it !" );
		        sc1.close();
	}
}
