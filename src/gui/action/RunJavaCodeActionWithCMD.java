package gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import com.tcg.json.FileHandle;

import gui.codeView.CodeViewContainer;

public class RunJavaCodeActionWithCMD implements ActionListener{
	
	private CodeViewContainer codeViewContainer;
	
	public RunJavaCodeActionWithCMD(CodeViewContainer codeViewContainer) {
		this.codeViewContainer = codeViewContainer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//Testing
		String oldDir =  System.getProperty("user.dir");
		System.out.println("Working Directory = " + oldDir);
//		System.setProperty("user.dir", oldDir + "\\temp");
//		System.out.println("Working Directory = " +
//	              System.getProperty("user.dir"));
		
		String encoding = 
				"-encoding UTF-16 ";
			//	"";
		String path = 
			//	"";
				".\\temp\\";
		String fileName = 
				"FlowCode";
			//	"Add2Numbers";
				//"ForLoop";
		String fileExtension = 
				".java";
		
		Runtime run = Runtime.getRuntime();
		String javaCode = codeViewContainer.getCodeViewTextPane().getText();
		FileHandle.saveTextFileFromString(codeViewContainer.getCodeViewTextPane().getText(), ".\\temp\\FlowCode.java");
		
		CompilationProgress progress = null; // instantiate your subclass
		
		
		//Testing
		//System.out.println(encoding + fileName + fileExtension);
		
		BatchCompiler.compile(
			encoding + 
			path + 
			fileName + 
			fileExtension,
		    new PrintWriter(System.out),
		    new PrintWriter(System.err),
		    progress);
		
		
	    try {
	      Process p = Runtime.getRuntime().exec("cmd.exe /c start cmd /k java -cp " + ".\\temp " +fileName);
	      p.waitFor();

	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	    
	   // System.setProperty("user.dir", oldDir);

	}
}
