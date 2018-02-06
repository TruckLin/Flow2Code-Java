package strategy.codegenerator;

import org.json.JSONObject;

import editor.system.testing.TextBranch;
import editor.system.testing.TextTree;
import gui.manager.NameCounterManager;
import strategy.generator.ModelGenerator;

public interface CodeGenerationProcess {
	public TextBranch generateCode(JSONObject model, TextBranch code, String indent);
}
