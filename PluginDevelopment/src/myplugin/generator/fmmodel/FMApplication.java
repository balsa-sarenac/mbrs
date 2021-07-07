package myplugin.generator.fmmodel;

public class FMApplication {

	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private String dbType;
	private String appHost;
	private String appPort;
	private String appName;
	private String appDescription;
	private String appContextPath;

	public FMApplication() {

	}

	public FMApplication(String dbUrl, String dbUsername, String dbPassword, String dbType, String appHost,
			String appPort, String appName, String appDescription, String appContextPath) {
		super();
		this.dbUrl = dbUrl;
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		this.dbType = dbType;
		this.appHost = appHost;
		this.appPort = appPort;
		this.appName = appName;
		this.appDescription = appDescription;
		this.appContextPath = appContextPath;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	public String getAppPort() {
		return appPort;
	}

	public void setAppPort(String appPort) {
		this.appPort = appPort;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public String getAppContextPath() {
		return appContextPath;
	}

	public void setAppContextPath(String appContextPath) {
		this.appContextPath = appContextPath;
	}

}
