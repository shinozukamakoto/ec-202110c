package com.example.form;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理者登録用フォーム
 * @author miyazawa
 *
 */
public class AdministratorForm {

	//管理者氏名
	@NotBlank(message="名前を入力して下さい")
	private String name;
	//メールアドレス
	@NotBlank(message="メールアドレスを入力して下さい")
	@Email(message="メールアドレスの形式が不正です")
	private String email;
	//パスワード
	@NotBlank(message="パスワードを入力して下さい")
	@Size(min=8, max=16, message="パスワードは８文字以上１６文字以内で設定してください")
	private String password;
	//確認用パスワード
	@NotBlank(message="確認用パスワードを入力して下さい")
	private String confirmPassword;
	
	
	
	//ゲッターセッター
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	//toStringのオーバーライド
	@Override
	public String toString() {
		return "AdministratorForm [name=" + name + ", email=" + email + ", password=" + password + ", confirmPassword="
				+ confirmPassword + "]";
	}

}
