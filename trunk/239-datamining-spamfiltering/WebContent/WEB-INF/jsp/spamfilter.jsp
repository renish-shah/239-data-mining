<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to SPAM FILTER</title>
</head>
<body>
	<table>
		<tr>
			<td><a href="svm.html">SVM SPAM</a></td>
			<td><a href="svm">SVM Non-SPAM</a></td>
			<td><a href="svm">SVM SPAM & NO SPAM</a></td>
		</tr>
		<tr>
			<td><a href="knn.html">KNN</a></td>
		</tr>
		<tr>
			<td>  </td>
		</tr>
		<c:forEach items="${message}" var="element" varStatus="count">
			<tr>
				<td>${element}</td>
			</tr>
		</c:forEach>


		<tr>
			<td></td>
		</tr>
	</table>
</body>
</html>