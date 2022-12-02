package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ToppingRepository;
import com.example.service.ItemService;
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
class ItemControllerTest {
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
	    @DisplayName("トップ画面（商品一覧画面）")
	    void showList1() throws Exception {
	    	//コントローラー呼び出し
	    	MvcResult mvcResult = mockMvc.perform(get("/showList"))
	                .andExpect(view().name("item/item_list_curry"))
	                .andReturn();
	    	
	    	
	    	//スコープデータの取り出し
	    	ModelAndView mav = mvcResult.getModelAndView();
	        Page<Item> pageList = (Page<Item>)mav.getModel().get("itemPage");
	        List<Item> itemList = pageList.getContent();
	       
	        List<Integer> integerList = (List<Integer>)mav.getModel().get("pageNumbers");
	        
	        //テスト
	        //1ページに5アイテム表示
	        Item item1 = itemList.get(0);
	        Item item2 = itemList.get(itemList.size()-1);
	        assertAll(
	    			() -> assertEquals("カツカレー", item1.getName()),
	    			() -> assertEquals("とんかつカレーラーメン", item2.getName())
	    		);
	        //18件あり1ページにつき5件表示させる場合→1,2,3、4がpageNumbersに入る
	        
	        Integer integer1 = integerList.get(0);
	        Integer integer4 = integerList.get(integerList.size()-1);
	        assertAll(
	    			() -> assertEquals(1,  integer1),
	    			() -> assertEquals(4, integer4)
	    		);

	}
    	
    	@Test
    	@DisplayName("空文字検索")
	    void showList3() throws Exception {
    		MvcResult mvcResult = mockMvc.perform(get("/showList")
            	.param("searchName", ""))
    			.andExpect(view().name("item/item_list_curry"))
    			.andReturn();
    		
    		//スコープデータの取り出し
	    	ModelAndView mav = mvcResult.getModelAndView();
	        Page<Item> pageList = (Page<Item>)mav.getModel().get("itemPage");
	        List<Item> itemList = pageList.getContent();
	        
	        //テスト
	        //最初と最後を表示
	        Item item1 = itemList.get(0);
	        Item item2 = itemList.get(itemList.size()-1);
	        assertAll(
	    			() -> assertEquals("カツカレー", item1.getName()),
	    			() -> assertEquals("とんかつカレーラーメン", item2.getName())
	    	);
    		
    }
    	
    	@Test
    	@DisplayName("曖昧検索（検索名(カツカレー)")
	    void showList4() throws Exception {
    		MvcResult mvcResult = mockMvc.perform(get("/showList")
        		.param("searchName", "カツカレー"))
    			.andExpect(view().name("item/item_list_curry"))
    			.andReturn();
    		
    		//スコープデータの取り出し
	    	ModelAndView mav = mvcResult.getModelAndView();
	        Page<Item> pageList = (Page<Item>)mav.getModel().get("itemPage");
	        List<Item> itemList = pageList.getContent();
	        
	        //テスト
	        //最初と最後を表示
	        Item item1 = itemList.get(0);
	        Item item2 = itemList.get(itemList.size()-1);
	        assertAll(
	    			() -> assertEquals("カツカレー", item1.getName()),
	    			() -> assertEquals("ヒレカツカレー", item2.getName())
	    	);
    		
    }
    	
    	@Test
    	@DisplayName("該当検索結果なし")
	    void showList5() throws Exception {
    		MvcResult mvcResult = mockMvc.perform(get("/showList")
        		.param("searchName", "あああ"))
    			.andExpect(view().name("item/item_list_curry"))
    			.andReturn();
    		
    		//スコープデータの取り出し
	    	ModelAndView mav = mvcResult.getModelAndView();
	        Page<Item> pageList = (Page<Item>)mav.getModel().get("itemPage");
	        List<Item> itemList = pageList.getContent();
	        
	        //テスト
	        //エラーメッセージが表示
			
			String message = (String)mav.getModel().get("message");
			
			assertEquals("該当する商品がありません", message);
	    	
    		
    }
	
	  @Test
	   @DisplayName("商品詳細画面")
	   void detail() throws Exception {
	        // ①コントローラー呼出し
	        MvcResult mvcResult = mockMvc.perform(get("/detail")
	                .param("id","1"))
	        		.andExpect(view().name("item/item_detail"))
	                .andReturn();
	       //スコープデータの取り出し
	    	ModelAndView mav = mvcResult.getModelAndView();
			Item item = (Item)mav.getModel().get("item");
			ServletContext application = mvcResult.getRequest().getServletContext();

			//テスト
			//toppingList 全件検索
			List<Topping> toppingList = (List<Topping>) application.getAttribute("toppingList");
			Topping topping1 = toppingList.get(0);
			Topping topping2 = toppingList.get(toppingList.size()-1);
	        assertAll(
	    			() -> assertEquals("オニオン", topping1.getName()),
	    			() -> assertEquals("チーズ増量", topping2.getName())
	    	);

	    }
	   

}
