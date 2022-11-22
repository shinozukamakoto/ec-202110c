package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.CartItem;
import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.service.UserService;



@Controller
@RequestMapping("")
public class LoginController {
	
	
	@Autowired
	private UserService service;
	
	@Autowired
	private HttpSession session;
	

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;//リクエストパラメーターにloginFormが格納された
	}
	
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login/login";
	}
	
	@RequestMapping("/login")
	public String  login(LoginForm form,Model model) {
		System.out.println(form);
		User user =service.login(form.getPassword(), form.getEmail());
		System.out.println(user+"です");
		if(user == null) {
			model.addAttribute("loginError", "メールアドレス、またはパスワードが間違っています");
			return toLogin();//RequestMappingのアドレスを指定
		}
		
		session.setAttribute("user",user);
		
		@SuppressWarnings("unchecked")
		List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
		
		if(cartItemList == null || cartItemList.size() == 0) {
			return "forward:/showList";
		} else {
			return "redirect:/orderCo";
		}
	}
	
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "forward:/showList";
	}
	
}
