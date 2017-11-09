package strategy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import gui.manager.NameCounterManager;
import gui.object.BlockFD;
import gui.object.BlockStartLOOP;
import saveload.SaveAndLoadManagerFD;

public class TestBlockGenerator extends JFrame{
	
	public TestBlockGenerator() {
	    //JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON("/Demo-WhileLoop.json");
	    JSONObject myModel = SaveAndLoadManagerFD.loadFlowDiagramFromJSON("/FlowDiagramDemo.json");
	    //JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON("/Demo-WhileLoop-info.json");
	    JSONObject myInfo = SaveAndLoadManagerFD.loadGraphicalInfoFromJSON("/FlowDiagramDemo-info.json");
		NameCounterManager nameManager = new NameCounterManager();
		BlockGenerator processor = new BlockGenerator();
		
		

		
		BlockFD flowDiagram = processor.generate(myModel,myInfo);
		
		
		this.setContentPane(flowDiagram);
		this.setSize(500,500);
		
		this.setVisible(true);
	}
		
	public static void main(String[] args) {
		// Run the GUI construction in the Event-Dispatching thread for thread-safety
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new TestBlockGenerator(); // Let the constructor do the job
	         }
	    });
	}

}
