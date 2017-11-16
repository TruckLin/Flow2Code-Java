package gui.manager;

import org.json.*;

import com.tcg.json.JSONUtils;


public abstract class SaveAndLoadManagerFD {
	
	public static JSONObject loadFlowDiagramFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	public static JSONObject loadGraphicalInfoFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
}