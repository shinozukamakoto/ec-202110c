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

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
    TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class LoginControllerTest {

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
	@DisplayName("ユーザログイン(正常系)toLoginメソッド(html呼び出し)")
	void test1() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/toLogin")
                 ).andExpect(view().name("login/login"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザログイン(正常系)loginメソッド(商品一覧画面遷移)")
	void test2() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/login")
				 .param("email", "test@test.co.jp")
				 .param("password", "morimori")
                 ).andExpect(view().name("forward:/showList"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザログイン(正常系)loginメソッド(商品一覧画面遷移)")
	void test3() throws Exception {
		 MockHttpSession cartItemListSession = SuperSessionUtil.cartItemList();
		 MvcResult mvcResult = mockMvc.perform(post("/login")
				 .param("email", "test@test.co.jp")
				 .param("password", "morimori")
				 .session(cartItemListSession)
                 ).andExpect(view().name("redirect:/orderCo"))
				 .andReturn();
	}

	@Test
	@DisplayName("ユーザログイン(異常系)loginメソッドのloginError")
	void test4() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/login")
				 .param("email", "curry@test.co.jp")
				 .param("password", "morimori")
                 ).andExpect(view().name("login/login"))
				 .andReturn();
	}
	
	@Test
	@DisplayName("ユーザログアウト(正常系)logoutメソッド")
	void test5() throws Exception {
		 MvcResult mvcResult = mockMvc.perform(post("/logout")
                 ).andExpect(view().name("forward:/showList"))
				 .andReturn();
	}
	
}
