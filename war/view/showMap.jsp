<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>My Route</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style type="text/css">
		html, body { height: 100%; margin: 0; padding: 0; }
		#map { height: 100%; }
		.labels {
    		color: white;
     		background-color: red;
     		font-family: "Lucida Grande", "Arial", sans-serif;
     		font-size: 10px;
     		text-align: center;
     		width: 100px;     
     		white-space: nowrap;
   		}
.modal {
    display:    none;
    position:   fixed;
    z-index:    1000;
    top:        0;
    left:       0;
    height:     100%;
    width:      100%;
    background: rgba( 255, 255, 255, .8 ) 
                url('http://i.stack.imgur.com/FhHRx.gif') 
                50% 50% 
                no-repeat;
}

body.loading {
    overflow: hidden;   
}

body.loading .modal {
    display: block;
}
	</style>
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCKK_MhrRijt2ZMHcJVBFI7ll3cqiWuzAw&callback=initMap">
	</script>
</head>
<body>
    <div id="map"></div>
    
    <script type="text/javascript">
    
    	$body = $("body");

		$(document).on({
    		ajaxStart: function() { $body.addClass("loading");    },
     		ajaxStop: function() { $body.removeClass("loading"); }    
		});

    	var userId = "${route.userId}";
    	var markerLat, markerLong, regDate, putDate, seqNo, locaId;
    	locaId = [
    		<c:forEach var="s" items="${route.trackLocations}" varStatus="status">
    		    "<c:out value="${s.locaId}"/>",
            </c:forEach>
    	];
    	
    	seqNo = [
    		<c:forEach var="s" items="${route.trackLocations}" varStatus="status">
                "<c:out value="${s.seqNo}"/>",
            </c:forEach>
    	];
    	putDate = [
    		<c:forEach var="s" items="${route.trackLocations}" varStatus="status">
                "<c:out value="${s.putAton}"/>",
            </c:forEach>
    	];
    	
    	<c:set var="timeZone" value="GMT+9"/>
    	
    	regDate = [
    		<c:forEach var="s" items="${route.trackLocations}" varStatus="status">
                "<fmt:formatDate type="both" value="${s.regAton}" timeZone="${timeZone}"/>",
            </c:forEach>
    	];
        markerLat = [
            <c:forEach var="s" items="${route.trackLocations}" varStatus="status">
                [<c:out value="${s.geoPt.latitude}"/>],
            </c:forEach>];
        markerLong = [
          <c:forEach var="s" items="${route.trackLocations}" varStatus="status">
              [<c:out value="${s.geoPt.longitude}"/>],
          </c:forEach>];
           
		var map;

		function initMap() {
			map = new google.maps.Map(document.getElementById('map'), {
				center: {lat:${route.trackLocations[route.count-1].geoPt.latitude}, lng: ${route.trackLocations[route.count-1].geoPt.longitude}},
				disableDoubleClickZoom: true,
				zoom: 15
	  		});
			google.maps.event.addListener(map, 'click', function() {
        		infowindow.close();
        	});

			var flightPlanCoordinates = [
			 	<c:forEach var="s" items="${route.trackLocations}" varStatus="status">
                	{ lat : <c:out value="${s.geoPt.latitude}"/> , 
                	  lng : <c:out value="${s.geoPt.longitude}"/> } ,
            	</c:forEach>
  			];
  			
  			var flightPath = new google.maps.Polyline({
				path: flightPlanCoordinates,
    			geodesic: true,
    			strokeColor: '#FF0000',
    			strokeOpacity: 1.0,
    			strokeWeight: 2
  			});
  				  	
	  		flightPath.setMap(map);
	  		
	  		for (i = 0; i < markerLat.length; i++) {
	  			var point = new google.maps.LatLng(markerLat[i], markerLong[i]);
	  			var content = regDate[i] + '<br>' + seqNo[i] +'<br>' + locaId[i] + '<br>'
	  									+ '<a href="javascript:void(0)" onclick="deleteMarker(\''+locaId[i] + '\');">지우기</a>';
//	  			  							+ '<a href=/adjLocation?flag=del&locaId=' + locaId[i] + '>지우기</a>';  
	  			var marker = createMarker(point, content);
        	}
	        var infowindow = new google.maps.InfoWindow(
	          { 
	            size: new google.maps.Size(150,50),
	        });
	    
	        function createMarker(latlng, contentString) {
	          var marker = new google.maps.Marker({
	            position: latlng,
	            map: map,
	            zIndex: Math.round(latlng.lat()*-100000)<<5
	            });
	
	            google.maps.event.addListener(marker, 'click', function() {
	              infowindow.setContent(contentString); 
	              infowindow.open(map,marker);
	            });
	        }
	        google.maps.event.addListener(map, "dblclick", function(e) {
				var latLng = e.latLng;
				addMarker(latLng);
			});
		}
		
		function addMarker(latLng){
			var url = 'uSubmit?seqNo=0&userId='+userId+'&latLng='+latLng.toUrlValue();
    		if (confirm("Add marker here?") == true) {
				$.get(url, function(data, status) {
					if(data.retCode == '0') {
						alert(data.desc);
						location.reload();
					} else {
						alert(data.desc);
					}
			});
    		} else {
        		alert("Cancelled");
    		}
		}

		function deleteMarker(locaId) {
			var url = 'adjLocation?flag=del&locaId='+locaId;
			$.get(url, function(data, status) {
				if(data.retCode == '0') {
					alert(data.desc);
					location.reload();
				} else {
					alert(data.desc);
				}
			});
		}
		
	
	
    </script>
<div class="modal"><!-- Place at bottom of page --></div>
</body>
</html>