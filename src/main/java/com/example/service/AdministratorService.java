package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public Administrator loginAdministrator(String password, String email) {
		Administrator administrator = repository.findByMailAddress(email);
		
		if (administrator == null) {
			return null;
		}
		// パスワードが不一致だった場合はnullを返す
		if (!passwordEncoder.matches(password, administrator.getPassword())) {
			return null;
		}
		return administrator;
	}
	
	/**
	 * ユーザー登録を行う。
	 * @param user
	 */
	public void insertAdministrator(Administrator administrator) {
		administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
		repository.insertAdministrator(administrator);
	}
	
}
