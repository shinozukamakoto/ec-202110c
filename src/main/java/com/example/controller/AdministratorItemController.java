package com.example.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Item;
import com.example.form.ItemForm;
import com.example.service.AdministratorItemService;
import com.example.service.ItemService;

@Controller
@RequestMapping("")
public class AdministratorItemController {

	@Autowired
	private AdministratorItemService service;
	
	@Autowired
	private ItemService itemService;
	
	// 1ページに表示する数は5名
	private static final int VIEW_SIZE = 5;
	
	@ModelAttribute
	public ItemForm setItemForm(){
		return new ItemForm();
	}
	
	@RequestMapping("/admiShowList")
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
		return "item/admi_item_list_curry";
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
				pageNumbers = new ArrayList<Integer>();
				for (int i = 1; i <= totalPages; i++) {
					pageNumbers.add(i);
				}
			}
			System.out.println(itemPage);
			model.addAttribute("itemPage", itemPage);
			return pageNumbers;
		}
	
	@RequestMapping("/toInsertItem")
	public String toInsertItem() {
		return "item/item_insert";
	}
	
	@RequestMapping("/insertItem")
	public String insertItem(@Validated ItemForm form,
							BindingResult result,
							Model model) {
		
		if(result.hasErrors()) {
			return "item/item_insert";
		}
	
	//	画像の名前を取得・ファイルに保存
	MultipartFile multiFile = form.getImagePath();
	String fileName = multiFile.getOriginalFilename();
	
	File filepath = new File("src/main/resources/static/img_curry/"+fileName);
	try {
		byte[] bytes = multiFile.getBytes();
		FileOutputStream stream = new FileOutputStream(filepath.toString());
		stream.write(bytes);
		stream.close();
	}catch(Exception e) {
		e.printStackTrace();
	}

		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setPriceM(form.getIntPriceM());
		item.setPriceL(form.getIntPriceL());
		item.setImagePath(fileName);
		item.setId(service.getId());
		service.insert(item);
		return "redirect:/admiShowList";
	}
	
	/**
	 * 商品詳細画面を表示
	 * 
	 * @param id　商品ID
	 * @param model リクエストスコープ
	 * @return item/detail.html
	 */
	@RequestMapping("/admiDetail")
	public String showItemDetail(String id, Model model) {
		
		
		
		//商品詳細を表示させる
		Item item = service.showDetail(Integer.parseInt(id));
		model.addAttribute("item", item);
		
		return "item/item_edit";
	}
	
	@RequestMapping("/admiDelete")
	public String delete(Integer id) {
		System.out.println(id);
		service.delete(id);
		return "redirect:/admiShowList";
	}
	
	@RequestMapping("/admiUpdate")
	public String admiUpdate(@Validated ItemForm form,
				BindingResult result,
				Model model,
				Integer id) {
		
		if(result.hasErrors()) {
			System.out.println("aaa");
			return showItemDetail(Integer.toString(id),model);
		}
		
//		画像の名前を取得・ファイルに保存
		MultipartFile multiFile = form.getImagePath();
		String fileName = multiFile.getOriginalFilename();
		System.out.println(form);
		System.out.println(fileName);
		
		File filepath = new File("src/main/resources/static/img_curry/"+fileName);
		try {
			byte[] bytes = multiFile.getBytes();
			FileOutputStream stream = new FileOutputStream(filepath.toString());
			stream.write(bytes);
			stream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

			Item item = new Item();
			BeanUtils.copyProperties(form, item);
			item.setPriceM(form.getIntPriceM());
			item.setPriceL(form.getIntPriceL());
			item.setImagePath(fileName);
			item.setId(id);
			System.out.println(item.getImagePath());
			service.update(item);
			return "redirect:/admiShowList";
	}
}
