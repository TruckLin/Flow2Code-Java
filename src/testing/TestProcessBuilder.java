package testing;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;

import javax.crypto.interfaces.PBEKey;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

public class TestProcessBuilder {
	public static void main(String args[]) {
		CompilationProgress progress = null; // instantiate your subclass
		String encoding = 
				//	"-encoding UTF-16 ";
					"";
			String fileName = 
				//	"FlowCode";
				//	"Add2Numbers";
					"ForLoop";
			String fileExtension = 
					".java";
			
			BatchCompiler.compile(
				encoding + fileName + fileExtension,
			    new PrintWriter(System.out),
			    new PrintWriter(System.err),
			    progress);
		
	    try {
	    	
	    	ProcessBuilder pb = new ProcessBuilder("java",fileName);
	    	pb.redirectInput(Redirect.INHERIT);
	    	pb.redirectError(Redirect.INHERIT);
	    	pb.redirectOutput(Redirect.INHERIT);
	    	Process p = pb.start();
	    	int exitCode = p.waitFor();
	    	System.out.println("exitCode = " + exitCode);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
