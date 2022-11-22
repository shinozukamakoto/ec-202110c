package com.example.form;


/**
 * @author satakemisako
 * 管理者ログイン用フォームクラス
 *
 */
public class AdministratorLoginForm {
	//管理者登録パスワード
	private String password;
	//管理者登録メールアドレス
	
	private String email;
	//企業ID
	private String companyId;
	
	
	@Override
	public String toString() {
		return "AdministratorInsertForm [password=" + password + ", email=" + email + ", companyId=" + companyId + "]";
	}

	//ゲッター、セッター
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


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
	
}
