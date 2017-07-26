package com.sylvie.serverendpoint;
import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

@ServerEndpoint("/MyEchoServer/{orderList}")
public class MyEchoServer {
	
private static final Map<Session, ArrayList<String>> allSessions = Collections.synchronizedMap(new HashMap<Session, ArrayList<String>>());
	
	@OnOpen
	public void onOpen(@PathParam("orderList") String orderList, Session userSession) throws IOException {
		ArrayList<String> orderArrlist = new ArrayList<String>();
		List<String> orderlist = Arrays.asList(orderList.split("_"));
		orderArrlist.addAll(orderlist);
		allSessions.put(userSession, orderArrlist);

	}

	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		
		JsonParser parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) parser.parse(message);
		String ordId = jsonObj.get("ord_id").toString();
		System.out.println(ordId.substring(1, ordId.length()-1));
		
		for (Session session : allSessions.keySet()) {
			if (session.isOpen()&&allSessions.get(session).contains(ordId.substring(1, ordId.length()-1))) {
				session.getAsyncRemote().sendText(message);									
			}
		}
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		allSessions.remove(userSession);
		System.out.println(userSession.getId() + ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
	}

 
}
