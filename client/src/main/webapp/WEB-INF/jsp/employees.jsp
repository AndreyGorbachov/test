<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:header active="employees" label="Employees" />

<c:url value="/add_employee.html" var="addUrl" />

<div class="container">
	<c:if test="${error != null}">
		<div class="alert alert-danger">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<div class="form-group">
		<button class="btn btn-primary" onclick="location.href='${addUrl}'">Add
			Employee</button>
	</div>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th width="40%">Name</th>
				<th width="10%">Date of Birth</th>
				<th width="10%">Salary</th>
				<th width="30%">Department</th>
				<th width="10%"></th>
			</tr>
			<c:forEach items="${employeesList}" var="employee">
				<tr>
					<td><a
						href="<c:url value="/edit_employee.html?id=${employee.id}"/>">${employee.name}</a>
					</td>
					<td><fmt:formatDate value="${employee.dob}" type="date"
							pattern="dd.MM.yyyy" /></td>
					<td>${employee.salary}</td>
					<td>${employee.departmentName}</td>
					<td>
						<button class="btn btn-danger"
							onclick="location.href='<c:url value="/delete_employee.html?id=${employee.id}"/>'">Delete</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<u:footer />