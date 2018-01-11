package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

public class TestCompile {
	public static void main(String[] args) {
		CompilationProgress progress = null; // instantiate your subclass
		String encoding = 
			//	"-encoding UTF-16 ";
				"";
		String fileName = 
			//	"FlowCode.java";
				//"Add2Numbers.java";
				"ForLoop.java";
		String tempCommand = 
				encoding + fileName;
		
		BatchCompiler.compile(
			tempCommand,
		    new PrintWriter(System.out),
		    new PrintWriter(System.err),
		    progress);
		
		try {
			String temp = "java ForLoop";
			System.out.println(temp);
			//Runtime.getRuntime().exec("cmd.exe");
			Process p = Runtime.getRuntime().exec(temp);
			//Scanner sc = new Scanner(System.in);
			
			
			//sc.close();
			
			//Process p = Runtime.getRuntime().exec("java Add2Numbers");
			/*
			int exitValue;
			try {
				exitValue = p.waitFor();
				System.out.println("Process Completed with exit value of " + exitValue);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         */
			
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
