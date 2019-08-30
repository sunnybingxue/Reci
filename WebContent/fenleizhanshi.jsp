<%@page import="Bean.reci"%>
<%@page import="Dao.chaxunreci"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta charset="UTF-8">
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
	String name="";
	if(request.getParameter("fenlei")!=null)
	{
		name=request.getParameter("fenlei");
		chaxunreci select=new chaxunreci();
		cilist=select.chaxunlei(name);
	}
    %>
<title>分类展示</title>
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
                        <a href="zhuye.jsp" class="nav-link">
                            <i class="icon icon-speedometer"></i> 搜索
                        </a>
                    </li>

                    <li class="nav-item nav-dropdown">
                        <a href="#" class="nav-link nav-dropdown-toggle">
                            <i class="icon icon-target"></i> 分类  <%=list.size() %><i class="fa fa-caret-left"></i>
                        </a>

                        <ul class="nav-dropdown-items" style="background:#0080FF">
                            <%for(String fenlei:list)
                            	{
                            	%>
                            	<li class="nav-item">
                                <a href="fenleizhanshi.jsp?fenlei=<%=fenlei %>" class="nav-link<%if(name.equals(fenlei)){out.print(" active");} %>">
                                    <i class="icon icon-target"></i> <%=fenlei %>&nbsp&nbsp&nbsp&nbsp<%if(name.equals(fenlei)){out.print(cilist.size());} %>
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
					<%//out.print("<p>123</p>"); 
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
						out.print("无该分类！");
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