<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.chaoo.trackloca.TrackRoute" %>
<%@ page import="com.chaoo.trackloca.TrackLocation" %>
<%@ page import="com.google.appengine.api.datastore.GeoPt" %>
<%@ page import="java.util.List" %>

<%@ page import="javax.servlet.http.HttpServletRequest" %>


<!DOCTYPE html>
<html>
  <head>
    <style type="text/css">
      html, body { height: 100%; margin: 0; padding: 0; }
      #map { height: 100%; }
    </style>
  </head>
  <body>
    <div id="map"></div>
    <script type="text/javascript">

	var map;
	function initMap() {
	
		var myLatLng = {lat: -25.363, lng: 131.044};
		
		map = new google.maps.Map(document.getElementById('map'), {
			center: {lat: -25.363, lng: 131.044},
			zoom: 8
	  	});
	  	
	  	var marker = new google.maps.Marker({
    		map: map,
    		position: myLatLng,
    		title: 'Hello World!'
  		});
	}

    </script>
    <script async defer
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCKK_MhrRijt2ZMHcJVBFI7ll3cqiWuzAw&callback=initMap">
    </script>
  </body>
</html>