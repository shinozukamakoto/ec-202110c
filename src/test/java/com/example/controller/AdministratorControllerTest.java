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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
    TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

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
                 .param("name", "鈴木一郎")
                 .param("email", "suzuki@example.com")
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
	@DisplayName("管理者登録(正常系)insertメソッド確認用パスワードエラー")
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
	@DisplayName("管理者登録(正常系)insertメソッド確認用パスワードエラー")
	void test5() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/insertAdministrator/insertAd")
                 .param("name", "鈴木一郎")
                 .param("email", "suzuki@example.com")
                 .param("password", "Abcd1234")
                 .param("confirmPassword", "1234abcd")
				 ).andExpect(view().name("register_administrator"))
				 .andReturn();
	}
	
	
}
