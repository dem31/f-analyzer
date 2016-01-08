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

  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <link href="//netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">

  <script type="text/javascript">
  $('#benchmarkIDSelect').change(function () {
	    var selected = $(this).find("option:selected").val();
	    $("#benchmarkID").val(selected);
	    $.ajax({
	        url:"index_assets/"+selected,  
	        success:function(data) {
	           alert(data); 
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
                <h1>Construct an analysis</h1>
            </div>
            <form method="post" action="indicator/add" class="form-vertical">
            <div class="form-group">
            	<label for="benchmarkIDSelect">Asset</label>
            	<div class="col-xs-4">
				    <input type="hidden" id="benchmarkID" name="bench"  />
				    <select id="benchmarkIDSelect">
				    	<option value="">-</option>
				    	<c:forEach var="index" items="${indexes}">
						<option value=<c:out value='"${index.key}"' escapeXml="false"/>><c:out value="${index.value}"/></option>
                  		</c:forEach>
				    </select>
			    </div>
            </div>
            <div class="form-group">
            	<label for="assetIDSelect">Benchmark</label>
            	<div class="col-xs-4">
              		<input type="hidden" id="assetID" name="asset"  />
					<select id="assetIDSelect">
						<option value="">-</option>
					</select>
              	</div>
            </div>
            <div class="form-group">
            	<label for="date">Start Date</label>
            	<div class="col-xs-4">
            		<input type="date" class="form-control" id="date" name="date">
            	</div>
          	</div>
            <div class="form-group">
            	<button type="submit" class="btn btn-default">Submit</button>
            </div>
          </form>
		</div>
	</div>
</div>


<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/2.3.2/js/bootstrap.min.js"></script>
</body>
</html>
