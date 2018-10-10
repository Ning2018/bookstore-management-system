<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>
<center>
<br><br>
Title: ${book.title }
<br><br>
Author: ${book.author }
<br><br>
Price: ${book.price }
<br><br>
SalesAmount: ${book.salesAmount }
<br><br>

<a href="bookServlet?method=getBooks&pageNo=${param.pageNo }"> Go on shopping</a>
</center>
</body>
</html>