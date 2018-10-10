<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<script type="text/javascript">
	$(function() {
		$("a").each(function() {
			this.onclick = function(){
				var serializeVal = $(":hidden").serialize();
				//alert(serializeVal);
				var href = this.href + "&" + serializeVal;
				window.location.href = href;
				return false;
			}
		});
	});
</script>

<input type="hidden" name="minPrice" value="${param.minPrice}" />
	<input type="hidden" name="maxPrice" value="${param.maxPrice}" />
