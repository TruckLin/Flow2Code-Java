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
		BatchCompiler.compile(
		   "-encoding UTF-16 FlowCode.java",
		   new PrintWriter(System.out),
		   new PrintWriter(System.err),
		   progress);
		
		
		
		try {
			Process p = Runtime.getRuntime().exec("java FlowCode");
			
			
			BufferedReader in = new BufferedReader(  
                    new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			
			OutputStream out = p.getOutputStream(); // out from parent process into sub-processs.
			BufferedWriter myOut = new BufferedWriter(new OutputStreamWriter(out));
			Scanner sc = new Scanner(System.in);
	        
	        out.write(50);
			
			//System.out.println("output stream is null?" + (out==null));
			
			
			
	        sc.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
