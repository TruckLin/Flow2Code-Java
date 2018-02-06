package gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class AssignTableModel extends AbstractTableModel{
	 private String[] columnNames = {"Variable",
					"",
					"Expression"};
	
	private JSONObject model;
	private JSONArray assignmentsModel;
	private ArrayList<ArrayList<Object>> assignmentsData = new ArrayList<ArrayList<Object>>();
	
	public AssignTableModel(JSONObject model) {
		this.model = model;
		this.assignmentsModel = this.model.getJSONArray("Assignments");
		int numOfAssignments = this.assignmentsModel.length();
		
		// Testing
		//System.out.println(numOfVariables);
		
		for(int i = 0; i < numOfAssignments; i++) {
			JSONObject currentVariable = this.assignmentsModel.getJSONObject(i);
			
			//Testing 
			//System.out.println(currentVariable.toString());
			
			ArrayList<Object> tempVar = new ArrayList<Object>();
			
			tempVar.add(currentVariable.getString("TargetVariable"));
			tempVar.add(" = ");
			tempVar.add(currentVariable.getString("Expression"));
			
			this.assignmentsData.add(tempVar);
		}
	
	}
	
	/** Getters and Setters **/
	public  ArrayList<ArrayList<Object>> getData(){
		return this.assignmentsData;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		//Testing
		//System.out.println("In declareTableModel's getRowCount() function : ");
		//System.out.println("variableData.size() = " + variableData.size());
	
		return assignmentsData.size();
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.assignmentsData.get(rowIndex).get(columnIndex);
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/*
	* JTable uses this method to determine the default renderer/
	* editor for each cell.  If we didn't implement this method,
	* then the last column would contain text ("true"/"false"),
	* rather than a check box.
	*/
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
	
	/*
	* Don't need to implement this method unless your table's
	* editable.
	*/
	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		
		//Testing
		//System.out.println("row = " + row + ", column = " + col);
		//System.out.println((boolean)getValueAt(row,2));
		
		if (col == 1) {
		//Testing
		//System.out.println((boolean)getValueAt(row,2));
		
			return false;
		} else {
			return true;
		}
	}
	
	/*
	* Don't need to implement this method unless your table's
	* data can change.
	*/
	public void setValueAt(Object value, int row, int col) {
		this.assignmentsData.get(row).remove(col);
		this.assignmentsData.get(row).add(col, value);
		fireTableCellUpdated(row, col);
	}
	
	/*
	* Function that adds a new variable
	* */
	public void addNewVariable() {
		ArrayList<Object> tempVar = new ArrayList<Object>();
		tempVar.add("NewVariable");
		tempVar.add(" = ");
		tempVar.add("0");
		this.assignmentsData.add(tempVar);
		this.fireTableDataChanged();
	}
	/*
	* Function that remove rows
	* */
	public void removeVariables(int[] selectedRows) {
		for(int i = 0; i < selectedRows.length; i++) {
			this.assignmentsData.remove(selectedRows[i] - i);
		}
		this.fireTableDataChanged();
		}
	
	}
