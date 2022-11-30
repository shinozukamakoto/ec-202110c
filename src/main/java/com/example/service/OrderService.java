package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.CartItem;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.domain.User;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * orderに関わる内容を行う
 * @author naramasato
 *
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private HttpSession session;

	@Autowired
	private MailSender sender;

	/**
	 * 注文詳細一件を取得
	 *
	 * @param orderId
	 * @return
	 */
	public List<Order> orderLoad(Integer orderId){
		return orderRepository.orderLoad(orderId);
	}

	/**
	 * 注文詳細全件を取得
	 * @param id
	 * @return
	 */
	public List<Order> findByOrder(Integer id){
		return orderRepository.findByOrdertable(id);
	}

	/**
	 * orderドメインに足りない物をセット
	 * @param order
	 */
	public void order(Order order) {
		order.setStatus(paymentMethodJudge(order));
		order.setUserId(getUserId());
		Integer orderId = orderRepository.insert(order);
		insertOrderItem(orderId);

	}

	/**
	 * statusを判別するメゾット
	 * @param order
	 * @return　statusを整数で返す
	 */
	public Integer paymentMethodJudge(Order order) {
		if(order.getPaymentMethod() == 1) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * ユーザーのIdを返すメゾット
	 * @return　userId
	 */
	public Integer getUserId() {
		User user = (User) session.getAttribute("user");
		return user.getId();
	}

	/**
	 * order_itemsテーブルにINSERTするメゾット
	 *
	 * @param orderId
	 */
	public void insertOrderItem(Integer orderId) {
		@SuppressWarnings("unchecked")
		List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
		for (CartItem cartItem : cartItemList) {
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(cartItem, orderItem);

			orderItem.setOrderId(orderId);
			Integer orderItemid = orderItemRepository.order(orderItem);

			InsertOrdertopping(orderItemid, cartItem.getToppingList());
		}
	}

	/**
	 * order_toppingsテーブルにセット
	 *
	 * @param orderItemId 注文商品の主キー
	 * @param toppingList　注文商品が持っているtoppingList
	 */
	public void InsertOrdertopping(Integer orderItemId, List<Topping> toppingList) {
		for (Topping topping : toppingList) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setOrderItemId(orderItemId);
			orderTopping.setToppingId(topping.getId());
			orderToppingRepository.insert(orderTopping);
		}
	}

	/**
	 * 引数で受け取ったemailに完了メールを送付
	 * @param email
	 */
    public void sendMail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("on99.matsunaga.dai@gmail.com");
        msg.setTo(email);
        msg.setSubject("注文完了！！！");//タイトルの設定
        msg.setText("ラクラクカリー より　注文完了"); //本文の設定

        this.sender.send(msg);
    }
}
