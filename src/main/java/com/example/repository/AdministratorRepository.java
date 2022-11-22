package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;

@Repository
public class AdministratorRepository {
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER =(rs,i)->{
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setEmail(rs.getString("email"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public Administrator findByPasswordAndMailAddress(String password,String email) {
		String sql ="SELECT * FROM administrators WHERE password=:password AND email=:email";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("password",password).addValue("email",email);
		System.out.println(password + " " + email);
		
		try {
			Administrator administrator= template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
			return administrator;
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public void insertAdministrator(Administrator administrator) {
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		String sql = "INSERT INTO administrators (name, email, password) VALUES (:name, :email, :password);";	
		template.update(sql, param);		
	}
	
	
	public Administrator findByMailAddress(String email) {
		String sql ="SELECT * FROM administrators WHERE email=:email";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("email",email);
		
		try {
			Administrator administrator= template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
			return administrator;
		}catch(Exception e) {
			return null;
		}
		
	}
	
}
