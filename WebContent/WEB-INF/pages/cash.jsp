<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<br><br>
You have bought ${sessionScope.ShoppingCart.bookNumber} books.
<br>
Total payment: RMB ${sessionScope.ShoppingCart.totalMoney}
<br><br>

<c:if test="${requestScope.errors!=null}">
<font color = "red">
${requestScope.errors }
</font>
</c:if>

<form action = "bookServlet?method=cash" method="post">
<table cellpadding="10">
<tr>
<td>User Name: </td>
<td><input type = "text" name="username" /></td>
</tr>
<tr>
<td>Account Id: </td>
<td><input type = "text" name="accountId" /></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="Submit" /></td>
</tr>
</table>

</form>
</center>

</body>
</html>