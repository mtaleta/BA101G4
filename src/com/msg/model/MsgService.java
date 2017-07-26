package com.msg.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.reply.model.ReplyVO;

public class MsgService {

	private MsgDAO_interface dao;
	
	public MsgService() {
		dao = new MsgDAO();
	}
	
	public MsgVO addMsg(String mem_id, String prod_id, String msg_content, Timestamp msg_date) {

		MsgVO msgVO = new MsgVO();

		msgVO.setMem_id(mem_id);
		msgVO.setProd_id(prod_id);
		msgVO.setMsg_content(msg_content);
		msgVO.setMsg_date(msg_date);
		dao.insert(msgVO);

		return msgVO;
	}
	
	public MsgVO getOneMsg(String msg_id) {
		return dao.findByPrimaryKey(msg_id);
	}
	
	public List<MsgVO> getAll(){
		return dao.getAll();
	}
	
	public Set<ReplyVO> getReplysByMsg_id(String msg_id) {
		return dao.getReplysByMsg_id(msg_id);
	}
}
