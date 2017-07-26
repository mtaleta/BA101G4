package com.admin.model;

import java.util.List;
import java.util.Set;

import com.auth.model.AuthVO;

public interface AdminDAO_interface {

	public AdminVO insert(AdminVO adminVO);
	public AdminVO insert(AdminVO adminVO, String[] feature);
	public void update(AdminVO adminVO, String[] feature);
	public void delete(String admin_id);
	public AdminVO findByPrimaryKey(String admin_id) ;
	public List<AdminVO> getAll() ;
	public Set<AuthVO> getAuthsByAdmin_id(String admin_id);
	public AdminVO login_Admin(AdminVO adminVO);
	public void Admin_Forget_Password(String admin_acct, String admin_pwd);
}