package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ResultSetTableModel extends DefaultTableModel{
	public ResultSetTableModel(Vector data,Vector columnNames) 
	{
		super(data,columnNames); 
	}
	public Class getColumnClass(int col) {
		Vector v=(Vector)dataVector.elementAt(0);
		return v.elementAt(col).getClass(); 
	}	
}