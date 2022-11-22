package com.example.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.AdministratorForm;
import com.example.form.AdministratorLoginForm;
import com.example.service.AdministratorService;

/**
 * 管理者登録・ログインするためのコントローラー
 * @author miyazawa
 *
 */
@Controller
@RequestMapping("/insertAdministrator")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public AdministratorLoginForm setUpLoginForm() {
		return new AdministratorLoginForm();
	}
	
	@ModelAttribute
	public AdministratorForm setUpForm() {
		return new AdministratorForm();
	}
	
	/**
	 * 管理者登録画面に遷移
	 * @return
	 */
	@RequestMapping("")
	public String toAdInsert() {
		return "register_administrator";
	}

	/**
	 * フォームから受け取った情報をもとに管理者登録を行う。
	 * 登録完了後ログイン画面に遷移。
	 * @param form
	 * @return
	 */
	@RequestMapping("/insertAd")
	public String insert(@Validated AdministratorForm form, BindingResult result, Model model) {
		//バリデーションチェックによるエラーがあればユーザー登録画面に遷移
		if(result.hasErrors()) {
			return "register_administrator";
		}
		
		//パスワードと確認用パスワードが不一致の場合エラー文をリクエストスコープに格納してユーザー登録画面に遷移
		if(!(form.getPassword().equals(form.getConfirmPassword()))) {
			model.addAttribute("passwordNotMatchError", "パスワードと確認用パスワードが不一致です");
			return "register_administrator";
		}
		
		//フォームの値をドメインにコピー
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		//emailが既に登録の場合はSQLで例外が発生するのでtry-catchを行う。
		//例外の際はエラー文をリクエストスコープに格納してユーザー登録画面に遷移
		try {
				administratorService.insertAdministrator(administrator);
				return "redirect:/insertAdministrator/companyId";
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			model.addAttribute("emailRegistedError","そのメールアドレスはすでに使われています");
			return "register_administrator";
		}
	}
	
	@RequestMapping("/adLogin")
	public String administratorLogin() {
		if(session.getAttribute("companyId") == null) {
			return "redirect:/insertAdministrator/companyId";
		}
		
		return "login/administrator_login";
	}
	
	@RequestMapping("/companyIdToLogin")
	public String companyIdToLogin(AdministratorLoginForm form,Model model) {
		//入力した企業IDが指定のIDと一致した場合、ログイン画面に遷移
				if(("202110c_banzai").equals(form.getCompanyId())) {
					System.out.println(form.getCompanyId()+"です");
					session.setAttribute("companyId", "202110c_banzai");
				return "redirect:/insertAdministrator/adLogin";
				}
					model.addAttribute("companyIdNotMatchError", "企業IDが不正です");
				
				return "forward:/insertAdministrator/companyId";
	}
	
	
	@RequestMapping("/toAdLogin")
	public String  login(AdministratorLoginForm form,Model model) {
		
		if(session.getAttribute("companyId") == null) {
			return "redirect:/insertAdministrator/companyId";
		}
		
		Administrator administrator = administratorService.loginAdministrator(form.getPassword(), form.getEmail());
		System.out.println(administrator);
			//管理者情報に登録されていなければエラーを返す
		if(administrator == null) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが不正です");
			return "login/administrator_login";//RequestMappingのアドレスを指定
		}
		
		//セッションに管理者情報を保存
		session.removeAttribute("202110c_banzai");
		session.setAttribute("administrator",administrator);
		return "forward:/admiShowList";
		}
		

	
	@RequestMapping("/adLogout")
	public String logout() {
		session.invalidate();
		return "forward:/insertAdministrator/adLogin";
	}
	
	@RequestMapping("/companyId")
	public String companyId(String companyId) {
		return "login/administrator_login_companyId";
	}
	
}
