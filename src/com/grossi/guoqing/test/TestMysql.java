/**
 * 
 */
package com.grossi.guoqing.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.HashMap;
//import java.util.Map;

/**
 * @author Glory
 *
 */
public class TestMysql {

	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	
	private static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/glorydb";
	
	private static final String DB_USER = "root";
	
	private static final String DB_PASSWD = "Smjb_001";
	
	private static final String SQL = "select * from tbl_fruit";
	
	private Connection conn = null;
	
	private PreparedStatement pstmt = null;
	
	private ResultSet rset = null;
	
	private boolean initConn()
	{
		boolean ret = false;
		try
		{
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(MYSQL_URL, DB_USER, DB_PASSWD);
			if(conn != null)
			{
				ret = true;
			}
		}
		catch(Exception se)
		{
			se.printStackTrace();
		}
		
		
		return ret;
	}
	
	private void releaseResource(AutoCloseable res)
	{
		if(res != null)
		{
			try
			{
				res.close();
				res = null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void queryAndPrint() throws Exception
	{
		pstmt = conn.prepareStatement(SQL);
		rset = pstmt.executeQuery();
		//Map<String, String> oneRow = new HashMap<String,String>(); 
		StringBuilder sb = new StringBuilder("");
		while(rset.next())
		{
			Object obj = rset.getObject("FRUIT_ID");
			if(obj!=null)
			{
				sb.append("fruit_id:").append(obj.toString());
			}
			
			obj = rset.getObject("DESCRIPTION");
			if(obj!=null)
			{
				sb.append(",desctiption:").append(obj.toString());
			}
			
			obj = rset.getObject("PRICE");
			if(obj!=null)
			{
				sb.append(",price:").append(obj.toString());
			}
			System.out.println(sb.toString());
			sb.delete(0, sb.length());
		}
	}
	
	public void perform()
	{
		if(initConn())
		{
			try
			{
				queryAndPrint();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			releaseResource(rset);
			releaseResource(pstmt);
			releaseResource(conn);
		}
		else
		{
			System.err.println("connect to mysql db failed.");
		}
	}
	
	public static void main(String[] args)
	{
		TestMysql tm = new TestMysql();
		tm.perform();
	}
	
}
