<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ include file="/commons/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#pageNo").change(
				function() {
					var val = $(this).val();
					val = $.trim(val);
					//1. check va1 is digit 1,2 not a12,b
					var reg = /^\d+$/g;
					if (!reg.test(val)) {
						alert("not legit pageNo");
						$(this).val("");
						return;
					}
					//2. check va1 is in the legit range (1 to totalPageNumber)
					var pageNo = parseInt(val);
					if (val<1||val>parseInt("${bookpage.totalPageNumber}")) {
						alert("not in the legit range");
						$(this).val("");
						return;
					}
					//3. page jumping
					var href = "bookServlet?method=getBooks&pageNo=" + pageNo
							+ "&" + $(":hidden").serialize();
					window.location.href = href;
				});
	})
</script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>

	<center>
	<c:if test="${param.title !=null}">
	   You have put ${param.title} into shopping cart.
	   <br><br>
	</c:if>
 <%--  <c:if test="${!empty sessionScope.ShoppingCart.books}"  --%> 
 
	<c:if test="${!empty sessionScope.ShoppingCart.books}">
	
	<c:if test="${sessionScope.ShoppingCart.bookNumber>1}">
	There are ${sessionScope.ShoppingCart.bookNumber} books in your shopping cart.
	</c:if>
	
	<c:if test="${sessionScope.ShoppingCart.bookNumber==1}">
	There is ${sessionScope.ShoppingCart.bookNumber} book in your shopping cart.
	</c:if>
	<br><br>
	  <a href = "bookServlet?method=forwardPage&page=cart&pageNo=${bookpage.pageNo}">check shopping cart</a>
	</c:if>
	
		<br><br>
		<form action="bookServlet?method=getBooks" method="post">
			Price: <input type="text" size="3" name="minPrice" /> 
			<input type="text" size="3" name="maxPrice" /> 
			<input type="submit" value="submit" />
		</form>

		<br><br>
		<table cellpadding="10">
			<c:forEach items="${bookpage.list}" var="book">
				<tr>
					<td><a
						href="bookServlet?method=getBook&pageNo=${bookpage.pageNo }&id=${book.id}">${book.title}</a>
						<br> ${book.author }</td>
					<td>${book.price}</td>
					<td><a href="bookServlet?method=addToCart&pageNo=${bookpage.pageNo }&id=${book.id}&title=${book.title}">add into shopping cart</a></td>
				</tr>
			</c:forEach>
		</table>
		<br><br> total page number: ${bookpage.totalPageNumber }
		&nbsp; &nbsp; current page number: ${bookpage.pageNo } &nbsp; &nbsp;
		<c:if test="${bookpage.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">First Page</a>
                  &nbsp;&nbsp;
                  <a
				href="bookServlet?method=getBooks&pageNo=${bookpage.prevPage }">Previous
				Page</a>
		</c:if>
		&nbsp; &nbsp;
		<c:if test="${bookpage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage }">Next
				Page</a>
                  &nbsp;&nbsp;
                  <a
				href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }">Last
				Page</a>
		</c:if>
		&nbsp; &nbsp; jump to page<input type="text" size="1" id="pageNo" />
	</center>
</body>
</html>