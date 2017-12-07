package strategy.codegenerator;

import org.json.JSONObject;

import gui.manager.NameCounterManager;
import strategy.generator.ModelGenerator;

public interface CodeGenerationProcess {
	public String generateCode(JSONObject model, String indent);
}
