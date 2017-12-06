package gui;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class declareTableModel extends AbstractTableModel{
	 private String[] columnNames = {"Type",
             						"Variable Name",
             						"isArray?",
             						"Size"};
	 
	 private JSONObject model;
	 private JSONArray variables;
	 private ArrayList<ArrayList<Object>> variableData = new ArrayList<ArrayList<Object>>();
	 
	 public declareTableModel(JSONObject model) {
		 this.model = model;
		 this.variables = this.model.getJSONArray("Variables");
		 int numOfVariables = this.variables.length();
		 
		 // Testing
		 //System.out.println(numOfVariables);
		 
		 for(int i = 0; i < numOfVariables; i++) {
			 JSONObject currentVariable = this.variables.getJSONObject(i);
			 
			 //Testing 
			 //System.out.println(currentVariable.toString());
			 
			 ArrayList<Object> tempVar = new ArrayList<Object>();
			 
			 tempVar.add(currentVariable.getString("DataType"));
			 tempVar.add(currentVariable.getString("VariableName"));
			 tempVar.add(currentVariable.getBoolean("IsArray"));
			 tempVar.add(currentVariable.getInt("Size"));
			 
			 this.variableData.add(tempVar);
		 }
		 
	 }
	 
	 /** Getters and Setters **/
	 public  ArrayList<ArrayList<Object>> getData(){
		 return this.variableData;
	 }
	 
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		//Testing
		//System.out.println("In declareTableModel's getRowCount() function : ");
		//System.out.println("variableData.size() = " + variableData.size());
		
		return variableData.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.variableData.get(rowIndex).get(columnIndex);
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
    	
        if (col == 3) {
        	//Testing
        	//System.out.println((boolean)getValueAt(row,2));
            
        	return (boolean)getValueAt(row,2);
        } else {
            return true;
        }
    }
    
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        this.variableData.get(row).remove(col);
        this.variableData.get(row).add(col, value);
        fireTableCellUpdated(row, col);
    }
    
    /*
     * Function that adds a new variable
     * */
     public void addNewVariable() {
    	 ArrayList<Object> tempVar = new ArrayList<Object>();
    	 tempVar.add("Integer");
    	 tempVar.add("NewVariable");
    	 tempVar.add(Boolean.FALSE);
    	 tempVar.add(new Integer(1));
    	 this.variableData.add(tempVar);
    	 this.fireTableDataChanged();
     }
     /*
      * Function that remove rows
      * */
     public void removeVariables(int[] selectedRows) {
    	 for(int i = 0; i < selectedRows.length; i++) {
    		 this.variableData.remove(selectedRows[i] - i);
    	 }
    	 this.fireTableDataChanged();
     }
     
}
