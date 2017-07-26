package com.tools;

public class SpecialQuery {

	public static String multipleProd_name(String[] search){
		
		StringBuffer whereCondition = new StringBuffer();
		for(String keyword : search){
			whereCondition.append(" and upper(prod_name) like '%" + keyword + "%'");
		}
		return whereCondition.toString();
	}
	
}
