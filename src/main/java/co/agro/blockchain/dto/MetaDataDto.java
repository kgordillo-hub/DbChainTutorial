package co.agro.blockchain.dto;

public class MetaDataDto {
	
	//SQL basic data
	private String location;
	private String statusId;
	private String statusDescription;
	private String trazabilityId;
	private String orderId;
	private String transactionDate;
	private String transactionUsername;
	private String transactionCompany;
	//Extra data
	private String productDescription;
	private String productExpirationDate;
	private String vehicleNumber;
	private String vehicleType;
	private String productTemperature;
	
	public MetaDataDto() {
		
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getTrazabilityId() {
		return trazabilityId;
	}

	public void setTrazabilityId(String trazabilityId) {
		this.trazabilityId = trazabilityId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionUsername() {
		return transactionUsername;
	}

	public void setTransactionUsername(String transactionUsername) {
		this.transactionUsername = transactionUsername;
	}

	public String getTransactionCompany() {
		return transactionCompany;
	}

	public void setTransactionCompany(String transactionCompany) {
		this.transactionCompany = transactionCompany;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductExpirationDate() {
		return productExpirationDate;
	}

	public void setProductExpirationDate(String productExpirationDate) {
		this.productExpirationDate = productExpirationDate;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getProductTemperature() {
		return productTemperature;
	}

	public void setProductTemperature(String productTemperature) {
		this.productTemperature = productTemperature;
	}
	
}
