package testing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;


public class CustomConsole extends JFrame /* or JDialog*/ {
	
	private int width = 600;
	private int TAheight = 500;
	private int TFheight = 100;
	private String nextLine = "\n";
	private JTextArea ConsoleTA;
	private JTextField ConsoleInputTF;
	
	private TextAreaOutputStream taos;
	private PrintStream ps;
	
	private BufferedReader br;
	
	private Process subProcess;
	
	private JButton runbtn = new JButton("run");
	
	private ActionListener enterListener = e->{
		String temp = this.ConsoleInputTF.getText() + "\n";
		this.ConsoleInputTF.setText("");
		this.ConsoleTA.append(temp);
	};
	
	public CustomConsole() {
		super();
		this.setTitle("Console");
		
		JPanel cp = new JPanel();
		cp.setLayout(new BorderLayout());
		
		this.ConsoleTA = new JTextArea();
		//this.ConsoleTA.setPreferredSize(new Dimension(this.width,this.TAheight));
		this.ConsoleTA.setEditable(false);
		JScrollPane scroll = new JScrollPane(this.ConsoleTA);
		cp.add(scroll,BorderLayout.CENTER);
		
		//Testing, redirection of System.out
		this.taos = new TextAreaOutputStream(this.ConsoleTA);
		this.ps = new PrintStream(taos);
	//	System.setOut(ps);
	//	System.setErr(ps);
		
		
		
		
		this.ConsoleInputTF = new JTextField();
		this.ConsoleInputTF.setPreferredSize(new Dimension(this.width, this.TFheight));
		this.ConsoleInputTF.addActionListener(this.enterListener);
		cp.add(this.ConsoleInputTF, BorderLayout.SOUTH);
		
		//Testing redirection of System.in
		//TextFieldStreamer ts = new TextFieldStreamer(this.ConsoleInputTF);
		//this.ConsoleInputTF.addActionListener(ts);
		//System.setIn(ts);
		
		// run button
		this.runbtn.addActionListener(e-> compileAndRunSubprocess());
		runbtn.setPreferredSize(new Dimension(50,50));
		cp.add(this.runbtn, BorderLayout.EAST);
		
		this.setContentPane(cp);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       // Exit the program when the close-window button clicked
	    setSize(new Dimension(width, TAheight + TFheight));   // "super" JFrame sets initial size
	    setVisible(true);    // "super" JFrame shows
	    
	    
	    // Redirect System.out
	/*    PipedOutputStream pOut = new PipedOutputStream();
		System.setOut(new PrintStream(pOut));
		PipedInputStream pIn;
		try {
			pIn = new PipedInputStream(pOut);
			this.br = new BufferedReader(new InputStreamReader(pIn));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		// Compile and run
		System.out.println("Random Text...");
		System.out.println("SecondLine");
		
	//	this.compileAndRunSubprocess();
	}
	
	private void compileAndRunSubprocess(){
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
	    //	pb.inheritIO();
	    //	pb.redirectInput(Redirect.INHERIT);
	    //	pb.redirectError(Redirect.INHERIT);
	    // 	pb.redirectOutput(Redirect.INHERIT);
	    	
	    	this.subProcess = pb.start();
	    	
	    	//Testing
	    	OutputStream out = subProcess.getOutputStream();
	    	InputStream in = subProcess.getInputStream();
	    	//System.out.println("out instanceof BufferedOutputStream : " + (out instanceof BufferedOutputStream));
	    	//System.out.println("in instanceof BufferedInputStream : " + (in instanceof BufferedInputStream));
	    	
	    	//MyPipe writePipe = new MyPipe(in,ps); // Redirect subProcess ---in---> ps
	    	
	    	//Testing pipes
	    	MyPipe writePipe = new MyPipe(in, ps);
	    //	MyPipe readPipe = new MyPipe(System.in, out);
	    	
	    	// Redirect TextField -- TextFieldStreamr ---> subprocess
	    	//TextFieldStreamer tfStreamer = new TextFieldStreamer(this.ConsoleInputTF);
	    	//this.ConsoleInputTF.addActionListener(tfStreamer);
	    	//MyPipe readPipe = new MyPipe(tfStreamer,ps);
	    	
	    	int exitCode = this.subProcess.waitFor();
	    	//System.out.println("exitCode = " + exitCode);
	    	
	    	// output with thread
	    	//Thread outputThread = new B(this.subProcess);
	    	//outputThread.start();
	    	
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class B extends Thread{
	    Process proc;
	    public B(Process proc){
	        this.proc=proc;
	    }
	    public void run(){
	        try{
	        	String line="";
	            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	            while((line=br.readLine())!=null){
	                System.out.println(line);
	            }
	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        }
	    }
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            CustomConsole cc = new CustomConsole(); // Let the constructor do the job
	         }
	    });
		
	}
	
}
