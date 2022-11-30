package com.example.form;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class OrderForm {

	//合計金額
	private Integer totalPrice;
	//お届け先　氏名
	@NotBlank(message="名前を入力して下さい")
	private String destinationName;
	//お届け先　メールアドレス
	@NotBlank(message="メールアドレスを入力して下さい")
	@Email(message="メールアドレスの形式が不正です")
	private String destinationEmail;
	//お届け先　郵便番号
	@NotBlank(message="郵便番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message="郵便番号はXXX-XXXXの形式で入力してください")
	private String destinationZipcode;
	//お届け先　住所
	@NotBlank(message="住所を入力して下さい")
	private String destinationAddress;
	//お届け先　電話番号
	@NotBlank(message="電話番号を入力して下さい")
	@Pattern(regexp = "^[0-9]+-[0-9]+-[0-9]+$", message="電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String destinationTel;
	//日付
	private Date orderDate ;
	//お届け時間
	@NotNull(message="配達日時を入力してください")
	private String deliveryTime;
	//支払い方法
	@NotNull(message="支払い方法を入力してください")
	private Integer paymentMethod;

	public Timestamp getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
	    java.util.Date date;
			try {
				date = sdf.parse(deliveryTime);
				Timestamp ts = new Timestamp(date.getTime());
				System.out.println(ts);
				return ts;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
	}

	public Integer getIntegerDeliveryTime() {
		return Integer.parseInt(deliveryTime);
	}

	//ゲッターセッター
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public String getDestinationEmail() {
		return destinationEmail;
	}
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}
	public String getDestinationZipcode() {
		return destinationZipcode;
	}
	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getDestinationTel() {
		return destinationTel;
	}
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@Override
	public String toString() {
		return "OrderForm [totalPrice=" + totalPrice + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", orderDate=" + orderDate
				+ ", deliveryTime=" + deliveryTime + ", paymentMethod=" + paymentMethod + "]";
	}
}