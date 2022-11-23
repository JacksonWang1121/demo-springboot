package com.example.demospringboot.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * DRUID连接池
 * 
 * @author JacksonWang
 *
 */
public class DruidUtil {

	// mysql连接池配置文件
	private final static String DRUID_FILE = "/druid.properties";

	private static DataSource ds = null;

	/**
	 * 只创建一次连接池
	 */
	static {
		try {
			// 创建集合
			Properties properties = new Properties();
			// 读取配置文件,此处的Object可以是随便的一个类,只是为了获取类型
			properties.load(DruidUtil.class.getResourceAsStream(DRUID_FILE));
			// 获取数据源,即连接池
			ds = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 关闭连接,将释放后的连接归还连接池
	 */
	public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DruidUtil druidUtil = new DruidUtil();
		System.out.println(DruidUtil.class.getResource("/druid.properties"));
	}

}
