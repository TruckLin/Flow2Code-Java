package testing;

import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

//import temp.ForLoop;

public class test {
	public static void main(String args[]) {
	//	ForLoop.main(null);
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
	      Process p = Runtime.getRuntime().exec("cmd.exe /c start cmd /k java ForLoop");
	      //p = Runtime.getRuntime().exec("cmd.exe /c start");
	      p.waitFor();
	      System.out.println("ok");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
