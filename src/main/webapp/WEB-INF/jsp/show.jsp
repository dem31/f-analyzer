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
    var arr = new Array();  
    <%-- <c:forEach items="${CustomFieldDetails}" var="current" varStatus="status">
        arr[i] = '<c:out value='${analysisList[0].shortName}'/>';     
        i++;
     </c:forEach> --%>
    
    $(document).ready(function(){ 
    	var line1 = [9.0,  13.0, 14.0, 16.0, 17.0, 19.0];
    	var line2 = [15.0, 17.0, 16.0, 18.0, 13.0, 11.0];
    	var ticks = ["4/13/2009","4/15/2009","4/17/2009","4/19/2009","4/21/2009","4/23/2009"];
    	
        plot2 = $.jqplot('chartdiv', [line1, line2], { 
            title: 'Asset', 
            seriesDefaults: {
                rendererOptions: {
                    smooth: true
                }
            },
            series: [
                {yaxis:'yaxis', label:'dataForAxis1'},
            	{yaxis:'yaxis', label:'dataForAxis2'} 
            ], 
            axes: { 
                xaxis: { 
                	ticks: ticks,
                    renderer:$.jqplot.DateAxisRenderer,
                    tickRenderer: $.jqplot.CanvasAxisTickRenderer, 
                    tickOptions: {
                      angle: -30
                    } 
                }, 
                yaxis: {  
                    tickOptions:{ prefix: '$' } 
                } 
            }, 
            cursor:{
                show: true, 
                zoom: true
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
    			<h1>All constructed indicators </h1>${analysis.dates[0]}
			</div>
            <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>a</th>
                        <th>b</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${analysis.indicators}" var="a">
                        <tr>
                            <td>${a.beta}</td>
                            <td>${a.alpha}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            <div id="chartdiv"></div>
        </div>
    </div>
</div>

</body>
</html>
