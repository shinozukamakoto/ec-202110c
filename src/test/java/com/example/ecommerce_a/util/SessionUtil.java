package com.example.ecommerce_a.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.domain.CartItem;
import com.example.domain.Order;
import com.example.domain.User;

public class SessionUtil {

	//必要なsession情報を入れる
	public static MockHttpSession createUserIdAndUserSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("test@gmail.com");
		user.setPassword("testpassword");
		user.setZipcode("1111111");
		user.setAddress("テスト住所");
		user.setTelephone("テスト電話番号");
		sessionMap.put("userId", user.getId());
		sessionMap.put("user", user);
		sessionMap.put("emailcheck", "yamada@example.com");
		sessionMap.put("email", "yamada@example.com");
		sessionMap.put("checkPass", "OK");
		return createMockHttpSession(sessionMap);
	}

	
	
	public static MockHttpSession createUserIdAndOrderSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("test@gmail.com");
		user.setPassword("testpassword");
		user.setZipcode("1111111");
		user.setAddress("テスト住所");
		user.setTelephone("テスト電話番号");
		sessionMap.put("user", user);
		
		Order order = new Order();
		List<CartItem> cartItemlist = new ArrayList<>();
		
//		sessionMap.put("orderItemList",order.setOrderItemList(list));	
		sessionMap.put("cartItemList", order);// List<CartItem>
		sessionMap.put("totalPrice", 1000);
		return createMockHttpSession(sessionMap);
	}

//	Order order = new Order();
//	List<OrderItem> orderItemList = new ArrayList<>();
//	order.setOrderItemList(orderItemList);	
//	OrderItem order = new OrderItem();
//	Item item = new Item();
//	item.setId(1);
//	item.setName("サウナタオル白");
//	item.setPriceS(1000);
//	order.setItemId(1);
//	order.setQuantity(10);
//	order.setSize('S');
//	order.setItem(item);
//	orderList.add(orderItem);
//	List<OrderOption> orderOptionList = new ArrayList<>();
//	orderItem.setOrderOptionList(orderOptionList);
//	sessionMap.put("shoppingCart", order);
//	return createMockHttpSession(sessionMap);
	

	//オーダー情報をsessionに入れるメソッド作成

	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}
}
