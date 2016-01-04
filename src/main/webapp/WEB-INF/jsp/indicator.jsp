<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Analysis</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>
<div class="page-header">
    <h1>All constructed indicators</h1>
</div>
<div class="container">
    <div class="row">
        <div class="span8 offset2">
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
                            <td>${analysis.startDate}</td>
                            <td>${analysis.startDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
