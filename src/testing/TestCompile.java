package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

public class TestCompile {
	
	private Process subProcess;
	private OutputStream toSub;
	private InputStream fromSub;
	private InputStream stderr;
	
	public TestCompile() {
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
			
			//Testing
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(toSub));
			out.write("50");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		new TestCompile();
	}
	
}
