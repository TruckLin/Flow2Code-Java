package gui.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

import gui.codeView.CodeViewContainer;
import gui.manager.SaveAndLoadManagerFD;

public class RunJavaCodeActionWithCMD implements ActionListener{
	
	private CodeViewContainer codeViewContainer;
	
	public RunJavaCodeActionWithCMD(CodeViewContainer codeViewContainer) {
		this.codeViewContainer = codeViewContainer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//Testing
//		String oldDir =  System.getProperty("user.dir");
//		System.out.println("Working Directory = " + oldDir);
//		System.setProperty("user.dir", oldDir + "\\temp");
//		System.out.println("Working Directory = " +
//	              System.getProperty("user.dir"));
		String systemTempFloder = 
				System.getProperty("java.io.tmpdir");
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
		String errorFilePath = 
				systemTempFloder + java.util.UUID.randomUUID();//"Flow2CodeError.txt";
		Runtime run = Runtime.getRuntime();
		String javaCode = codeViewContainer.getCodeEditPanel().getText();
		SaveAndLoadManagerFD.saveTextFileFromString(javaCode, ".\\temp\\FlowCode.java");
		
		CompilationProgress progress = null; // instantiate your subclass
		
		//Testing
		//System.out.println(encoding + fileName + fileExtension);
		String errorMsg;
		boolean success = false;
		try {
			success = BatchCompiler.compile(
					encoding + 
					path + 
					fileName + 
					fileExtension,
					new PrintWriter(System.out),
					new PrintWriter(errorFilePath),
				    progress);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Testing
		//System.out.println("Compilation success = " + success);
		//System.out.println(errorFilePath);
		if(success) {
		    try {
		    	Process p = Runtime.getRuntime().exec("cmd.exe /c start cmd /k java -cp " + ".\\temp " +fileName);
		    	p.waitFor();
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    } catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}else{
			try {
				BufferedReader in = new BufferedReader(new FileReader(errorFilePath));
				String line = "";
				String errorFile = "";
				while((line = in.readLine()) != null)
				{
					errorFile = errorFile + line;
					errorFile = errorFile + "\n";
				}
				in.close();
				UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 18));
				UIManager.put("OptionPane.messageForeground", Color.red);
				JOptionPane.showMessageDialog(null,errorFile,"ERROR",JOptionPane.WARNING_MESSAGE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	   // System.setProperty("user.dir", oldDir);

	}
}
