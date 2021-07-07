package myplugin.generator.fmmodel;

import java.util.HashMap;
import java.util.Map;

public class FMTableView {

	Map<String, String> columnNames = new HashMap<String, String>();

	public FMTableView() {

	}

	public FMTableView(Map<String, String> columnNames) {
		this.columnNames = columnNames;
	}

	public Map<String, String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Map<String, String> columnNames) {
		this.columnNames = columnNames;
	}
	
	
}
