package com.example.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

@Repository
public class AdministratorItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};
	
	/**
	 * 最大のIDを取得
	 * @return
	 */
	public Integer getPrimaryId() {
		try {
			Integer maxId = template.queryForObject("SELECT MAX(id) FROM items;", new MapSqlParameterSource(),
					Integer.class);
			return maxId + 1;
		} catch (DataAccessException e) {
			// データが存在しない場合
			return 1;
		}
	}
	/**
	 * 商品の登録
	 * @param item
	 */
	public void insert(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "INSERT INTO items (id, name, description, price_m, price_l, image_path, deleted)"
				+ " VALUES (:id, :name, :description, :priceM, :priceL, :imagePath, 'f');";
		template.update(sql, param);
	}
	
	/**
	 * 商品編集画面を表示
	 */
	public Item showDetail(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "SELECT * FROM items WHERE id = :id;";
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
	
	/**
	 * 商品の削除
	 * @param item
	 */
	public void delete(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "UPDATE items SET deleted = true WHERE id = :id;";
		template.update(sql, param);
	}
	
	/**
	 * 商品の編集
	 * @param item
	 */
	public void update(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "UPDATE items SET name=:name, description=:description,"
				+ " price_m=:priceM, price_l=:priceL, image_path=:imagePath, deleted='f' WHERE id = :id;";
		template.update(sql, param);
	}
}
