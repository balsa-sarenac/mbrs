package myplugin.generator.fmmodel;

public class FMComponent {

	private String name;
	private FMForm form;
	private FMTableView tableView;

	public FMComponent() {

	}

	public FMComponent(String name, FMForm form, FMTableView tableView) {
		this.name = name;
		this.form = form;
		this.tableView = tableView;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FMForm getForm() {
		return form;
	}

	public void setForm(FMForm form) {
		this.form = form;
	}

	public FMTableView getTableView() {
		return tableView;
	}

	public void setTableView(FMTableView tableView) {
		this.tableView = tableView;
	}

}
