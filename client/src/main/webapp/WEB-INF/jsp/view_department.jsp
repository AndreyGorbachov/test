<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:header active="main" label="${department.name}" />

<script>
	$(document).ready(function() {

		$('input[name*="Date"]').datepicker({
			format : 'dd.mm.yyyy'
		});

		var check = $('#check');
		if ($('#period').val() == 'true') {
			check.attr('checked', true);
		}

		if (check.is(':checked')) {
			$('#endDate').show();
		} else {
			$('#endDate').hide();
		}

		check.on('change', function() {
			if (check.is(':checked')) {
				$('#endDate').show();
				$('#period').val('true');
			} else {
				$('#endDate').hide();
				$('#endDate').val('');
				$('#period').val('false');
			}
		});
	});
</script>

<div class="container">
	<c:if test="${error != null}">
		<div class="alert alert-danger">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<h3>Employees:</h3>
	<label>Period:<input type="checkbox" id="check" name="check" /></label>
	<form:form class="form-inline" modelAttribute="searchForm" method="GET">
		<form:hidden path="departmentId" value="${department.id}" />
		<form:hidden id="period" path="period" />
		<div class="form-group">
			<form:input class="form-control" path="startDate"
				placeholder="DD.MM.YYYY" />
		</div>
		<div class="form-group">
			<form:input class="form-control" id="endDate" path="endDate"
				placeholder="DD.MM.YYYY" />
		</div>
		<form:button class="btn btn-info">Search</form:button>
	</form:form>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th width="80%">Name</th>
				<th width="10%">Date of Birth</th>
				<th width="10%">Salary</th>
			</tr>
			<c:forEach items="${employeesList}" var="employee">
				<tr>
					<td><a
						href="<c:url value="/edit_employee.html?id=${employee.id}"/>">${employee.name}</a>
					</td>
					<td><fmt:formatDate value="${employee.dob}" type="date"
							pattern="dd.MM.yyyy" /></td>
					<td>${employee.salary}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<u:footer />