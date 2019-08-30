package testimport;



import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hwpf.extractor.WordExtractor;

import DBUtil.DBUtil;
import fenci.testfenci;;

public class poiutil {

	public void testpoi1() throws IOException
	{
		FileInputStream stream = new FileInputStream("C:\\Users\\hpnb\\Desktop\\课件\\下\\大数据1\\大数据技术应用大作业-1\\河北省信息技术手册1.doc");
		 WordExtractor wordExtractor = new WordExtractor(stream);
		 String[] paragraph = wordExtractor.getParagraphText();
		 System.out.println("该Word文件共有"+paragraph.length+"段。");
//		 System.out.println(paragraph[1]);
		 String fenlei="";
		 String name="";
		 int num=1;
		 Connection connection = DBUtil.getConnection();
			PreparedStatement preparedStatement = null;
			try {
		 for(int i=0;i<paragraph.length;i++)
		 {
			 if(paragraph[i].indexOf("章")>-1)
			 {
				 fenlei=paragraph[i].split("\t")[1];
				// System.out.println(fenlei); 
			 }
			 else
			 {
				
				 if(paragraph[i].split("\t")[0].equals(String.valueOf(num)))
				 {
					 name=paragraph[i].split("\t")[1];
//					 System.out.println(name);
//					 System.out.println(fenlei);
					 num++;
					
					
					String sql = "insert into reci (name,fenlei) values('"+name+"','"+fenlei+"')";
					 
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.executeUpdate();
				 }
			 }
		 }
		 System.out.println("插入成功！");
			 } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 finally {
					 DBUtil.close(connection);
					 DBUtil.close(preparedStatement);
				 }
	}
	public void testpoi2() throws IOException
	{
		ArrayList<String> list = new ArrayList<String>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = null;
		String sql = "select name from reci";
		ResultSet res = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			res = preparedStatement.executeQuery();
			while(res.next()) {
				list.add(res.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(res);
		}
		//System.out.println(list.get(1));
		FileInputStream stream = new FileInputStream("C:\\Users\\hpnb\\Desktop\\课件\\下\\大数据1\\大数据技术应用大作业-1\\河北省信息技术手册.doc");
		 WordExtractor wordExtractor = new WordExtractor(stream);
		 String[] paragraph = wordExtractor.getParagraphText();
		 System.out.println("该Word文件共有"+paragraph.length+"段。");
		 int num=0;
		 String jieshi="";
		 Connection connection1 = DBUtil.getConnection();
		PreparedStatement preparedStatement1 = null;
		 try {
//			 System.out.println("KKK"); 
		 for(int i=0;i<paragraph.length;i++)
		 {
			 //System.out.println(list.get(0)); 
			 if(!paragraph[i].equals("\r\n"))
			 {
			 if(paragraph[i].split("\r\n")[0].trim().equals(list.get(num)))
			 {
				 //System.out.println("KKK"); 
				 i++;
				 if(paragraph[i].split("\r\n")[0].equals(list.get(num)))
					 i++;
				 System.out.println(list.get(num));
				 if(num!=253)
				 {
				 while(!(paragraph[i].split("\r\n")[0].trim().equals(list.get(num+1))))
						 {
					 		jieshi+=paragraph[i];
					 		i++;
					 		while(paragraph[i].equals("\r\n"))
					 		{
					 			i++;
					 		}
					 		if(num==253)
					 		{
					 			if(paragraph[i].split("\r\n")[0].equals("附录"))
					 			{
					 				break;
					 			}
					 		}
						 }
//				 System.out.println(jieshi);
//				 if(list.get(num).equals("云计算"))
//					 System.out.println(list.get(num+1));
				 		i--;
					    sql="update reci set jieshi=?,fenlei2=?,guanjianzi=? where name=?";
						preparedStatement1 = connection1.prepareStatement(sql);
						preparedStatement1.setString(1, jieshi);
						List<String> keywordList = testfenci.getMainIdea(jieshi);
						preparedStatement1.setString(2, keywordList.get(0));
						preparedStatement1.setString(3, keywordList.toString());
						preparedStatement1.setString(4, list.get(num));
			            // 执行SQL语句，实现数据添加
						preparedStatement1.executeUpdate();
						System.out.println("更新成功！");
						num++;
						jieshi="";
						System.out.println(num);
			 }
				 else
				 {
					 while(!(paragraph[i].split("\r\n")[0].equals("附录")))
					 {
						jieshi+=paragraph[i];
				 		i++;
				 		while(paragraph[i].equals("\r\n"))
				 		{
				 			i++;
				 		}
					 }
					 i--;
					 sql="update reci set jieshi=?,fenlei2=?,guanjianzi=? where name=?";
						preparedStatement1 = connection1.prepareStatement(sql);
						preparedStatement1.setString(1, jieshi);
						//System.out.println(jieshi);
						List<String> keywordList = testfenci.getMainIdea(jieshi);
						preparedStatement1.setString(2, keywordList.get(0));
						preparedStatement1.setString(3, keywordList.toString());
						preparedStatement1.setString(4, list.get(num));
						// 执行SQL语句，实现数据添加
						preparedStatement1.executeUpdate();
						System.out.println("更新成功！");
						num++;
						jieshi="";
						System.out.println(num);
				 }
			 }
			 if(num==254)
			 {
				 break;
			 }
			 }
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		DBUtil.close(connection1);
		DBUtil.close(preparedStatement1);
	}
//		 for(String attribute : list)
//		 {
//			 
//		 }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		poiutil tp = new poiutil();
		try {
			//tp.testpoi1();
			tp.testpoi2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
