package myplugin.generator.fmmodel;

public class FMStandardForm extends FMForm {

	private Boolean create;
	private Boolean update;
	private Boolean delete;

	public FMStandardForm() {
		super();
	}

	public FMStandardForm(String name, Boolean create, Boolean update, Boolean delete) {
		super(name);
		this.create = create;
		this.update = update;
		this.delete = delete;
	}

	public Boolean isCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean isUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean isDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

}
