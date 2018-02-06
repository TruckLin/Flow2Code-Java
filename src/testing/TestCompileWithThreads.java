package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TestCompileWithThreads {
	
	private class A extends Thread{
	    Process proc;
	    public A(Process proc){
	        this.proc=proc;
	    }
	    
	    public void run(){
	        BufferedReader br = new BufferedReader(new       InputStreamReader(System.in));
	        OutputStream out = proc.getOutputStream();
	        try{
	            while(proc.isAlive()){   
	                String s = br.readLine();
	                out.write(s.getBytes());
	                out.flush();
	                out.close();
	                
	                //Testing
	                System.out.println("thread A, while loop");
	            }
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	    }
	}
	private class B extends Thread{
	    Process proc;
	    public B(Process proc){
	        this.proc=proc;
	    }
	    public void run(){
	        try{
	        	String line="";
	            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	            while((line=br.readLine())!=null){
	                System.out.println(line);
	            }
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	    }
	} 
	
	public static void main(String args[]){
        try{
        	TestCompileWithThreads obj = new TestCompileWithThreads();
            String command="java Add2Numbers";
            Process proc=Runtime.getRuntime().exec(command);
            Thread t1 = obj.new A(proc);
            t1.start();
            Thread t2 = obj.new B(proc);
            t2.start();
            proc.waitFor();
            t1.join();
            t2.join();
       }
       catch(Exception e){
    	   
       }
    }

}
