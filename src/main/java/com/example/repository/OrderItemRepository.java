package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * order_itemsとやりとりする
 * @author naramasato
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

//	private static final RowMapper<OrderItemRepository> ORDER_ITEM_ROW_MAPPER
//		 = new BeanPropertyRowMapper<>(OrderItemRepository.class);

	/**
	 * order_itemsにINSERTする
	 * @param orderItem
	 * @return　自動採番されたid
	 */
	public Integer order(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);

		String sql = "INSERT INTO order_items(item_id, order_id, quantity, size, sub_total) "
				+ "VALUES(:itemId, :orderId, :quantity, :size, :subTotal)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = {"id"};
		template.update(sql, param, keyHolder, keyColumnNames);

		orderItem.setId(keyHolder.getKey().intValue());

		return orderItem.getId();
	}
}
