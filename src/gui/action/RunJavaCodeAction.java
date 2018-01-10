package gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;

import com.tcg.json.FileHandle;

import gui.codeView.CodeViewContainer;

public class RunJavaCodeAction implements ActionListener{
	
	private CodeViewContainer codeViewContainer;
	
	public RunJavaCodeAction(CodeViewContainer codeViewContainer) {
		this.codeViewContainer = codeViewContainer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Runtime run = Runtime.getRuntime();
		String javaCode = codeViewContainer.getCodeViewTextPane().getText();
		FileHandle.saveTextFileFromString(codeViewContainer.getCodeViewTextPane().getText(), "FlowCode.java");
		
		/*
		try {
	        run.exec("javac tempcode.java");
	        Process p = run.exec("java tempcode");
	        DataInputStream dataIn = new DataInputStream(p.getInputStream());
	        
	        System.out.println("dataIn.available() > 0 is " + (dataIn.available() > 0));
	        while(dataIn.available() > 0) {
	        	System.out.println(dataIn.readChar());
	        }
	        
        } catch (IOException ex) {
            ex.printStackTrace();
       }
		*/
		//Testing
		System.out.println("RunJavaCodeAction is performed.");
		System.out.println("Code : \n" + javaCode);
	}
}
