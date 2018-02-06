package strategy.codegenerator;

import java.util.HashMap;

import org.json.JSONObject;

import gui.codeView.TextBranch;
import gui.codeView.TextTree;
import gui.object.BlockFD;
import strategy.generator.ModelGenerationProcess;

public abstract class CodeGenerator{
	protected HashMap<String, CodeGenerationProcess> registry = new HashMap<String, CodeGenerationProcess>();
	
	public void register(String modelType, CodeGenerationProcess process) {
		registry.put(modelType, process);
	}
	
	
	public TextTree generate(JSONObject model,TextBranch textBranch, String indent) {
		//Testing
		//System.out.println("model.getString(\"Type\") = " + model.getString("Type"));
		
		return registry.get(model.getString("Type")).generateCode(model, textBranch,indent);
	}
	
}
