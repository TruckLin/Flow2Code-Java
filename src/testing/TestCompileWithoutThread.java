package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

public class TestCompileWithoutThread {
	private Process subProcess;
	private OutputStream toSub;
	private InputStream fromSub;
	private InputStream stderr;
	
	public TestCompileWithoutThread() {
		CompilationProgress progress = null; // instantiate your subclass
		String encoding = 
			//	"-encoding UTF-16 ";
				"";
		String fileName = 
			//	"FlowCode";
				//"Add2Numbers";
				"ForLoop";
		String fileExtension = 
				".java";
		
		BatchCompiler.compile(
			encoding + fileName + fileExtension,
		    new PrintWriter(System.out),
		    new PrintWriter(System.err),
		    progress);
		
		String temp = "java " + fileName;
		try {
			this.subProcess = Runtime.getRuntime().exec(temp);
			this.toSub = this.subProcess.getOutputStream();
			this.fromSub = this.subProcess.getInputStream();
			this.stderr = this.subProcess.getErrorStream();
			BufferedReader pout = new BufferedReader(new InputStreamReader(this.fromSub));
			 
	         // Save the output in a StringBuffer for further processing
	         StringBuffer sb = new StringBuffer();
	         int ch;
	         try {
				while ((ch = pout.read()) != -1) {
				    sb.append((char)ch);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         System.out.println(sb);
	         
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//BufferedReader in = new BufferedReader(new InputStreamReader(fromSub));
		//if(subProcess.isAlive()) {
			//Testing
		/*	
		System.out.println("subProcess.isAlive() " + subProcess.isAlive());
	        String line;
	        try {
				while((line = in.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//}
	        
	     // Read from the standard output of the subprocess
	         
		
	}
	
	public static void main(String[] args) {
		new TestCompileWithoutThread();
	}
}
