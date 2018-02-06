package gui.manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class NameCounterManager {
	
	private int count;
	
	public NameCounterManager() {
		this.count = 1;
	}
	
	public int getCurrentCount() {
		return count-1;
	}
	
	public void excludeExistingNames(JSONObject info) {
		int max = 0;
		for(String name : info.keySet()) {
			String regex = "[0-9*]";
			Pattern p = Pattern.compile("[0-9]*");
			Matcher m = p.matcher(name);
			boolean b = m.matches();
			//Testing
			//System.out.println("regex = " + regex + ",   target = " + name);
			//System.out.println(" matches ? " + b);
			
			if(b && Integer.parseInt(name) > max) {
				int temp = Integer.parseInt(name);
				if(temp > max) max = temp;
			}
		}
		this.count = max + 1;
	}
	
	public String getAvailableName() {
		int oldValue = count;
		this.count ++;
		return (oldValue + "");
	}
}
