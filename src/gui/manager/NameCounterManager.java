package gui.manager;

public class NameCounterManager {
	
	private int count;
	
	public NameCounterManager() {
		this.count = 1;
	}
	
	public int getCurrentCount() {
		return count-1;
	}
	
	public String getAvailableName() {
		int oldValue = count;
		this.count ++;
		return (oldValue + "");
	}
}
