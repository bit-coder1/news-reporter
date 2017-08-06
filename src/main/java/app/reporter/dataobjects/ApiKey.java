package app.reporter.dataobjects;

public class ApiKey {

	private String resourceId;
	private String apiKeyName;
	private String apiKeyValue;
	private KeyPassedByMethodEnum passedBy;

	public enum KeyPassedByMethodEnum {
		REQUEST_PARAM, HEADER
	};

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public KeyPassedByMethodEnum getPassedBy() {
		return passedBy;
	}

	public void setPassedBy(KeyPassedByMethodEnum passedBy) {
		this.passedBy = passedBy;
	}

	public String getApiKeyName() {
		return apiKeyName;
	}

	public void setApiKeyName(String apiKeyName) {
		this.apiKeyName = apiKeyName;
	}

	public String getApiKeyValue() {
		return apiKeyValue;
	}

	public void setApiKeyValue(String apiKeyValue) {
		this.apiKeyValue = apiKeyValue;
	}

}
