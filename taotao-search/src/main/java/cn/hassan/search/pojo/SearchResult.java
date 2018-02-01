package cn.hassan.search.pojo;

import cn.hassan.search.vo.Item;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

	//商品列表
	private List<Item> itemList;
	//总记录数
	private long recordCount;
	//总页数
	private long pageCount;
	//当前页
	private long curPage;
	
}
