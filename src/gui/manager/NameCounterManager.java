package gui.manager;

public class NameCounterManager {
	
	private int count = 0;
	
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
