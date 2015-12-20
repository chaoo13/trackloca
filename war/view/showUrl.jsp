<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>Url List</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
  <h2>Url</h2>
  <table class="table">
    <thead>
      <tr>
        <th>Type</th>
        <th>Country Code</th>
        <th>Url</th>
      </tr>
    </thead>
    <tbody>
    	<c:forEach var="url" items="${urls}" varStatus="status">
    	<tr>
        	<td><c:out value="${url.requestType}"/></td>
        	<td><c:out value="${url.countryCode}"/></td>
        	<td><c:out value="${url.url}"/></td>
      	</tr>
		</c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>