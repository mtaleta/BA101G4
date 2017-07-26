package com.tools;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class DatabaseUtils {

	public static <T> Set<T> querySet(DataSource ds, String sql, Class<T> clazz, Object... params) throws SQLException {
		List<T> list = query(ds, sql, clazz, params);
		return new HashSet<T>(list);
	}

	public static <T> List<T> query(DataSource ds, String sql, Class<T> clazz, Object... params) throws SQLException {
		QueryRunner run = new QueryRunner(ds);
		RowProcessor processor = new BasicRowProcessor(new BasicBeanProcessor());
		ResultSetHandler<List<T>> rsh = new BeanListHandler<T>(clazz, processor);
		List<T> list = run.query(sql, rsh, params);
		return list;
	}

	public static <T> T queryOne(DataSource ds, String sql, Class<T> clazz, Object... params) throws SQLException {
		QueryRunner run = new QueryRunner(ds);
		RowProcessor processor = new BasicRowProcessor(new BasicBeanProcessor());
		ResultSetHandler<T> rsh = new BeanHandler<T>(clazz, processor);
		T object = run.query(sql, rsh, params);
		return object;
	}
}
