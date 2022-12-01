package com.example.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.domain.CartItem;
import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.domain.User;



public class HyperSessionUtil {

	//必要なsession情報を入れる
	public static MockHttpSession createUserIdAndUserSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<>();
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

	
	public static MockHttpSession createUserIdAndOrderCompletionSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("test@gmail.com");
		user.setPassword("testpassword");
		user.setZipcode("111-1111");
		user.setAddress("テスト住所");
		user.setTelephone("0000-0000-0000");
		sessionMap.put("user", user);

		CartItem cart = new CartItem();
		List<CartItem> cartList = new ArrayList<>();
		
		cart.setItemId(1);
		cart.setName("カツカレー");
		cart.setItemPrice(300);
		cart.setSize("M");
		cart.setQuantity(2);
		cart.setSubTotal(300);
		Topping topp = new Topping();
		List<Topping> toppingList = new ArrayList<>();
		topp.setId(1);
		topp.setName("ナス");
		topp.setPriceM(150);
		topp.setPriceL(200);
		toppingList.add(topp);
		cart.setToppingList(toppingList);
		cartList.add(cart);
		sessionMap.put("cartItemList", cartList);// List<CartItem>
		sessionMap.put("totalPrice", 1000);
		

		
		OrderItem orderitem = new OrderItem();
		List<OrderItem> orderItemList = new ArrayList<>();
		Order order = new Order();
		List<Order> orderList = new ArrayList<>();
		
		orderitem.setId(1);
		orderitem.setItemId(1);
		orderitem.setOrderId(1);
		orderitem.setQuantity(1);
		orderitem.setSize("M");
		orderitem.setSubTotal(300);
		Item item = new Item();
		item.setName("カツカレー");
		orderitem.setItem(item);
		Topping topping = new Topping();
		OrderTopping orderTopping = new OrderTopping();
		List<OrderTopping> orderToppingList = new ArrayList<>();
		topping.setId(1);
		topping.setName("ナス");
		topping.setPriceM(150);
		topping.setPriceL(200);
		orderTopping.setTopping(topping);
		orderToppingList.add(orderTopping);
		orderitem.setOrderTopping(orderToppingList);
		orderItemList.add(orderitem);
		order.setOrderItemList(orderItemList);
		
		
		
		order.setId(1);
		order.setUserId(1);
		order.setStatus(1);
		order.setTotalPrice(300);
		String date = "2019-05-01";
		Date orderdate = Date.valueOf(date);
		order.setOrderDate(orderdate);
		order.setDestinationName("テストユーザー");
		order.setDestinationEmail("test@gmail.com");
		order.setDestinationZipcode("111-1111");
		order.setDestinationAddress("テスト住所");
		order.setDestinationTel("0000-0000-0000");
		order.setPaymentMethod(1);
		order.setUser(user);
		String datetime = "2019-05-01 09:02:03.123456789";
		Timestamp timestamp = Timestamp.valueOf(datetime);
		order.setDeliveryTime(timestamp);
		
		orderList.add(order);
		sessionMap.put("orderList", orderList);

		return createMockHttpSession(sessionMap);
	}


//	public static MockHttpSession createUserIdAndOrderHistorySession() {
//		Map<String, Object> sessionMap = new LinkedHashMap<>();
//		User user = new User();
//		user.setId(1);
//		user.setName("テストユーザ");
//		user.setEmail("test@gmail.com");
//		user.setPassword("testpassword");
//		user.setZipcode("1111111");
//		user.setAddress("テスト住所");
//		user.setTelephone("テスト電話番号");
//		sessionMap.put("user", user);
//
//		Order order = new Order();
//		String date = "2019-05-01";
//		Date orderdate = Date.valueOf(date);
//		order.setOrderDate(orderdate);
//		order.setTotalPrice(1000);
//		order.setId(1);
//		String datetime = "2019-05-01 01:02:03.123456789";
//		Timestamp timestamp = Timestamp.valueOf(datetime);
//		order.setDeliveryTime(timestamp);
//		order.setDestinationZipcode("123-4567");
////		OrderItem orderItem = new OrderItem();
////
////		order.setOrderItemList(orderItem);
//		sessionMap.put("orderList", order);
//
//		List<OrderItem> orderItemList = new ArrayList<>();
//		OrderItem orderitem = new OrderItem();
//		orderitem.setId(1);
//		orderItemList.add(orderitem);
//
//
//		return createMockHttpSession(sessionMap);
//	}
//
//
//	public static MockHttpSession createOrderDetailSession() {
//		Map<String, Object> sessionMap = new LinkedHashMap<>();
//
//		Order order = new Order();
//		String date = "2019-05-01";
//		Date orderdate = Date.valueOf(date);
//		order.setOrderDate(orderdate);
//		String datetime = "2019-05-01 01:02:03.123456789";
//		Timestamp timestamp = Timestamp.valueOf(datetime);
//		order.setDeliveryTime(timestamp);
//		order.setDestinationZipcode("123-4567");
//		sessionMap.put("orderList", order);
//
//
//		List<OrderItem> orderItemList = new ArrayList<>();
//		OrderItem orderitem = new OrderItem();
//		orderitem.setId(1);
//		orderItemList.add(orderitem);
//		sessionMap.put("orderItemList", orderItemList);
//
//		return createMockHttpSession(sessionMap);
//	}

//	String datetime = "2019-05-01 01:02:03.123456789";
//	Timestamp timestamp = Timestamp.valueOf(datetime);
//	order.setDeliveryTime(timestamp);
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
