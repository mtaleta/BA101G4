package com.news.model;

import java.util.List;

public interface NewsDAO_interface {

	public void insert(NewsVO newsVO);
	public void update(NewsVO newsVO);
	public void delete(String news_id);
	public NewsVO findByPrimaryKey(String news_id);
	public List<NewsVO> getAll();

	// 新增
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	// 晨均 晨均 晨均
	public List<NewsVO> getAllPassNews();
	public List<NewsVO> getAllNoPassNews();
	public void updatePass(String news_id);
	public List<NewsVO> getAllNewsByStore_id(String store_id);
	public List<NewsVO> getLittlePassNews();
	public List<NewsVO> getNewsByClass(String news_class);
	
}