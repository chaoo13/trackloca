<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Web submit page</title>

<script type="text/javascript">

	var x = document.getElementById("demo");
	function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}

function showPosition(position) {
    x.innerHTML = "Latitude: " + position.coords.latitude + 
    "<br>Longitude: " + position.coords.longitude; 
}

    </script>
    
</head>
<body>
<form>
	User Id: <br>
	<input type="text" name="userId" id="userId">
	<input type="submit">
</from>
</body>
</html>