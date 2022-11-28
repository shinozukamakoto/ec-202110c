package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.util.CsvDataSetLoader;
import com.example.ecommerce_a.util.SessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class //
})
class OrderControlerTest {
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
	@DisplayName("注文確認画面への遷移（ログイン情報確認）")
	void testToOrder01() throws Exception {
		MockHttpSession userAndOrderSession = SessionUtil.createUserIdAndOrderSession();
		mockMvc.perform(get("/toOrder")
				.session(userAndOrderSession))
				.andExpect(view().name("order/order_confirm"));
	}

	@Test
	@DisplayName("注文完了画面への遷移")
	void testOrderCompletion() throws Exception{
		MockHttpSession userAndOrderSession = SessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/orderCompletion")
				.session(userAndOrderSession))
				.andExpect(view().name("/order/order_finished"));
	}

	@Test
	@DisplayName("注文履歴画面への遷移")
	void testOrderHistory()throws Exception {
		MockHttpSession orderSession = SessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/orderHistory")
				.session(orderSession))
				.andExpect(view().name("order/order_history"));
	}

	@Test
	@DisplayName("注文詳細画面への遷移")
	void testOrderDetail()throws Exception {
		MockHttpSession orderSession = SessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/orderdetail")
				.session(orderSession))
				.andExpect(view().name("/order/order_detail"));
				
	}

}
