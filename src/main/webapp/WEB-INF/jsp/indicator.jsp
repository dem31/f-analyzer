<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Indicators</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/js/jquery.jqplot.css' />" />
    <script type="text/javascript" src="<c:url value='/resources/js/jquery.min.js' />"></script>
   	<script type="text/javascript" src="<c:url value='/resources/js/jquery.jqplot.js' />"></script>
  	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.cursor.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.dateAxisRenderer.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.logAxisRenderer.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.canvasTextRenderer.js' />"></script>
 	<script type="text/javascript" src="<c:url value='/resources/js/plugins/jqplot.canvasAxisTickRenderer.js' />"></script>
    
    <script type="text/javascript">
  	</script>

</head>

<body>
<div class="container">
    <div class="row">
        <div class="span8 offset2">
        	<div class="page-header">
    			<h1>All constructed indicators</h1>
			</div>
            <%--@elvariable id="analysisList" type="java.util.List"--%>
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
                            <td>${fn:substring(analysis.startDate, 0, 10)}</td>
                            <td>${fn:substring(analysis.startDate, 10, 20)}</td>
                            <td>
                            	<form method="post" action="indicator/show">
              						<input type="hidden" name="id" value="${analysis.analysisId}">
            						<button type="submit" class="btn btn-default">Submit</button>
          						</form>
							</td>
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
