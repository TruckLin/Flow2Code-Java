package testing;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.Scanner;

public class ScannerExample{
    public static void main(String[] args){
        int x;
        
        //Testing
        System.out.println("In subProcess : ");
        
        Scanner in = new Scanner(System.in);
        x = in.nextInt();
       // in.close();
        try {
        	System.out.println("Waiting");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println( "Number entered is : " + x );

    }
}
