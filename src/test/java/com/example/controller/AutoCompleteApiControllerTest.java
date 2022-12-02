package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
class AutoCompleteApiControllerTest {

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
	  public void test() throws Exception {
	    MvcResult mvcResult = mockMvc.perform(get("/searchItem")).andReturn();

		String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ObjectMapper objectMapper = new ObjectMapper();
		List<String> itemList = objectMapper.readValue(result, new TypeReference<>() {});

		System.out.println(itemList);
		
		assertEquals("カツカレー", itemList.get(0), "No1 カツカレー");
		assertEquals("学芸会カレー", itemList.get(itemList.size()-2), "No17 学芸会カレー");
	  }
	
}