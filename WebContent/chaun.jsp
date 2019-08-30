<%@page import="java.util.ArrayList"%>
<%@page import="Dao.chaxunreci"%>
<%@page import="Bean.reci"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>热词查询</title>
<style>
.table-b table td{border:1px solid #C0C0C0}
</style>
</head>
<body>
<%  
	request.setCharacterEncoding("UTF-8");
	ArrayList<reci> cilist=new ArrayList<reci>();
	if(request.getParameter("reci")!=null)
	{
		String name=request.getParameter("reci");
		int num=Integer.parseInt(request.getParameter("select"));
		chaxunreci select=new chaxunreci();
		cilist=select.chaxun(name, num);
	}
%>
	<div align="center" class="table-b">
	<form action="chaun.jsp" method="post">
	<input type="text" name="reci" placeholder="热词">
	<select name="select">
	<option value="1" selected="selected">模糊查询</option>
	<option value="2">精确查询</option>
	</select>
	<button id="submit" type="submit">查询</button>
	</form>
	<%//out.print("<p>123</p>"); 
	if(!cilist.isEmpty())
	{
		out.print("<table border='0' cellspacing='0' cellpadding='0'>");
		out.print("<tr><td>热词</td><td>解释</td><td>分类</td></tr>");
		for(reci a:cilist)
		{
			out.print("<tr>");
			out.print("<td>"+a.getName()+"</td>");
			out.print("<td>"+a.getJieshi()+"</td>");
			out.print("<td>"+a.getFenlei()+"</td>");
			out.print("</tr>");
		}
		out.print("</table>");
	}
	else
	{
		out.print("查不到！");
	}
	%>
	</div>
</html>