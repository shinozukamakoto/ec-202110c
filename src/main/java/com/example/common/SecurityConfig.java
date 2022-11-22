package com.example.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.service.AdministratorDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AdministratorDetailsServiceImpl administratorDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers( "/css/**"
						, "/img_curry/**"
						, "/js/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().permitAll();
		
		http
        .authorizeRequests()
        	.antMatchers("/insert", "/insert/insertUser", "/login", "/logout", "/mailInsert", "/mailsend", "/toLogin" ,"/check", "/showList", "/insertAdministrator/**" ,"/searchItem", "/detail", "/inCart", "/showCart", "/delete", "/order", "/orderCompletion", "/orderHistory", "/toOrder", "/orderCo", "/orderdetail" ,"/passCheck").permitAll() //全てのユーザに許可
        	.antMatchers("/admiShowList", "/toInsertItem", "/insertItem", "/admiDetail", "/admiDelete", "/admiUpdate").hasRole("ADMIN") // 引数から始まるパスはADMIN権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
        	.anyRequest().authenticated()	// それ以外は要認証
            .and()
        .formLogin()
            .loginPage("/insertAdministrator/companyId") //ログインページはコントローラを経由しないのでViewNameとの紐付けが必要
            .loginProcessingUrl("/insertAdministrator/toAdLogin") //フォームのSubmitURL、このURLへリクエストが送られると認証処理が実行される
            .usernameParameter("email") //リクエストパラメータのname属性を明示
            .passwordParameter("password")
            .defaultSuccessUrl("/admiShowList", true) //認証が成功した際に遷移するURL
            .failureUrl("/insertAdministrator/toAdLogin") //認証が失敗した際に遷移するURL
            .permitAll() //どのユーザでも接続できる。
            .and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/toLogin")
            .permitAll();
	}
	
	
	/**
	 * 「認証」に関する設定.<br>
	 * 認証ユーザを取得する「UserDetailsService」の設定や<br>
	 * パスワード照合時に使う「PasswordEncoder」の設定
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(administratorDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	 /**
	  * <pre>
	  * @Autowired
	  * private PasswordEncoder passwordEncoder;
	  * と記載するとDIできる。
	  * </pre>
	  * @return bcryptアルゴリズムで暗号化する実装オブジェクト
	  */
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
}
