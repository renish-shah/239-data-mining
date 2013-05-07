<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- /**
 * @author RENISH
 * 
 */ -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to SPAM FILTER</title>
</head>
<body>
	<table>

		<tr>
			<td><a href="yahooSS.html">Yahoo SPAM > SPAM</a></td>
		</tr>
		<tr>
			<td><a href="svmSS.html">SVM Train> SPAM & Test> SPAM</a></td>
			<td><a href="svmSN.html">SVM Train> SPAM & Test> NonSPAM</a></td>
		</tr>
		<tr>
			<td><a href="svmNN.html">SVM Train> NonSPAM & Test> NonSPAM</a></td>
			<td><a href="svmNS.html">SVM Train> NonSPAM & Test> SPAM</a></td>
		</tr>
		<tr>
			<td><a href="knnSpam.html">KNN SPAM</a></td>
			<td><a href="knnNonSpam.html">KNN NonSPAM</a></td>
		</tr>
		<tr>
			<td><a href="naiveTestSpam.html">Naive Bayesian Test SPAM</a></td>
		</tr>
		<tr>
			<td><a href="naiveTestNonSpam.html">Naive Bayesian Test
					NON-SPAM</a></td>
		</tr>
		<tr>
			<td></td>
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