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

import com.example.util.CsvDataSetLoader;
import com.example.util.HyperSessionUtil;
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
	@DisplayName("注文確認画面への遷移（ログイン情報確認成功）")
	void testToOrder01() throws Exception {
		MockHttpSession userAndOrderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/toOrder")
				.session(userAndOrderSession)
				.param("user","user"))
				.andExpect(view().name("order/order_confirm"));
	}

	@Test
	@DisplayName("注文確認画面への遷移（ログイン情報確認失敗）")
	void testToOrder02() throws Exception {
		mockMvc.perform(get("/toOrder"))
				.andExpect(view().name("redirect:/toLogin"));
	}

	@Test
	@DisplayName("注文完了画面への遷移(成功）")
	void testOrderCompletion01() throws Exception{
		MockHttpSession userAndOrderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/order")
				.session(userAndOrderSession)
				.param("totalPrice","3000")
				.param("orderDate","2022-12-10")
				.param("deliveryTime","10")
				.param("destinationName","ebian")
				.param("destinationEmail","ebina@gmail.com")
				.param("destinationZipcode","111-1111")
				.param("destinationAddress","テスト住所")
				.param("destinationTel","0000-0000-0000")
				.param("paymentMethod","1"))
				.andExpect(view().name("redirect:/orderCompletion"));
	}

	@Test
	@DisplayName("注文完了画面への遷移（失敗＝日付が過去）")
	void testOrderCompletion02() throws Exception{
		MockHttpSession userAndOrderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/order")
				.session(userAndOrderSession)
				.param("totalPrice","3000")
				.param("orderDate","2022-10-10")
				.param("deliveryTime","10")
				.param("destinationName","ebian")
				.param("destinationEmail","ebina@gmail.com")
				.param("destinationZipcode","111-1111")
				.param("destinationAddress","テスト住所")
				.param("destinationTel","0000-0000-0000")
				.param("paymentMethod","1"))
				.andExpect(view().name("/order/order_confirm"));
	}
	
	@Test
	@DisplayName("注文完了画面への遷移（失敗＝ヴァリデーション,失敗＝日付が空白）")
	void testOrderCompletion03() throws Exception{
		MockHttpSession userAndOrderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/order")
				.session(userAndOrderSession)
				.param("totalPrice","3000")
				.param("orderDate","")
				.param("deliveryTime","10")
				.param("destinationName","ebian")
				.param("destinationEmail","ebina@gmail.com")
				.param("destinationZipcode","111-1111")
				.param("destinationAddress","テスト住所")
				.param("destinationTel","0000-0000-0000")
				.param("paymentMethod","1"))
				.andExpect(view().name("/order/order_confirm"));
	}

	
	@Test
	@DisplayName("注文完了画面への遷移（失敗＝ヴァリデーション,失敗＝日付が過去）")
	void testOrderCompletion04() throws Exception{
		MockHttpSession userAndOrderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/order")
				.session(userAndOrderSession)
				.param("totalPrice","3000")
				.param("orderDate","2022-10-10")
				.param("deliveryTime","10")
				.param("destinationName","ebian")
				.param("destinationEmail","ebina@gmail.com")
				.param("destinationZipcode","111-1111")
				.param("destinationAddress","テスト住所")
				.param("destinationTel","00000000-0000")
				.param("paymentMethod","1"))
				.andExpect(view().name("/order/order_confirm"));
	}
	@Test
	@DisplayName("注文完了画面への遷移（失敗＝日時が３時間以内）")
	void testOrderCompletion05() throws Exception{
		MockHttpSession userAndOrderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/order")
				.session(userAndOrderSession)
				.param("deliveryTime","10"))
				.andExpect(view().name("/order/order_confirm"));
	}

	@Test
	@DisplayName("注文履歴画面への遷移（成功）")
	void testOrderHistory01()throws Exception {
		MockHttpSession orderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/orderHistory")
				.session(orderSession)
				.param("orderList", "orderList"))
				.andExpect(view().name("order/order_history"));
	}

	@Test
	@DisplayName("注文履歴画面への遷移（ログイン失敗）")
	void testOrderHistory02()throws Exception {
		mockMvc.perform(get("/orderHistory"))
				.andExpect(view().name("forward:/toLogin"));
	}


	@Test
	@DisplayName("注文詳細画面への遷移")
	void testOrderDetail()throws Exception {
		MockHttpSession orderSession = HyperSessionUtil.createUserIdAndOrderCompletionSession();
		mockMvc.perform(get("/orderdetail")
				.session(orderSession))
				.andExpect(view().name("/order/order_detail"));

	}

}
