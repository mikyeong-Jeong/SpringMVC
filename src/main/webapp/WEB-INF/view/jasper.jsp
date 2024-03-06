<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>jasper</title>
</head>
<body>
	<h1>Jasper Report JSP Page</h1>
	<hr>
	<br/>
	
	<form action="/project001/jasper/download" method="get">
		<input type="hidden" name="type" value="excel"  />
		<button type="submit">Excel Type</button>
	</form>
	<br>
	<form action="/project001/jasper/download" method="get">
		<input type="hidden" name="type" value="pdf"  />
		<button type="submit">Pdf Type</button>
	</form>
	<br>
	<form action="/project001/jasper/download" method="get">
		<input type="hidden" name="type" value="html"  />
		<button type="submit">Html Type</button>
	</form>
	<br>
	
</body>
</html>