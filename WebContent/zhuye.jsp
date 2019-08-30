<%@page import="Bean.reci"%>
<%@page import="Dao.chaxunreci"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>搜索</title>
    <link rel="stylesheet" href="./vendor/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet" href="./vendor/font-awesome/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="./css/styles.css">
    <style>
	.table-b table td{border:1px solid #C0C0C0}
	</style>
    <%ArrayList<String> list=new ArrayList<String>();
    chaxunreci selecta=new chaxunreci();
    list=selecta.chaxunfenlei();
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
</head>
<body class="sidebar-fixed header-fixed">
<div class="page-wrapper">
    <nav class="navbar page-header">
        <a href="#" class="btn btn-link sidebar-mobile-toggle d-md-none mr-auto">
            <i class="fa fa-bars"></i>
        </a>


        <h2  style="text-align:center" style="font-size:30px;color:#000000">信息技术手册查重分析</h2>
        </a>

    </nav>

    <div class="main-container" style="background:#FFF7FF">
        <div class="sidebar">
            <nav class="sidebar-nav" style="background:#0080FF">
                <ul class="nav">
                    <li class="nav-item">
                        <a href="index.html" class="nav-link active">
                            <i class="icon icon-speedometer"></i> 搜索
                        </a>
                    </li>
                    <li class="nav-item nav-dropdown">
                        <a href="#" class="nav-link nav-dropdown-toggle">
                            <i class="icon icon-target"></i> 分类 <%=list.size() %><i class="fa fa-caret-left"></i>
                        </a>

                        <ul class="nav-dropdown-items" style="background:#0080FF">
                            <%for(String fenlei:list)
                            	{
                            	%>
                            	<%System.out.println(fenlei); %>
                            	<li class="nav-item">
                                <a href="fenleizhanshi.jsp?fenlei=<%=fenlei %>" class="nav-link">
                                    <i class="icon icon-target"></i> <%=fenlei %>
                                </a>
                            	</li>
                            	<%
                            	}
                            	%>
                        </ul>
                    </li>
                     <li class="nav-item">
                        <a href="zhanshi.jsp" class="nav-link">
                            <i class="icon icon-grid"></i> PDF
                        </a>
                    </li>
                </ul>
            </nav>
        </div>

        <div class="content">
            <div class="container-fluid">
				<div align="center" class="table-b">
	<form action="zhuye.jsp" method="post">
	<input type="text" name="reci" placeholder="热词">
	<select name="select">
	<option value="1" selected="selected">模糊查询</option>
	<option value="2">精确查询</option>
	</select>
	<button id="submit" type="submit">查询</button>
	</form>
	<%
	if(!cilist.isEmpty())
	{
	   
		out.print("<table border='0' cellspacing='0' cellpadding='0'>");
		out.print("<tr><td>热词</td><td>解释</td><td>百度百科</td><td>查重标红</td><td>重复率</td><td>分类</td></tr>");
		for(reci a:cilist)
		{
			
			out.print("<tr>");
			out.print("<td>"+a.getName()+"</td>");
			out.print("<td>"+a.getJieshi()+"</td>");
			
			if(a.getBdwk()!=null)
				out.print("<td>"+a.getBdwk()+"</td>");
			else
				out.print("<td></td>");
			if(a.getChong()!=null)
				out.print("<td>"+a.getChong()+"</td>");
			else
				out.print("<td></td>");
			if(a.getNum()!=null)
				out.print("<td>"+a.getNum()+"</td>");
			else
				out.print("<td></td>");
			out.print("<td>"+a.getFenlei()+"</td>");
			out.print("</tr>");
		}
		out.print("</table>");
	}
	else
	{
		if(request.getParameter("reci")!=null)
		{
		out.print("查不到！");
		}
	}
	%>
	</div>
            </div>
        </div>
    </div>
</div>
<script src="./vendor/jquery/jquery.min.js"></script>
<script src="./vendor/popper.js/popper.min.js"></script>
<script src="./vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="./vendor/chart.js/chart.min.js"></script>
<script src="./js/carbon.js"></script>
<script src="./js/demo.js"></script>
</body>
</html>