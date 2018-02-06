package testing;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class MyPipe {
	
	private InputStream in;
	private OutputStream out;
	
	public MyPipe(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		
		transferThread thr = new transferThread();
		thr.run();
	}
	
	private class transferThread extends Thread {
		@Override
		public void run() {
			int temp;
			try {
				System.out.println("Thread number : " + this.getName() );
				
				
				while( (temp = in.read()) != -1) {
					out.write(temp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
