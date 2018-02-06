package gui.commands;

import org.json.JSONObject;

import gui.interfaces.Command;
import gui.object.BlockFD;

public class EditCommand implements Command{
	JSONObject oldModel;
	JSONObject newInputDetail;
	
	JSONObject model;
	
	BlockFD block;
	
	public EditCommand(BlockFD block, JSONObject newInputDetail) {
		this.block = block;
		this.model = block.getModel();
		this.newInputDetail = newInputDetail;
		this.oldModel = cloneJSONObject(model);

		//Testing
		//for(String temp : newInputDetail.keySet()) {
		//	System.out.println(temp);
		//}
		
	}
	
	public void replaceInputDetailOfModel(JSONObject model, JSONObject someInputDetail) {
		for(String temp : newInputDetail.keySet()) {
			model.put( temp, someInputDetail.get(temp) );
		}
	}
	
	public JSONObject cloneJSONObject(JSONObject model) {
		JSONObject newJSONObj = new JSONObject();
		
		for(String temp : model.keySet()) {
			newJSONObj.put( temp, model.get(temp) );
		}
		
		return newJSONObj;
	}
	
	
	@Override
	public void execute() {
		this.replaceInputDetailOfModel(this.model, this.newInputDetail);
		this.block.updateBlockContent();
	}

	@Override
	public void undo() {
		this.replaceInputDetailOfModel(this.model, this.oldModel);
		this.block.updateBlockContent();
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
