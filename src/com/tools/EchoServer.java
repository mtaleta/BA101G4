package com.tools;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.orderlist.model.OrderlistService;
import com.orderlist.model.OrderlistVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;

import net.sf.json.JSONObject;

@ServerEndpoint("/EchoServer/{myRoom}")
public class EchoServer {

	private static final Hashtable<String, Set<Session>> allRooms = new Hashtable<String, Set<Session>>();

	@OnOpen
	public void onOpen(@PathParam("myRoom") String myRoom, Session userSession) throws IOException {

		Set<Session> allSessions = allRooms.get(myRoom);
		
		if(allSessions != null){
			allSessions.add(userSession);
		}
		else{
			allSessions = Collections.synchronizedSet(new HashSet<Session>());
			allSessions.add(userSession);
			allRooms.put(myRoom, allSessions);
		}

	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		
		JSONObject jsonObject = JSONObject.fromObject(message);
	    Map map = jsonObject;

	    String ord_id = (String)map.get("ord_id");
	    String ord_shipping = (String)map.get("ord_shipping");
	    
	    shipping(ord_id, ord_shipping);
	    
	    Set<Session> allSessions = allRooms.get(ord_id);

	    for (Session session : allSessions) {
			if (session.isOpen()){
				session.getAsyncRemote().sendText(ord_shipping);
			}
		}

		System.out.println("Message received: " + message);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
//		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		
		for(Object key : allRooms.keySet()){

			Set<Session> allSessions = allRooms.get(key);
			System.out.println("remove: " + allSessions.remove(userSession));

			System.out.println("allSessions.size(): " + allSessions.size());
			
			if(allSessions.size() == 0){
				System.out.println("remove room " + key + "!!!");
				allRooms.remove(key);
			}
		}
	}
	
	private void shipping(String ord_id, String str_ord_shipping){
		
		/*************************** 1.接收請求參數 ****************************************/
		Integer ord_shipping = new Integer(str_ord_shipping);

		/*************************** 開始查詢資料 ****************************************/
		OrderlistService ordSvc = new OrderlistService();
		OrderlistVO orderlistVO = ordSvc.getOneOrderlist(ord_id);

		String mem_id = orderlistVO.getMem_id();
		String store_id = orderlistVO.getStore_id();
		Integer ord_total = orderlistVO.getOrd_total();
		Integer ord_pick = orderlistVO.getOrd_pick();
		String ord_add = orderlistVO.getOrd_add();
		Timestamp ord_time = orderlistVO.getOrd_time();
		Integer score_seller = null;

		/*************************** 開始修改資料 *****************************************/
		if (ord_shipping != 5) {
			orderlistVO = ordSvc.updateOrderlist(ord_id, mem_id, store_id, ord_total, ord_pick, ord_add, ord_shipping, ord_time, score_seller);
		} else { // ord_shipping == 5
			StoreService storeSvc = new StoreService();
			StoreVO storeVO = storeSvc.getOneStore(store_id);
			storeVO.setStore_points(storeVO.getStore_points() + ord_total);

			orderlistVO = ordSvc.updateWithStore(ord_id, mem_id, store_id, ord_total, ord_pick, ord_add, ord_shipping, ord_time, score_seller, storeVO);
		}
	}
}
