package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class ItemForm {

	//	商品名
	@NotBlank(message="商品名を入力して下さい")
	private String name;
	
	//	商品説明
	@NotBlank(message="商品説明を入力して下さい")
	private String description;
	
	//	サイズMの価格
	@Pattern(regexp = "^[0-9]+$", message="価格は1以上の数字で入力して下さい")
	private String priceM;
	
	//	サイズLの価格
	@Pattern(regexp = "^[0-9]+$", message="価格は1以上の数字で入力して下さい")
	private String priceL;
	
	//	商品画像
	private MultipartFile imagePath;
	
	//	削除フラグメント
	private Boolean deleted;

	//	引数あり
	public ItemForm(String name, String description, String priceM, String priceL, MultipartFile imagePath,
			Boolean deleted) {
		this.name = name;
		this.description = description;
		this.priceM = priceM;
		this.priceL = priceL;
		this.imagePath = imagePath;
		this.deleted = deleted;
	}
	
	public ItemForm() {
	}
	
	public int getIntPriceM() {
		return Integer.parseInt(priceM);
	}
	public int getIntPriceL() {
		return Integer.parseInt(priceL);
	}
	
	//	GetterSetter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriceM() {
		return priceM;
	}
	public void setPriceM(String priceM) {
		this.priceM = priceM;
	}
	public String getPriceL() {
		return priceL;
	}
	public void setPriceL(String priceL) {
		this.priceL = priceL;
	}
	public MultipartFile getImagePath() {
		return imagePath;
	}
	public void setImagePath(MultipartFile imagePath) {
		this.imagePath = imagePath;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "ItemForm [name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL=" + priceL
				+ ", imagePath=" + imagePath + ", deleted=" + deleted + "]";
	}
	
}
