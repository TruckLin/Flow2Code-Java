package testing;

import java.io.IOException;

public class test {
	public static void main(String args[]) {
	    try {
	        //Runtime.getRuntime().exec("cmd.exe /c start java ForLoop");
	       Runtime.getRuntime().exec("cmd.exe /c start");
	       System.out.println("ok");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
}
