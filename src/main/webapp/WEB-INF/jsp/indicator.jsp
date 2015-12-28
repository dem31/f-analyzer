<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Spring MVC and Hibernate Template</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container">
    <div class="row">
        <div class="span8 offset2">
            <div class="page-header">
                <h1>Simple CRUD Page</h1>
            </div>
            
            <form method="post" action="indicator/add" class="form-vertical">
            	<input type="text" id="Asset ID" name="asset" value=""/>
            	<input type="text" id="Benchmark ID" name="bench" value=""/>
	            <input type="submit" class="btn"/>
            </form>
            
            <c:if  test="${!empty link}">
                <h3>People</h3>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${link}</td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
