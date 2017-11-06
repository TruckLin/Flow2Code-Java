package gui.interfaces;

public interface Command {
	void execute();
	
	void undo();
	
	void redo();
	
	boolean isCollapsible(Command command);
	
	void collapse(Command comand);
	
	String getName();
}
