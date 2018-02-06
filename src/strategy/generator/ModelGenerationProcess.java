package strategy.generator;

import org.json.JSONObject;

import gui.manager.NameCounterManager;

public interface ModelGenerationProcess {
	public JSONObject generateModel(ModelGenerator modelGenerator, NameCounterManager nameManger) ;
}
