package graph.object;

import org.json.JSONObject;

public class BlockFlowDiagram extends BlockFD{
	
	public BlockFlowDiagram(JSONObject model) {
		super(model);
		this.setLayout(null);
	}
	
	/** Getters and Setters **/
	
	
	/** Utility Functions **/
	public void setAppropriateBounds() {
		super.setAppropriateBounds();
		// We need a minimum size for FlowDiagram Panel.
	}
}
