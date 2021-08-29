package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMTableView {

	private List<FMTableColumn> columns = new ArrayList<FMTableColumn>();

	public FMTableView() {

	}

	public FMTableView(List<FMTableColumn> columns) {
		super();
		this.columns = columns;
	}

	public List<FMTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<FMTableColumn> columns) {
		this.columns = columns;
	}


}
