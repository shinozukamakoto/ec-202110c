package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.service.ItemService;

@Controller
@RequestMapping("")
public class ItemController {

	@Autowired
	private ServletContext application;

	@Autowired
	private ItemService itemService;

	// 1ページに表示する従業員数は5名
			private static final int VIEW_SIZE = 5;

	/**
	 * 従業員一覧画面を出力
	 * @param model
	 * @return
	 */
	@RequestMapping("/showList")
	public String showItem(Model model, Integer page, String searchName) {
		List<Item> itemList = null;


    	// ページング機能追加
    			if (page == null) {
    	// ページ数の指定が無い場合は1ページ目を表示させる
    				page = 1;
    			}

    			if(searchName == null) {
    	// 検索文字列が空なら全件検索
    				itemList = itemService.findAll();
    			} else {
    	// 検索文字列があれば曖昧検索
    				itemList = itemService.findByName(searchName);
    	// ページングの数字からも検索できるように検索文字列をスコープに格納しておく
    				model.addAttribute("searchName", searchName);
    				//該当する検索結果がなければ全件返す
    				if(itemList==null) {
    					itemList = itemService.findAll();
    					model.addAttribute("message", "該当する商品がありません");
    				}
    			}

    	// 表示させたいページ数、ページサイズ、従業員リストを渡し１ページに表示させる従業員リストを絞り込み
    			Page<Item> itemPage = itemService.showListPaging(page, VIEW_SIZE, itemList);
    			model.addAttribute("itemPage", itemPage);

    	// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
    			List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
    			model.addAttribute("pageNumbers", pageNumbers);
		return "item/item_list_curry";
	}

	 /**
		 * ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
		 *
		 * @param model        モデル
		 * @param employeePage ページング情報
		 */
		private List<Integer> calcPageNumbers(Model model, Page<Item> itemPage) {
			int totalPages = itemPage.getTotalPages();
			System.out.println(totalPages);
			List<Integer> pageNumbers = null;
			if (totalPages > 0) {
				pageNumbers = new ArrayList<>();
				for (int i = 1; i <= totalPages; i++) {
					pageNumbers.add(i);
				}
			}
			System.out.println(itemPage);
			model.addAttribute("itemPage", itemPage);
			return pageNumbers;
		}

	/*@RequestMapping("/searchItem")
	public String searchItem(Model model,Integer page, String searchName) {

		List<Item> itemList = itemService.findByName(searchName);
		//System.out.println(itemList.get(0));

		if(itemList == null) {
	// ページング機能追加
			if (page == null) {
	// ページ数の指定が無い場合は1ページ目を表示させる
				page = 1;
			}

			if(searchName == null) {
// 検索文字列が空なら全件検索
			itemList = itemService.findAll();
			} else {
// 検索文字列があれば曖昧検索
			itemList = itemService.findByName(searchName);
// ページングの数字からも検索できるように検索文字列をスコープに格納しておく
			model.addAttribute("searchName", searchName);
			}

	// 表示させたいページ数、ページサイズ、従業員リストを渡し１ページに表示させる従業員リストを絞り込み
			Page<Item> itemPage = itemService.showListPaging(page, VIEW_SIZE, itemList);
			model.addAttribute("itemPage", itemPage);

	// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
			List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
			model.addAttribute("pageNumbers", pageNumbers);
			model.addAttribute("message", "該当する商品がありません");

		}
		else{

	// 表示させたいページ数、ページサイズ、従業員リストを渡し１ページに表示させる従業員リストを絞り込み
			Page<Item> itemPage = itemService.showListPaging(page, VIEW_SIZE, itemList);


	// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき5件表示させる場合→1,2,3がpageNumbersに入る
			List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
			model.addAttribute("pageNumbers", pageNumbers);

			model.addAttribute("itemPage", itemList);
		}
		model.addAttribute("name", searchName);

		return showItem(model,page,searchName);
	}*/

	/**
	 * 商品詳細画面を表示
	 *
	 * @param id　商品ID
	 * @param model リクエストスコープ
	 * @return item/detail.html
	 */
	@RequestMapping("/detail")
	public String showItemDetail(String id, Model model) {


		//商品詳細を表示させる
		Item item = itemService.showItemDetail(Integer.parseInt(id));
		model.addAttribute("item", item);

		//トッピング一覧を表示
		List<Topping> toppingList = itemService.findAllTopping();

		application.setAttribute("toppingList", toppingList);
		return "item/item_detail";
	}
}
