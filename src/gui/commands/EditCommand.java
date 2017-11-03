package gui.commands;

import org.json.JSONObject;

import gui.interfaces.Command;
import gui.object.BlockFD;

public class EditCommand implements Command{
	JSONObject oldInputDetail;
	JSONObject newInputDetail;
	
	JSONObject model;
	
	BlockFD block;
	
	public EditCommand(BlockFD block, JSONObject newInputDetail) {
		this.block = block;
		this.model = block.getModel();
		this.newInputDetail = newInputDetail;
		this.oldInputDetail = obtainInputDetailFromModel(model);
	}
	
	public void replaceInputDetailOfModel(JSONObject model, JSONObject someInputDetail) {
		if(model.getString("Type").equals("While")) {
			model.put( "Expression", someInputDetail.getString("Expression") );
		}else if(model.getString("Type").equals("For")) {
			
		}// and any others
	}
	
	public JSONObject obtainInputDetailFromModel(JSONObject model) {
		JSONObject inputDetail = new JSONObject();
		if(model.getString("Type").equals("While")) {
			inputDetail.put( "Expression", model.getString("Expression") );
		}else if(model.getString("Type").equals("For")) {
			
		}// and any others
		
		return inputDetail;
	}
	
	
	@Override
	public void execute() {
		this.replaceInputDetailOfModel(this.model, this.newInputDetail);
		this.block.updateBlock();
	}

	@Override
	public void undo() {
		this.replaceInputDetailOfModel(this.model, this.oldInputDetail);
		this.block.updateBlock();
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public boolean isCollapsible(Command command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collapse(Command comand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
