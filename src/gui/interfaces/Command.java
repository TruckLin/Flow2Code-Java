package gui.interfaces;

public interface Command {
	void execute();
	
	void undo();
	
	void redo();
	
	String getName();
}
