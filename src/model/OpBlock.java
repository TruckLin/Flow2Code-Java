package model;

import java.util.ArrayList;

import org.json.*;

public class OpBlock {
	private String Name;
	private String Type;
	private ArrayList<String> Children;
	
	public OpBlock(String name, String type, ArrayList<String> children) {
		this.Name = name;
		this.Type = type;
		this.Children = children;
	}
	
	public OpBlock(JSONObject obj) {
		this.Name = obj.getString("Name");
		this.Type = obj.getString("Type");
		// Some component like "End" might not have any children, hence the value of children is null.
		if(obj.isNull("Children")) {
			this.Children = new ArrayList<String>();
		}else{
			JSONArray cList = obj.getJSONArray("Children");

			this.Children = new ArrayList<String>();
			for(int i = 0; i < cList.length(); i++) {
				this.Children.add( cList.getString(i) );
			}
		}
	}
	
	public String toString() {
		String temp = "{\n";
		temp = temp + "Name : " + Name + "\n";
		temp = temp + "Type : " + Type + "\n";
		temp = temp + "Children : " + Children.toString() + "\n}\n";
		
		//Testing
		//System.out.println("toString() in OpBlock is called");
		
		return temp;
	}
	
	public static void main(String[] args) {
		

	}

}
