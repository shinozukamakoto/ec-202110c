package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.example.ecommerce_a.util.SuperSessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
    TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
@Transactional
class AdministratorControllerTest {

	@Autowired
	private WebApplicationContext wac;

    private MockMvc mockMvc;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("管理者登録(正常系)toAdInsertメソッド")
	void test1() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator")
                 ).andExpect(view().name("register_administrator"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者登録(正常系)insertメソッド")
	 @ExpectedDatabase(value = "/Administrator/insert_01/expected", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void test2() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/insertAd")
                .param("name", "佐藤一郎")
                .param("email", "satou@example.com")
                .param("password", "Abcd1234")
                .param("confirmPassword", "Abcd1234")
				 ).andExpect(view().name("redirect:/insertAdministrator/companyId"))
				 .andReturn();
		 
	}
	
	@Test
	@DisplayName("管理者登録(異常系)insertメソッドのバリデーションエラー")
	void test3() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/insertAd")
                 .param("name", "鈴木一郎")
				 ).andExpect(view().name("register_administrator"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者登録(異常系)insertメソッド確認用パスワードエラー")
	void test4() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/insertAd")
                 .param("name", "鈴木一郎")
                 .param("email", "suzuki@example.com")
                 .param("password", "Abcd1234")
                 .param("confirmPassword", "1234abcd")
				 ).andExpect(view().name("register_administrator"))
				 .andReturn();
	}

	@Test
	@DisplayName("管理者登録(異常系)insertメソッドメールアドレス重複エラー")
	@DatabaseSetup("/Administrator/insert_02/expected")
	void test5() throws Exception {		
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/insertAd")
	                .param("name", "鈴木次郎")
	                .param("email", "suzuki@example.com")
	                .param("password", "Abcd1234")
	                .param("confirmPassword", "Abcd1234")
					 ).andExpect(view().name("register_administrator"))
					 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(企業ID)(正常系)administratorLoginメソッド")
	void test6() throws Exception {
		 MockHttpSession companyIdSession = SuperSessionUtil.companyIdSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/adLogin")
                .session(companyIdSession)
				 ).andExpect(view().name("login/administrator_login"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(企業ID)異常系)administratorLoginメソッドの企業IDnullエラー")
	void test7() throws Exception {
		 //MockHttpSession companyIdSession = SuperSessionUtil.nullCompanyId();
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/adLogin")
         //        .session(companyIdSession)
				 ).andExpect(view().name("redirect:/insertAdministrator/companyId"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(企業ID)(正常系)companyIdToLoginメソッド")
	void test8() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/companyIdToLogin")
                 .param("companyId", "202110c_banzai")
				 ).andExpect(view().name("redirect:/insertAdministrator/adLogin"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(企業ID)(異常系)companyIdToLoginメソッドの企業ID不正エラー")
	void test9() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/companyIdToLogin")
                 .param("companyId", "aaa")
				 ).andExpect(view().name("forward:/insertAdministrator/companyId"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(mail&password)(正常系)loginメソッド")
	@DatabaseSetup("/Administrator/insert_03/expected")
	void test10() throws Exception {		
		 MockHttpSession companyIdSession = SuperSessionUtil.companyIdSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/toAdLogin")
				 .session(companyIdSession)
				 .param("password", "morimori")
                 .param("email", "tanaka@example.com")
				 ).andExpect(view().name("forward:/admiShowList"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(mail&password)(異常系)loginメソッドの企業IDnullエラー")
	void test11() throws Exception {
		 //MockHttpSession companyIdSession = SuperSessionUtil.companyId();
		 MvcResult mvcResult2 = mockMvc.perform(post("/insertAdministrator/toAdLogin")
		 //		 .session(companyIdSession)
				 ).andExpect(view().name("redirect:/insertAdministrator/companyId"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログイン(mail&password)(異常系)loginメソッドの管理者未登録")
	void test12() throws Exception {
		 MockHttpSession companyIdSession = SuperSessionUtil.companyIdSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/toAdLogin")
				 .session(companyIdSession)
                .param("email", "suzuki@example.com")
                .param("password", "Abcd1234")
				 ).andExpect(view().name("login/administrator_login"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("管理者ログアウト(正常系)logoutメソッド")
	void test13() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/adLogout")
				 ).andExpect(view().name("forward:/insertAdministrator/adLogin"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("企業ID遷移(正常系)companyIdメソッド")
	void test14() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/companyId")
				 ).andExpect(view().name("login/administrator_login_companyId"))
				 .andReturn();
	}
	
}
