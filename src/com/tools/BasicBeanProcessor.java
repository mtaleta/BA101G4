package com.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;

import org.apache.commons.dbutils.BeanProcessor;

public class BasicBeanProcessor extends BeanProcessor {

	@Override
	protected Object processColumn(ResultSet rs, int index, Class<?> propType) throws SQLException {
		if (!propType.isPrimitive() && rs.getObject(index) == null) {
			return null;
		}

		if (propType.equals(String.class)) {
			return rs.getString(index);

		} else if (propType.equals(Integer.TYPE) || propType.equals(Integer.class)) {
			return Integer.valueOf(rs.getInt(index));

		} else if (propType.equals(Boolean.TYPE) || propType.equals(Boolean.class)) {
			return Boolean.valueOf(rs.getBoolean(index));

		} else if (propType.equals(Long.TYPE) || propType.equals(Long.class)) {
			return Long.valueOf(rs.getLong(index));

		} else if (propType.equals(Double.TYPE) || propType.equals(Double.class)) {
			return Double.valueOf(rs.getDouble(index));

		} else if (propType.equals(Float.TYPE) || propType.equals(Float.class)) {
			return Float.valueOf(rs.getFloat(index));

		} else if (propType.equals(Short.TYPE) || propType.equals(Short.class)) {
			return Short.valueOf(rs.getShort(index));

		} else if (propType.equals(Byte.TYPE) || propType.equals(Byte.class)) {
			return Byte.valueOf(rs.getByte(index));
		} else if (propType.equals(Timestamp.class)) {
			return rs.getTimestamp(index);

		} else if (propType.equals(SQLXML.class)) {
			return rs.getSQLXML(index);
		} else if (propType.equals(byte[].class) || propType.equals(Byte[].class)) {
			return rs.getBytes(index);
		} else {
			return rs.getObject(index);
		}
	}
}