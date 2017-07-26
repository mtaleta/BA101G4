package com.admin.model;

import java.util.List;

import com.auth.model.AuthDAO;
import com.auth.model.AuthDAO_interface;
import com.auth.model.AuthVO;
import com.member.model.MemberVO;

public class AdminService {

	private AdminDAO_interface dao;
	private AuthDAO_interface authDao;

	public AdminService() {
		dao = new AdminDAO();
		authDao = new AuthDAO();
	}

	public AdminVO addAdmin(String admin_acct, String admin_pwd, String admin_name, String admin_email,
			Integer admin_employed, byte[] admin_img, String[] feature) {

		AdminVO adminVO = new AdminVO();

		adminVO.setAdmin_acct(admin_acct);
		adminVO.setAdmin_pwd(admin_pwd);
		adminVO.setAdmin_name(admin_name);
		adminVO.setAdmin_email(admin_email);
		adminVO.setAdmin_employed(admin_employed);
		adminVO.setAdmin_img(admin_img);
		return dao.insert(adminVO, feature);
	}

	public AdminVO updateAdmin(String admin_id, String admin_acct, String admin_name, String admin_email,
			Integer admin_employed, byte[] admin_img, String[] feature) {

		AdminVO adminVO = this.getOneAdmin(admin_id);
		adminVO.setAdmin_id(admin_id);
		adminVO.setAdmin_acct(admin_acct);
		adminVO.setAdmin_name(admin_name);
		adminVO.setAdmin_email(admin_email);
		adminVO.setAdmin_employed(admin_employed);
		adminVO.setAdmin_img(admin_img);
		dao.update(adminVO, feature);
		return this.getOneAdmin(admin_id);
	}

	public AdminVO update_admin_personal(String admin_id, String admin_name, String admin_email, byte[] admin_img) {

		AdminVO adminVO = this.getOneAdmin(admin_id);
		adminVO.setAdmin_id(admin_id);
		adminVO.setAdmin_name(admin_name);
		adminVO.setAdmin_email(admin_email);
		adminVO.setAdmin_img(admin_img);
		dao.update(adminVO, null);
		return this.getOneAdmin(admin_id);
	}

	public void deleteAdmin(String admin_id) {
		dao.delete(admin_id);
	}

	public AdminVO getOneAdmin(String admin_id) {
		AdminVO vo = dao.findByPrimaryKey(admin_id);
		if (vo != null) {
			vo.setAuths(authDao.findByPrimaryKey(admin_id));
		}
		return vo;
	}

	public List<AdminVO> getAll() {
		return dao.getAll();
	}

	public AdminVO login_Admin(String admin_acct, String admin_pwd) {

		AdminVO adminVO = new AdminVO();
		adminVO.setAdmin_acct(admin_acct);
		adminVO.setAdmin_pwd(admin_pwd);
		return dao.login_Admin(adminVO);
	}

	public void Admin_Forget_Password(String admin_acct, String admin_pwd) {
		AdminVO adminVO = new AdminVO();
		adminVO.setAdmin_acct(admin_acct);
		adminVO.setAdmin_pwd(admin_pwd);

		dao.Admin_Forget_Password(admin_acct, admin_pwd);
		;
	}
}
