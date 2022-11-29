//package com.example.controller;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.awt.PageAttributes.MediaType;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.example.domain.Item;
//import com.example.service.ItemService;

//class AutoCompleteApiControllerTest {
//
//	@RunWith(SpringRunner.class)
//	@WebMvcTest(AutoCompleteApiController.class)
//	public class ListCafeControllerTest {
//
//	  @Autowired
//	  private MockMvc mvc;
//
//	  @MockBean
//	  private ItemService ItemService;
//
//	  @Test
//	  public void test() throws Exception {
//	    Item item = new Item();
//	    item.setName("Test Item");
//
//	    List<Item> allNames = Arrays.asList(item);
//
//	    given(ItemService.findAll())
//        .willReturn(allNames);
//
//	    mvc.perform(get("/searchItem")
//	        .contentType(MediaType.APPLICATION_JSON))
//	        .andExpect(status().isOk())
//	        .andExpect(jsonPath("$", hasSize(1)))
//	        .andExpect(jsonPath("$[0].name", is(item.getName())));
//
//	  }
//
//	}
//}