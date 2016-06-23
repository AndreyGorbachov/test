<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="active" type="java.lang.String"%>
<%@attribute name="label" type="java.lang.String"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<link href="resources/css/bootstrap.css" rel="stylesheet" />
<link href="resources/css/bootstrap-datepicker.css" rel="stylesheet" />

<script src="resources/js/jquery-1.12.4.js"></script>
<script src="resources/js/bootstrap.js"></script>
<script src="resources/js/bootstrap-datepicker.js"></script>

<script>
	$(document).ready(function() {
		switch ('${active}') {
		case 'main':
			$('#main').addClass('active')
			break
		case 'departments':
			$('#dep').addClass('active')
			break
		case 'employees':
			$('#emp').addClass('active')
			break
		}
	});
</script>

<title>${label}</title>

<div class="container">
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<div class="navbar-brand">REST CLIENT</div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li id="main"><a href="<c:url value="/main.html"/>">Home</a></li>
				<li id="dep"><a href="<c:url value="/departments.html"/>">Departments</a></li>
				<li id="emp"><a href="<c:url value="/employees.html"/>">Employees</a></li>
			</ul>
		</div>
	</nav>
	<div class="page-header">
		<h1>${label}</h1>
	</div>
</div>
