package com.photo_store.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.photo_store.model.Photo_storeService;
import com.photo_store.model.Photo_storeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Photo_storeServlet
 */
public class Photo_storeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Photo_storeServlet() {
        super();
    }


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
		if("insert".equals(action)) {
			String store_id = req.getParameter("store_id");
			String photos = req.getParameter("photo");
			Gson gson = new Gson();
			String[] photo = gson.fromJson(photos, String[].class);
								
			Photo_storeService photo_storeSvc = new Photo_storeService();
			byte[] encoded;
			for(int i = 0; i < photo.length; i++) {
				String str = photo[i];
				System.out.print(str);			
				encoded = Base64.getDecoder().decode(str);
				Photo_storeVO photo_storeVO = photo_storeSvc.insert(null,encoded,store_id,null);				
			}
			List<Photo_storeVO> photoStoreList = photo_storeSvc.getPhotoByStore_id(store_id);
			List<String> encodedList = new ArrayList<String>();
//			if(photoStoreList.size() > 1) {
				for(Photo_storeVO photoStoreVO : photoStoreList) {
					encodedList.add(Base64.getEncoder().encodeToString(photoStoreVO.getPhoto()));
				}
				
//			}
			
			JSONArray array = new JSONArray();
			
			JSONObject obj = new JSONObject();
			try{
				obj.put("photo_store", encodedList);

			}catch(Exception e){}
			array.add(obj);
		
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;
		}
		// 晴帆 晴帆 晴帆
		// 晴帆 晴帆 晴帆
	}

}
