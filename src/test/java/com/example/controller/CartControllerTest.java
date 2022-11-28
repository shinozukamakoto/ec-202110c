package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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
import com.example.ecommerce_a.util.SessionUtil;
import com.example.form.ItemForm;
import com.example.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
//@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
      DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
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
	    MvcResult mvcResult = (MvcResult) mockMvc.perform(get("/inCart")
				  .param("name", "cutletcurry")
                  .param("size", "M")
                  .param("priceM", "1490円")
                  .param("priceL", "2570円")
                  .param("toppingIndex", "1")
                  .param("quantity", "1")
		).andExpect(view().name("redirect:/showCart"));

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        ItemForm inCart = (ItemForm) session.getAttribute("cartItemList");
        assertEquals("cartItemList", inCart);
	
	}
	
	
	//Cartの中身を表示するメソッド（通常）
	@Test
    void showCartTest02() throws Exception {
		mockMvc.perform(get("/showCart")
	  ).andExpect(model().attributeExists("cartItemList"));
}
	
	
	//Cartの中身を表示するメソッド（中身nullの状態）
	@Test
    void showCartTest03() throws Exception {
		List<CartItem> cartItemList = new ArrayList<>();
		CartItem cartItem = new CartItem();
		cartItem.setSize(null);
		cartItemList.add(cartItem);
		mockMvc.perform(get("/showCart")
				.sessionAttr("cartItemList", cartItemList)
	  ).andExpect(model().attributeExists("cartItemList"));
	}

	
	//Cartの中身を表示するメソッド（まだできてない）
	@Test
    void showCartTest04() throws Exception {
		List<CartItem> cartItemList = new ArrayList<>();
		CartItem cartItem = new CartItem();
		cartItem.setSize(1);
		cartItemList.add(cartItem);
		mockMvc.perform(get("/showCart")
				.sessionAttr("cartItemList", cartItemList)
	  ).andExpect(model().attributeExists("cartItemList"));
	}
	
	//Cartの中身を消す（まだできてない）
		@Test
		void incartTest05() throws Exception {		
			List<CartItem> cartItemList = new ArrayList<>();
			CartItem cartItem = new CartItem();
			mockMvc.perform(get("/delete")
					.sessionAttr("cartItemList", cartItemList.remove(0))
					).andExpect(model().attributeExists("showCart"));
		}
}
	
