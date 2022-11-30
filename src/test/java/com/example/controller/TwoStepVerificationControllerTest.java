package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpSession;

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
import com.example.util.HyperSessionUtil;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
//@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
class TwoStepVerificationControllerTest {
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
	@DisplayName("メールアドレス入力")
	void testMailInsert() throws Exception {
		MockHttpSession emailCheckSession = HyperSessionUtil.createUserIdAndUserSession();
		MvcResult mvcResult = mockMvc.perform(get("/mailInsert")
				.session(emailCheckSession))
				.andExpect(view().name("/insert/mail_insert"))
				.andReturn();

		HttpSession session = mvcResult.getRequest().getSession();
		String emailcheck = (String) session.getAttribute("emailcheck");
		String email = (String) session.getAttribute("email");
		String checkPass = (String) session.getAttribute("checkPass");
		assertNull(emailcheck);
		assertNull(email);
		assertNull(checkPass);
	}

	@Test
	@DisplayName("入力されたメールアドレスに送信（失敗")
	@DatabaseSetup("/User/insert_01/expected")
	void testMailSend01() throws Exception {
		mockMvc.perform(post("/mailsend"))
				.andExpect(view().name("/insert/mail_insert"))
				.andReturn();
	}

	@Test
	@DisplayName("入力されたメールアドレスに送信（成功")
	@DatabaseSetup("/User/insert_01/expected")
	void testMailSend02() throws Exception {
		mockMvc.perform(post("/mailsend")
				.param("mail", "yamada@example.com"))
				.andExpect(view().name("redirect:/passCheck"))
				.andReturn();
	}

	@Test
	@DisplayName("メールアドレス確認(null)")
	void testPassCheck01() throws Exception {
		mockMvc.perform(get("/passCheck")
				.param("emailCheck", ""))
				.andExpect(view().name("redirect:/mailInsert"));
	}

	@Test
	@DisplayName("メールアドレス確認(確認）")
	void testPassCheck02() throws Exception {
		MockHttpSession userSession = HyperSessionUtil.createUserIdAndUserSession();
		mockMvc.perform(get("/passCheck")
				.session(userSession))
				.andExpect(view().name("/insert/pass_check"));
	}

	@Test
	@DisplayName("ユーザー登録画面への遷移（emailcheck=null）")
	void testCheck01() throws Exception {
		mockMvc.perform(get("/check"))
				.andExpect(view().name("redirect:/mailInsert"))
				.andReturn();
	}

	@Test
	@DisplayName("ユーザー登録画面への遷移（emailcheck=ok,message=OK）")
	void testCheck02() throws Exception {
		MockHttpSession userSession = HyperSessionUtil.createUserIdAndUserSession();
		mockMvc.perform(get("/check")
				.session(userSession)
				.param("passCheck", "OK"))
				.andExpect(view().name("redirect:/insert"));
	}

	@Test
	@DisplayName("ユーザー登録画面への遷移（emailcheck=ok,message=NG）")
	void testCheck03() throws Exception {
		MockHttpSession userSession = HyperSessionUtil.createUserIdAndUserSession();
		mockMvc.perform(get("/check")
				.session(userSession)
				.param("passCheck", "NG"))
				.andExpect(view().name("/insert/pass_check"));
	}
}
