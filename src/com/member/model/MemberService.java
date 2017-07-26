package com.member.model;

import java.util.List;
import java.util.Set;

import com.activity.model.ActivityVO;
import com.orderlist.model.OrderlistVO;
import com.participant.model.ParticipantVO;
import com.spndcoffeelist.model.SpndcoffeelistVO;

public class MemberService {

	private MemberDAO_interface dao;

	public MemberService() {
		dao = new MemberDAO();
	}

	public MemberVO addMember(String mem_acct, String mem_pwd, String mem_name, String mem_tel, String mem_email,
			String mem_add, Integer mem_points, byte[] mem_img, String mem_validateCode) {

		MemberVO memberVO = new MemberVO();
		memberVO.setMem_acct(mem_acct);
		memberVO.setMem_pwd(mem_pwd);
		memberVO.setMem_name(mem_name);
		memberVO.setMem_tel(mem_tel);
		memberVO.setMem_email(mem_email);
		memberVO.setMem_add(mem_add);
		memberVO.setMem_points(mem_points);
		memberVO.setMem_img(mem_img);
		memberVO.setMem_validateCode(mem_validateCode);
		return dao.insert(memberVO);
	}

	public MemberVO updateMember(String mem_id, String mem_name, String mem_tel,
			String mem_email, String mem_add, Integer mem_points, byte[] mem_img) {

		MemberVO memberVO = this.getOneMember(mem_id);
		memberVO.setMem_id(mem_id);
		memberVO.setMem_name(mem_name);
		memberVO.setMem_tel(mem_tel);
		memberVO.setMem_email(mem_email);
		memberVO.setMem_points(mem_points);
		memberVO.setMem_img(mem_img);
		dao.update(memberVO);
		return this.getOneMember(mem_id);
	}

	public void deleteMember(String mem_id) {
		dao.delete(mem_id);
	}

	public MemberVO getOneMember(String mem_id) {
		return dao.findByPrimaryKey(mem_id);
	}

	public List<MemberVO> getAll() {
		return dao.getAll();
	}

	public MemberVO Login_Member(String mem_acct, String mem_pwd) {

		MemberVO memberVO = new MemberVO();
		memberVO.setMem_acct(mem_acct);
		memberVO.setMem_pwd(mem_pwd);
		return dao.login_Member(memberVO);
	}

	public MemberVO getOneMemberByAccount(String mem_acct) {
		return dao.findByAccount(mem_acct);
	}
	
	public void mem_Authentication(String mem_acct, Integer mem_authentication) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_acct(mem_acct);
		memberVO.setMem_authentication(mem_authentication);

		dao.MEM_AUTHENTICATION(mem_acct, mem_authentication);
	}

	public void mem_Forget_Password(String mem_acct, String mem_pwd){
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_acct(mem_acct);
		memberVO.setMem_pwd(mem_pwd);

		dao.Mem_Forget_Password(mem_acct, mem_pwd);
	}

	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣
	public Set<ParticipantVO> getParticipantsByMem_id(String mem_id){
		return dao.getParticipantsByMem_id(mem_id);
	}

	public Set<ActivityVO> getActivitysByMem_id(String mem_id) {
		return dao.getActivitysByMem_id(mem_id);
	}
	// 祈竣 祈竣 祈竣
	// 祈竣 祈竣 祈竣

	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByMem_idDesc(String mem_id) {
		return dao.getSpndcoffeelistsByMem_idDesc(mem_id);
	}
	// 安琪 安琪 安琪
	// 安琪 安琪 安琪
	
	// 強禾 強禾 強禾
	public MemberVO updateMember(String mem_id, String mem_acct, String mem_pwd, String mem_name, String mem_tel,
			String mem_email, String mem_add, Integer mem_points, byte[] mem_img, Integer mem_authentication, String mem_validatecode){
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMem_id(mem_id);
		memberVO.setMem_acct(mem_acct);
		memberVO.setMem_pwd(mem_pwd);
		memberVO.setMem_name(mem_name);
		memberVO.setMem_tel(mem_tel);
		memberVO.setMem_email(mem_email);
		memberVO.setMem_add(mem_add);
		memberVO.setMem_points(mem_points);
		memberVO.setMem_img(mem_img);
		memberVO.setMem_authentication(mem_authentication);
		memberVO.setMem_validateCode(mem_validatecode);
		dao.update(memberVO);
		
		return memberVO;
	}
	// 強禾 強禾 強禾
	
	// 強禾 強禾 強禾
	// 會員全部訂單
	public Set<OrderlistVO> getOrderlistsByMem_idDesc(String mem_id){
		return dao.getOrderlistsByMem_idDesc(mem_id);
	}
	// 強禾 強禾 強禾
	
	// 強禾 強禾 強禾
	// 會員單一種取貨方式的訂單
	public Set<OrderlistVO> getOrderlistsByMem_idAndOrd_pickDesc(String mem_id, Integer ord_pick){
		return dao.getOrderlistsByMem_idAndOrd_pickDesc(mem_id, ord_pick);
	}
	// 強禾 強禾 強禾
	
	// Adding by Sylvie
    public MemberVO update(String mem_id, String mem_acct, String mem_pwd, String mem_name, String mem_tel, String mem_email, String mem_add, Integer mem_points, byte[] mem_img) {
        MemberVO memberVO = new MemberVO();
        memberVO.setMem_id(mem_id);
        memberVO.setMem_acct(mem_acct);
        memberVO.setMem_pwd(mem_pwd);
        memberVO.setMem_name(mem_name);
        memberVO.setMem_tel(mem_tel);
        memberVO.setMem_email(mem_email);
        memberVO.setMem_add(mem_add);
        memberVO.setMem_points(mem_points);
        memberVO.setMem_img(mem_img);
        dao.update(memberVO);
        return memberVO;
    }

	// Adding by Sylvie
    public MemberVO findByPrimaryKey(String mem_id) {
        return dao.findByPrimaryKey(mem_id);
    }
	
	// Adding by Sylvie
    public Set<OrderlistVO> getOrderlistsByMem_id(String mem_id) {
    	return dao.getOrderlistsByMem_id(mem_id);
    }
    
    // Adding by Sylvie
//    public List<OrderlistVO> getOrderlistListByMem_id(String mem_id) {
//    	return dao.getOrderlistListByMem_id(mem_id);
//    }
    public List<OrderlistVO> getOrderlistListByMem_idAndOrd_pickDESC(String mem_id, Integer ord_pick, Integer ord_pick2) {
    	return dao.getOrderlistListByMem_idAndOrd_pickDESC(mem_id, ord_pick, ord_pick2);
    }
}
