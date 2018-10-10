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
$(function(){
	$(".delete").click(function(){
		var $tr=$(this).parent().parent();
		var title=$.trim($tr.find("td:first").text());
		var flag=confirm("Are you sure to delete   "+title+" ?");
		if(flag){
			return true;
		}
		return false;
	});
	// ajax adjust the quantity
	$(":text").change(function(){
		
		var quantityVal = $.trim(this.value);
		var flag = false;
		var reg=/^\d+$/g;
		if(reg.test(quantityVal)){
				var quantity = parseInt(quantityVal);
				if(quantity >=0){
					flag = true;
				}
		     }
		if(!flag){
		   alert("Not legit quantity!");
		   $(this).val($(this).attr("class"));
		   return;
		}
		
		var $tr=$(this).parent().parent();
		var title=$.trim($tr.find("td:first").text());
		
		if(quantity==0){
			var flag2=confirm("Are you sure to delete   "+title+" ?");
			if(flag2){
				var $a = $tr.find("td:last").find("a");
				//alert($a[0].onclick);
				$a[0].onclick();
				
				window.location.href=href;
				return;
			}
		}
		
		var flag=confirm("Are you sure to adjust the quantity of "+title+"?");
		
		if(!flag){
			   $(this).val($(this).attr("class"));
			   return;
			}
		
		var url = "bookServlet";
		var idVal = $.trim(this.name);
		
		var args = {"method":"updateItemQuantity","id":idVal,"quantity":quantityVal,"time":new Date()};
		
		$.post(url,args,function(data){
			var bookNumber = data.bookNumber;
			var totalMoney = data.totalMoney;
			
			$("#totalMoney").text("Total Amount: RMB " + totalMoney);
			$("#bookNumber").text("There are "+bookNumber +" books in your shopping cart.");

		},"JSON");
	});
	
});
</script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>
<center>
<br>
<div id = "bookNumber">
There are ${sessionScope.ShoppingCart.bookNumber } books in your shopping cart.
</div>

<br><br>
<table>
<tr>
<td>Title</td>
<td>Price</td>
<td>Quantity</td>
<td>&nbsp;</td>
</tr>
    <c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
    <tr>
    <td>${item.book.title }&nbsp; &nbsp;</td>
    <td>${item.book.price }&nbsp; &nbsp;</td>
    <td><input class = "${item.quantity }" type="text" size="1" name="${item.book.id }" value="${item.quantity }" /> &nbsp;&nbsp;</td>
    <td><a href="bookServlet?method=remove&pageNo=${param.pageNo}&id=${item.book.id}" class="delete">Delete</a>&nbsp; &nbsp;</td>
    </tr>
    </c:forEach>
    
    <tr>
    
    <td colspan="4" id="totalMoney"> <br> Total Amount: RMB ${sessionScope.ShoppingCart.getTotalMoney()}</td>
    </tr>
   
    <tr>
    <td colspan="4"> 
     <br>
      <a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">Go on Shopping</a>
      <br>
       <a href="bookServlet?method=clear">Clear Shopping Cart</a>
      <br>
       <a href="bookServlet?method=forwardPage&page=cash">Check out</a>
      <br>

    </td>
    </tr>
</table>
</center>
</body>
</html>