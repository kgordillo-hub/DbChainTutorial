package co.agro.blockchain.dto;

public class ProductDto {

	private String idProduct;
	private String idRfidTag;
	private String productName;
	private String productType;
	private String creationDate;
	
	public ProductDto() {
		//default constructor
	}

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}


	public String getIdRfidTag() {
		return idRfidTag;
	}

	public void setIdRfidTag(String idRfidTag) {
		this.idRfidTag = idRfidTag;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
