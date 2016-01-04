<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Indicators</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
   	<script type="text/javascript" src="<c:url value='/resources/js/jquery.jqplot.js' />"></script>
  	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.cursor.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.dateAxisRenderer.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.logAxisRenderer.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.canvasTextRenderer.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.canvasAxisTickRenderer.js' />"></script>
 	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/js/jquery.jqplot.css' />" />
    <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
    
    <script type="text/javascript">
  $(document).ready(function(){
	  var cosPoints = []; 
	  for (var i=0; i<2*Math.PI; i+=0.1){ 
	     cosPoints.push([i, Math.cos(i)]); 
	  } 
	  var plot1 = $.jqplot('chartdiv', [cosPoints], {  
	      series:[{showMarker:false}],
	      axes:{
	        xaxis:{
	          label:'Angle (radians)'
	        },
	        yaxis:{
	          label:'Cosine'
	        }
	      }
	  });
	});
  </script>

</head>

<body>
<div class="container">
    <div class="row">
        <div class="span8 offset2">
        	<div class="page-header">
    			<h1>All constructed indicators</h1>
			</div>
            <c:if  test="${!empty analysisList}">
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Asset</th>
                        <th>Benchmark</th>
                        <th>First day</th>
                        <th>Last day</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${analysisList}" var="analysis">
                        <tr>
                            <td>${analysis.asset}</td>
                            <td>${analysis.bench}</td>
                            <td>${fn:substring(analysis.startDate, 0, 9)}</td>
                            <td>${fn:substring(analysis.startDate, 10, 17)}</td>
                            <td><button id="${loop.index}" class="btn btn-default">View data</button></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <div id="chartdiv"></div>
        </div>
    </div>
</div>

</body>
</html>
