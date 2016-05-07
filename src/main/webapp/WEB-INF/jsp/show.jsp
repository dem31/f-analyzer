<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

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
    var price = [
                  <c:forEach var="path" items="${analysis.pricePath}" varStatus="status">
                  	<joda:format value="${path.date}" pattern="MM/dd/yyyy" var="date"/>
                    [ <c:out value='"${date}"' escapeXml="false"/>, <c:out value="${path.price}"/>]
                    <c:if test="${not status.last}">,</c:if>
                  </c:forEach>
    ];
    var priceBench = [
                  <c:forEach var="path" items="${analysis.pricePath}" varStatus="status">
                  	<joda:format value="${path.date}" pattern="MM/dd/yyyy" var="date"/>
                    [ <c:out value='"${date}"' escapeXml="false"/>, <c:out value="${path.priceBench}"/>]
                    <c:if test="${not status.last}">,</c:if>
                  </c:forEach>
    ];
    
    $(document).ready(function(){
  	var plot2 = $.jqplot('chartdiv', [price, priceBench], {
            title: 'Asset and benchmark price paths',
            axes: {
                xaxis: {
                    renderer: $.jqplot.DateAxisRenderer,
                    tickRenderer: $.jqplot.CanvasAxisTickRenderer, 
                    tickOptions: {
                      angle: -30
                    },
                    yaxis: {  
                        tickOptions:{ prefix: '$' } 
                    }
                }
            },
            legend: { show: true },
            series: [
                    { label: "${analysis.asset}" },
                    { label: "${analysis.bench}" }
            ],
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
    			<h1>"${analysis.asset}" Analysis </h1>
			</div>
            <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Period</th>
                    	<th>Performance</th>
                    	<th>Volume</th>
                        <th>Alpha</th>
                        <th>Beta</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${analysis.indicators}" var="a">
                        <tr>
                            <c:set var="months" value="${((a.period)/13)*3}"/>
                            <td><fmt:formatNumber value="${months}" maxFractionDigits="0"/> months</td>
                        	<td><fmt:formatNumber value="${a.perf}" maxFractionDigits="3"/></td>
                            <td><fmt:formatNumber value="${a.vol}" maxFractionDigits="3"/></td>
                            <td><fmt:formatNumber value="${a.beta}" maxFractionDigits="3"/></td>
                            <td><fmt:formatNumber value="${a.alpha}" maxFractionDigits="3"/></td>
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
