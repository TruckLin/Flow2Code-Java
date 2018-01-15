package testing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream{

	private JTextArea ta;

	public TextAreaOutputStream(JTextArea ta) {
		this.ta = ta;
	}
	
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
		ta.append(String.valueOf( (char)b ) );
	}

}
