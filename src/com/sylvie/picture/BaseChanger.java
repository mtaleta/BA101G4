package com.sylvie.picture;

import java.io.Serializable;
import java.util.Base64;

public class BaseChanger implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String baseStr;
	
	public BaseChanger(){}

	public String getBaseStr() {
		return baseStr;
	}

	public void setImgByte(byte[] imgByteArr) {
		this.baseStr = imgByteArr != null? Base64.getEncoder().encodeToString(imgByteArr): null;
	}

}
