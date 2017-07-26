package com.reply.model;

import java.sql.Timestamp;

public class ReplyService {

	private ReplyDAO_interface dao;
	
	public ReplyService() {
		dao = new ReplyDAO();
	}
	
	public ReplyVO addReply(String msg_id, String mem_id, String store_id, String reply_content, Timestamp reply_date) {

		ReplyVO replyVO = new ReplyVO();

		replyVO.setMsg_id(msg_id);
		replyVO.setMem_id(mem_id);
		replyVO.setStore_id(store_id);
		replyVO.setReply_content(reply_content);
		replyVO.setReply_date(reply_date);
		dao.insert(replyVO);

		return replyVO;
	}
	
}
