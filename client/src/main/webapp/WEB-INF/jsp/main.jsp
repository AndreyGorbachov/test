<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:header active="main" label="Welcome!" />

<div class="container">
	<c:if test="${error != null}">
		<div class="alert alert-danger">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<h3>Departments:</h3>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th width="80%">Name</th>
				<th width="20%">Average salary</th>
			</tr>
			<c:forEach items="${departmentsList}" var="department">
				<tr>
					<td><a
						href="<c:url value="/view_department.html?departmentId=${department.id}"/>">${department.name}</a>
					</td>
					<td>${department.salary}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<u:footer />