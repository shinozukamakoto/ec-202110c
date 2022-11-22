package com.example.domain;
/**
 * 
 * @author miyazawa
 * 管理者情報のドメインクラス
 *
 */
public class Administrator {

	//ID
	private Integer id;
	//管理者氏名
	private String name;
	//パスワード
	private String password;
	//メールアドレス
	private String email;
	
	//引数ありコンストラクタ
	public Administrator(Integer id, String name, String password, String email) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	//引数なしコンストラクタ
	public Administrator() {}
	
	//以下getter及びsetter
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//toStringメソッド
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + "]";
	}
	
	
	
}
