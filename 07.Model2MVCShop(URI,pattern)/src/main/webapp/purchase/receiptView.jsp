<%@ page contentType="text/html; charset=EUC-KR" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>구매 영수증</title>
</head>

<script type="text/javascript">
<!--
function fncGoListPurchase() {
	document.viewPurchase.submit();
}
-->
</script>

<body>

<form name="viewPurchase" action="/purchase/listPurchase" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${ purchase.purchaseProd.prodNo }</td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${ purchase.buyer.userId }</td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
			<c:if test="${ purchase.paymentOption == '001' }">
				현금구매
			</c:if>
			<c:if test="${ purchase.paymentOption == '002' }">
				신용구매
			</c:if>		
		</td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${ purchase.receiverName }</td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${ purchase.receiverPhone }</td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${ purchase.dlvyAddr }</td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${ purchase.dlvyRequest }</td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${ purchase.dlvyDate }</td>
	</tr>	
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="center">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
						<a href="javascript:fncGoListPurchase();">목록</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					<td width="30"></td>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
						<a href="javascript:history.go(-1)">취소</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>

</body>
</html>