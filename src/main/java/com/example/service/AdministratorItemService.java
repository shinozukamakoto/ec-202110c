package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.AdministratorItemRepository;

@Service
@Transactional
public class AdministratorItemService {

	@Autowired
	private AdministratorItemRepository repository;
	
	/**
	 * 新しいIDの取得
	 * @return
	 */
	public Integer getId() {
		return repository.getPrimaryId();
	}
	
	/**
	 * 商品の登録
	 * @param item
	 */
	public void insert(Item item) {
		repository.insert(item);
	}
	
	/**
	 * 商品編集画面を表示
	 * @param id
	 * @return
	 */
	public Item showDetail(Integer id) {
		Item item = repository.showDetail(id);
		return item;
	}
	/**
	 * 商品の削除
	 * @param item
	 */
	public void delete(Integer id) {
		repository.delete(id);
	}
	
	public void update(Item item) {
		repository.update(item);
	}
}
