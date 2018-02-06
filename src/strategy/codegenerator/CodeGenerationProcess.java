package strategy.codegenerator;

import org.json.JSONObject;

import gui.codeView.TextBranch;
import gui.codeView.TextTree;
import gui.manager.NameCounterManager;
import strategy.generator.ModelGenerator;

public interface CodeGenerationProcess {
	public TextBranch generateCode(JSONObject model, TextBranch code, String indent);
}
