package myplugin.generator.fmmodel;

public class FMUIComponent {

	private String idName;
	private String label;
	private Boolean visible;
	private FMType type;
	private ComponentTypeEnum componentTypeEnum;
	private ComponentShowTypeEnum componentShowTypeEnum;
	private Boolean editable = false;
	private Boolean calculated = false;
	private Boolean next = false;
	private Boolean zoom = false;
	private Boolean hierarchy = false;
	private String formula;
	private String level;
	private Boolean isKey;
	private Integer upper;

	public FMUIComponent() {

	}

	public FMUIComponent(String idName, String label, Boolean visible, FMType type, ComponentTypeEnum componentTypeEnum,
			ComponentShowTypeEnum componentShowTypeEnum, Boolean editable, Boolean calculated, Boolean next,
			Boolean zoom, Boolean hierarchy, String formula, String level, Boolean isKey, Integer upper) {
		super();
		this.idName = idName;
		this.label = label;
		this.visible = visible;
		this.type = type;
		this.componentTypeEnum = componentTypeEnum;
		this.componentShowTypeEnum = componentShowTypeEnum;
		this.editable = editable;
		this.calculated = calculated;
		this.next = next;
		this.zoom = zoom;
		this.hierarchy = hierarchy;
		this.formula = formula;
		this.level = level;
		this.isKey = isKey;
		this.upper = upper;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setType(FMType type) {
		this.type = type;
	}

	public FMType getType() {
		return type;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public ComponentTypeEnum getComponentTypeEnum() {
		return componentTypeEnum;
	}

	public void setComponentTypeEnum(ComponentTypeEnum componentTypeEnum) {
		this.componentTypeEnum = componentTypeEnum;
	}
	
	public ComponentShowTypeEnum getComponentShowTypeEnum() {
		return componentShowTypeEnum;
	}

	public void setComponentShowTypeEnum(ComponentShowTypeEnum componentShowTypeEnum) {
		this.componentShowTypeEnum = componentShowTypeEnum;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getCalculated() {
		return calculated;
	}

	public void setCalculated(Boolean calculated) {
		this.calculated = calculated;
	}

	public Boolean getNext() {
		return next;
	}

	public void setNext(Boolean next) {
		this.next = next;
	}

	public Boolean getZoom() {
		return zoom;
	}

	public void setZoom(Boolean zoom) {
		this.zoom = zoom;
	}

	public Boolean getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Boolean hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Boolean getIsKey() {
		return isKey;
	}

	public void setIsKey(Boolean isKey) {
		this.isKey = isKey;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

}
