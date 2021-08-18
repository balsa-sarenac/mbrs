package myplugin.generator.fmmodel;

public class FMTableColumn {

	private String sourceName;
	private String columnName;
	private FMType type;
	private int upper;

	public FMTableColumn() {
		super();
	}

	public FMTableColumn(String sourceName, String columnName, FMType type, int upper) {
		super();
		this.sourceName = sourceName;
		this.columnName = columnName;
		this.type = type;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public FMType getType() {
		return type;
	}

	public void setType(FMType type) {
		this.type = type;
	}

	public int getUpper() {
		return upper;
	}

	public void setUpper(int upper) {
		this.upper = upper;
	}

}
