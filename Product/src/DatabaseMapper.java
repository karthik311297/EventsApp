
public class DatabaseMapper {
	private String appId;
	private String serialNumber;
	private int isAssociated;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getIsAssociated() {
		return isAssociated;
	}
	public void setIsAssociated(int isAssociated) {
		this.isAssociated = isAssociated;
	}
	
	public DatabaseMapper(String appId,String serialNumber,int isAssociated){
		this.appId=appId;
		this.serialNumber=serialNumber;
		this.isAssociated=isAssociated;
	}
	
}
