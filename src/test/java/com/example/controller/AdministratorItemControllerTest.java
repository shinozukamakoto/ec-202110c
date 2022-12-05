package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.Item;
import com.example.util.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;


@SpringBootTest
//@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
@Transactional
class AdministratorItemControllerTest {

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
	    @DisplayName("商品一覧画面")
	    void admiShowList() throws Exception {
	    	//コントローラー呼び出し
	    	MvcResult mvcResult = mockMvc.perform(get("/admiShowList"))
	                .andExpect(view().name("item/admi_item_list_curry"))
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
   		MvcResult mvcResult = mockMvc.perform(get("/admiShowList")
           	.param("searchName", ""))
   			.andExpect(view().name("item/admi_item_list_curry"))
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
   		MvcResult mvcResult = mockMvc.perform(get("/admiShowList")
       		.param("searchName", "カツカレー"))
   			.andExpect(view().name("item/admi_item_list_curry"))
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
   		MvcResult mvcResult = mockMvc.perform(get("/admiShowList")
       		.param("searchName", "あああ"))
   			.andExpect(view().name("item/admi_item_list_curry"))
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
	    	@DisplayName("商品登録画面の表示")
	    	void toInsertItemTest1() throws Exception {
	        	
	        	mockMvc.perform(get("/toInsertItem"))
		                .andExpect(view().name("item/item_insert"));
	        		
	        	    	}
	        
	        
	        @Test
	    	@DisplayName("エラーが存在する場合には商品登録画面に遷移")
	    	void toInsertItemTest2() throws Exception {
	            /*mockMvc.perform(MockMvcRequestBuilders.post("/admiShowList/toInsertItemTest")
	            .param("name", "ビーフカレー")
	            .param("description", ""))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.view().name("/admiShowList/toInsertItemTest"))
	            .andExpect(MockMvcResultMatchers.model().attributeHasErrors("itemForm"))
	            .andExpect(MockMvcResultMatchers.model().errorCount(1))
	            .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("itemForm", "description", "NotBlank"));*/
	        	
	        	mockMvc.perform(get("/insertItem")
	    	 	.param("name", " "))
	        	.andExpect(view().name("item/item_insert"));
	        }
	        	
	        
		 	 @Test
			 @DisplayName("画像アップロード")
			 	void insertItemTest() throws Exception {
			 		MockMultipartFile multipartFile = createMockMultipartFile("imagePath", "carry.jpg");
			 		mockMvc.perform(multipart("/insertItem")
			 				.file(multipartFile)
			 				.param("name", "テストカレー")
			 				.param("description", "画像アップロードテストカレー")
			 				.param("priceM", "1000")
			 				.param("priceL", "2000")
			 				.param("deleted", "false"))
			 				.andExpect(view().name("redirect:/admiShowList"));
		 	 }
			 		
			 		// MockMultipartFile作成
			 	private MockMultipartFile createMockMultipartFile(String key, String testFileName) {
			 		try {
			 			return new MockMultipartFile(key // ファイルの名前
			 					,testFileName
			 					,null
			 					,new FileInputStream("src/test/resources/" + testFileName));// ファイルの内容
			 			} catch (IOException e) {
			 				throw new RuntimeException(e);
			 			}
			 		}

			 	

	       @Test
	 	   @DisplayName("商品編集画面を表示")
	 	   void admiDetail() throws Exception {
	 	        // ①コントローラー呼出し
	    	   
	    	   	mockMvc.perform(get("/admiDetail")
	    		.param("id","1"))
	    	   	.andExpect(view().name("item/item_edit"));

  		
	       }
	 	        
	       @Test
	 	   @DisplayName("論理削除（UPDATE items SET deleted = true>=csv 商品編集画面に遷移）")
	 	   @ExpectedDatabase(value = "/item", assertionMode = DatabaseAssertionMode.NON_STRICT)
	 	   void admiDelete() throws Exception {
	 		  MvcResult mvcResult = mockMvc.perform(get("/admiDelete")
	 	                .param("id","1"))
	 	        		.andExpect(view().name("redirect:/admiShowList"))
	 	                .andReturn();
	 		  
				
			 }
	 	   
	 	  @Test
	 	  @DisplayName("エラーが存在する場合には、商品編集画面に遷移")
	 	  void admiUpdate1() throws Exception {
	 		 mockMvc.perform(get("/admiUpdate")
	 			.param("id","1")
	 			.param("name", " ")
	 			.param("description", " ")
	 			.param("price_m"," ")
	 			.param("price_m",""))
	 		 	.andExpect(view().name("item/item_edit"));
	 		  
	 	  }
	 	  

		 @Test
		 @DisplayName("画像アップロード")
			 void admiUpdate2() throws Exception {
			 	MockMultipartFile multipartFile = createMockMultipartFile2("imagePath", "carry2.jpg");
			 	mockMvc.perform(multipart("/admiUpdate")
			 			.file(multipartFile)
			 			.param("name", "テストカレー")
			 			.param("description", "画像アップロードテストカレー")
			 			.param("priceM", "1000")
			 			.param("priceL", "2000")
			 			.param("deleted", "false"))
			 			.andExpect(view().name("redirect:/admiShowList"));
		 	 }
			 		
			 		// MockMultipartFile作成
			 	private MockMultipartFile createMockMultipartFile2(String key, String testFileName2) {
			 		try {
			 			return new MockMultipartFile(key // ファイルの名前
			 					,testFileName2
			 					,null
			 					,new FileInputStream("src/test/resources/" + testFileName2));// ファイルの内容
			 			} catch (IOException e) {
			 				throw new RuntimeException(e);
			 			}
			 		}



}
