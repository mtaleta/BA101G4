package com.news.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class NewsService {
private NewsDAO_interface dao;
	
	public NewsService(){
		dao = new NewsDAO();
	}
	
	public List<NewsVO> getAll(){
		return dao.getAll();
	}
	
	public NewsVO getNewsContentByNews_id(String news_id){
		return dao.findByPrimaryKey(news_id);
	}
	
	public List<NewsVO> getAllPassNews(){
		return dao.getAllPassNews();
	}
	
	public List<NewsVO> getLittlePassNews(){
		return dao.getLittlePassNews();
	}
	
	public List<NewsVO> getAllNoPassNews(){
		return dao.getAllNoPassNews();
	}
	
	
	public List<NewsVO> getNewsByClass(String news_class){
		return dao.getNewsByClass(news_class);
	}
	
	public void updatePass(String news_id){
		dao.updatePass(news_id);
	}
	
	public List<NewsVO> getAllNewsByStore_id(String stroe_id){
		return dao.getAllNewsByStore_id(stroe_id);
	}
	
	public NewsVO addNews(String store_id, String news_title, String news_content,
						byte[] news_img,Timestamp news_date, String news_class,Integer news_top,Integer news_pass){
		NewsVO newsVO = new NewsVO();
		
		newsVO.setStore_id(store_id);
		newsVO.setNews_title(news_title);
		newsVO.setNews_content(news_content);
		newsVO.setNews_img(news_img);
		newsVO.setNews_date(news_date);
		newsVO.setNews_class(news_class);
		newsVO.setNews_top(news_top);
		newsVO.setNews_pass(news_pass);
		dao.insert(newsVO);
		
		return newsVO;
	}
	
}
