package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.reci;
import DBUtil.DBUtil;

public class chaxunreci {
	public ArrayList<reci> chaxun(String name,int flag)
	{
		ArrayList<reci> list = new ArrayList<reci>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		reci recidemo=null;
		String sql = "select * from reci where name=?";
		ResultSet res = null;
		try {
			if(flag==1)
			{
				sql="select * from reci where name like ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, "%"+name+"%");
			}
			else
			{
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1,name);
			}
			res = preparedStatement.executeQuery();
			while(res.next()) {
				recidemo = new reci();
				recidemo.setName(res.getString("name"));
				recidemo.setFenlei(res.getString("fenlei2"));
				recidemo.setJieshi(res.getString("jieshi"));
				recidemo.setBdwk(res.getString("bdwk"));
				recidemo.setChong(res.getString("chong"));
				recidemo.setNum(res.getString("num"));
				list.add(recidemo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		return list;
	}
	public ArrayList<reci> chaxunlei(String fenlei)
	{
		ArrayList<reci> list = new ArrayList<reci>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		reci recidemo=null;
		String sql = "select * from reci where fenlei2=?";
		ResultSet res = null;
		try {
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1,fenlei);
		res = preparedStatement.executeQuery();
		while(res.next()) {
			recidemo = new reci();
			recidemo.setName(res.getString("name"));
			recidemo.setFenlei(res.getString("fenlei2"));
			recidemo.setJieshi(res.getString("jieshi"));
			recidemo.setBdwk(res.getString("bdwk"));
			recidemo.setChong(res.getString("chong"));
			recidemo.setNum(res.getString("num"));
			list.add(recidemo);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		return list;
	}
	public ArrayList<String> chaxunfenlei()
	{
		ArrayList<String> list = new ArrayList<String>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String fenlei=null;
		String sql = "select fenlei2 from reci";
		ResultSet res = null;
		int flag=0;
		try {
		preparedStatement = connection.prepareStatement(sql);
		res = preparedStatement.executeQuery();
		while(res.next()) {
			fenlei=res.getString("fenlei2");
			flag=0;
			for(String a:list)
			{
				if(a.equals(fenlei))
				{
					flag=1;
					break;
				}
			}
			if(flag==0)
			{
				list.add(fenlei);
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		return list;
	}
	public static ArrayList<String> chaxunname()
	{
		ArrayList<String> list = new ArrayList<String>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String fenlei=null;
		String sql = "select name from reci";
		ResultSet res = null;
		int flag=0;
		try {
		preparedStatement = connection.prepareStatement(sql);
		res = preparedStatement.executeQuery();
		while(res.next()) {
			fenlei=res.getString("name");
			list.add(fenlei);
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		return list;
	}
	public static void charubd(String name,String bdwk)
	{
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		 String sql="update reci set bdwk=? where name=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bdwk);
			preparedStatement.setString(2, name);
	        // 执行SQL语句，实现数据添加
			int r=preparedStatement.executeUpdate();
			if(r==1) {System.out.println("更新成功！");}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void charucf(String name,String chong,String num)
	{
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		 String sql="update reci set chong=?,num=? where name=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, chong);
			preparedStatement.setString(2, num);
			preparedStatement.setString(3, name);
	        // 执行SQL语句，实现数据添加
			int r=preparedStatement.executeUpdate();
			if(r==1) {System.out.println("更新成功！");}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<reci> chachong()
	{
		ArrayList<reci> list = new ArrayList<reci>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from reci";
		ResultSet res = null;
		reci are=null;
		try {
		preparedStatement = connection.prepareStatement(sql);
		res = preparedStatement.executeQuery();
		while(res.next()) {
			if(res.getString("bdwk") != null) {
			are=new reci();
			are.setName(res.getString("name"));
			are.setJieshi(res.getString("jieshi"));
			are.setBdwk(res.getString("bdwk"));
			list.add(are);
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		return list;
	}
	public static ArrayList<reci> zsc()
	{
		ArrayList<reci> list = new ArrayList<reci>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select * from reci";
		ResultSet res = null;
		reci are=null;
		try {
		preparedStatement = connection.prepareStatement(sql);
		res = preparedStatement.executeQuery();
		while(res.next()) {
			if(res.getString("chong") != null) {
			are=new reci();
			are.setName(res.getString("name"));
			are.setJieshi(res.getString("chong"));
			are.setNum(res.getString("num"));
			list.add(are);
			}
			else
			{
				are=new reci();
				are.setName(res.getString("name"));
				are.setJieshi(res.getString("jieshi"));
				list.add(are);
			}
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		return list;
	}
	public static void main(String[] args) {
		chaxunreci P=new chaxunreci();
		ArrayList<reci> list =null;
		//list=P.chaxun("互联网",0);
		list=P.chaxunlei("信息化热词");
		ArrayList<String> list1 =null;
		list1=P.chaxunname();
		if(list1.isEmpty())
		{
			System.out.println("找不到！");
		}
		else
		{
			for(String a :list1)
			{
				System.out.println(a);
			}
		}
//		if(list.isEmpty())
//		{
//			System.out.println("找不到！");
//		}
//		else
//		{
//			for(reci a :list)
//			{
//				System.out.println(a.getJieshi());
//			}
//		}
	}
}
