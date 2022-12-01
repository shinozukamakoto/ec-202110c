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
import org.springframework.web.context.WebApplicationContext;

import com.example.util.CsvDataSetLoader;
import com.example.util.SuperSessionUtil;
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

class InsertControllerTest {

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
	@DisplayName("ユーザー登録(正常系)")
	 @ExpectedDatabase(value = "/User/insert_01/expected", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void test1() throws Exception {
		 MockHttpSession userEmailSession = SuperSessionUtil.createEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert/insertUser")
				 .session(userEmailSession)
                 .param("name", "山田太郎")
                 .param("email", "yamada@example.com")
                 .param("zipcode", "111-1111")
                 .param("address", "東京都新宿区")
                 .param("telephone", "080-0000-0000")
                 .param("password", "Abcd1234")
                 .param("confirmPassword", "Abcd1234")
				 ).andExpect(view().name("redirect:/toLogin"))
				 .andReturn();
		
	}

	@Test
	@DisplayName("ユーザー登録(異常系)toInsertメソッドのemailがnull")
	void test2() throws Exception {
		 //MockHttpSession userEmailSession = SuperSessionUtil.nullEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert")
		 //		 .session(userEmailSession)
				 ).andExpect(view().name("redirect:/mailInsert"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザー登録(異常系)toInsertメソッドのemailがnotNull")
	void test3() throws Exception {
		MockHttpSession userEmailSession = SuperSessionUtil.createEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert")
				 .session(userEmailSession)
				 ).andExpect(view().name("register_user"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザー登録(異常系)insertメソッドのemailがnull")
	void test4() throws Exception {
		//MockHttpSession userEmailSession = SuperSessionUtil.nullEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert/insertUser")
		//		 .session(userEmailSession)
				 ).andExpect(view().name("redirect:/mailInsert"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザー登録(異常系)insertメソッドのバリデーションエラー")
	void test5() throws Exception {
		 MockHttpSession userEmailSession = SuperSessionUtil.createEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert/insertUser")
				 .session(userEmailSession)
                .param("name", "山田太郎")
				 ).andExpect(view().name("register_user"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザー登録(異常系)insertメソッドのパスワード確認エラー")
	void test6() throws Exception {
		 MockHttpSession userEmailSession = SuperSessionUtil.createEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert/insertUser")
				 .session(userEmailSession)
                .param("name", "山田太郎")
                .param("email", "yamada@example.com")
                .param("zipcode", "111-1111")
                .param("address", "東京都新宿区")
                .param("telephone", "080-0000-0000")
                .param("password", "Abcd1234")
                .param("confirmPassword", "1234abcd")
				 ).andExpect(view().name("register_user"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザー登録(異常系)insertメソッドのメールアドレス使用済みエラー")
	void test7() throws Exception {
		 MockHttpSession userEmailSession = SuperSessionUtil.createEmailSession();
		 MvcResult mvcResult = mockMvc.perform(post("/insert/insertUser")
				 .session(userEmailSession)
                .param("name", "山田太郎")
                .param("email", "test@test.co.jp")
                .param("zipcode", "111-1111")
                .param("address", "東京都新宿区")
                .param("telephone", "080-0000-0000")
                .param("password", "Abcd1234")
                .param("confirmPassword", "Abcd1234")
				 ).andExpect(view().name("register_user"))
				 .andReturn();
	}
	
}
