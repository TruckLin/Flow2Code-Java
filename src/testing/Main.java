package testing;

import java.util.Scanner;

public class Main {
	    public static void main(String[] args){
	        Scanner sc = new Scanner(System.in);

	        int cpuNum;
	        int myGuess;

	        cpuNum = (int)Math.round(Math.random()*1000);
	        myGuess = sc.nextInt();
	        while( myGuess != cpuNum ) {

	            if( cpuNum > myGuess ) {
	                System.out.println( "Higher" );
	            } else {
	                System.out.println( "Lower" );
	            }
	            myGuess = sc.nextInt();
	        }
	        System.out.println( "Correct " );
	        sc.close();
	    }
}
