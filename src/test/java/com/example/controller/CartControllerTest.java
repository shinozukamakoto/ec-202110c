package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import com.example.domain.CartItem;
import com.example.domain.Topping;
import com.example.form.ItemForm;
import com.example.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
//@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class CartControllerTest {
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

//Cartに商品を追加
	@Test
	void incartTest01() throws Exception {
		List<Topping> toppingList = new ArrayList<>();
		Topping topping1 = new Topping();
		topping1.setId(1);
		topping1.setName("カツカレー");
		topping1.setPriceM(1490);
		topping1.setPriceL(2570);
		Topping topping2 = new Topping();
		topping2.setId(2);
		topping2.setName("ポークカレー");
		topping2.setPriceM(1490);
		topping2.setPriceM(2570);
		toppingList.add(topping1);
		toppingList.add(topping2);
		wac.getServletContext().setAttribute("toppingList", toppingList);
		
		MvcResult mvcResult = mockMvc.perform(get("/inCart")
						.param("name", "cutletcurry")
						.param("size", "M")
						.param("priceM", "1490")
						.param("priceL", "2570")
						.param("toppingList", "1")
						.param("quantity", "1"))
				.andExpect(view().name("redirect:/showCart")).andReturn();

		MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
		List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
		CartItem cartItem = cartItemList.get(0);
		assertEquals("cutletcurry", cartItem.getName());

	}

	// Cartの中身を表示するメソッド（通常）
	@Test
	void showCartTest02() throws Exception {
		List<CartItem> cartItemList = new ArrayList<>();
		MvcResult mvcResult = mockMvc.perform(get("/showCart").sessionAttr("cartItemList", cartItemList))
	                 .andExpect(view().name("cart/cart_list"))
					 .andReturn();
	}
	

	// Cartの中身を表示するメソッド（中身nullの状態）
	@Test
	void showCartTest03() throws Exception {
		List<CartItem> cartItemList = new ArrayList<>();
		CartItem cartItem = new CartItem();
		cartItem.setSize(null);
		cartItem.setSubTotal(1000);
		cartItemList.add(cartItem);
		MvcResult mvcResult = mockMvc.perform(get("/showCart").sessionAttr("cartItemList", cartItemList))
				.andExpect(view().name("cart/cart_list")).andReturn();
		
		HttpSession session = mvcResult.getRequest().getSession();
		List<CartItem> resultCartItemList = (List<CartItem>)session.getAttribute("cartItemList");
		
		assertEquals(1, resultCartItemList.size());
		

	}

	// Cartの中身を消す
	@Test
	void deleteTest05() throws Exception {
		List<CartItem> cartItemList = new ArrayList<>();
		CartItem cartItem1 = new CartItem();
		cartItem1.setName("カツカレー");
		cartItem1.setSubTotal(1490);
		cartItemList.add(cartItem1);
		CartItem cartItem2 = new CartItem();
		cartItem2.setName("ポークカレー");
		cartItem2.setSubTotal(2570);
		cartItemList.add(cartItem2);
		MvcResult mvcResult = mockMvc
				.perform(get("/delete").param("index", "0").sessionAttr("cartItemList", cartItemList))
				.andExpect(view().name("cart/cart_list")).andReturn();
		
		HttpSession session = mvcResult.getRequest().getSession();
		List<CartItem> resultCartItemList = (List<CartItem>)session.getAttribute("cartItemList");
		assertEquals(1, resultCartItemList.size());

	}
}
