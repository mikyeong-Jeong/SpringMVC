<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>QRcode</title>
</head>
<body>
	<h1>QR Code JSP Page</h1>
	<hr>
	<br/>
	
	<form action="/project001/qr/craete" method="get">
		<input type="hidden" name="qrImg" value=""  />
		<button type="submit">Generate QR code</button>
	</form>
	
</body>
</html>