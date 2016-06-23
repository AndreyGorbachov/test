<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:header active="departments" label="Departments" />

<c:url value="/add_department.html" var="addUrl" />
<c:url value="/delete_department.html" var="delUrl" />

<div class="container">
	<c:if test="${error != null}">
		<div class="alert alert-danger"><c:out value="${error}"></c:out></div>
	</c:if>
	<div class="form-group">
		<button class="btn btn-primary" onclick="location.href='${addUrl}'">Add
			Department</button>
	</div>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th width="90%">Name</th>
				<th width="10%"></th>
			</tr>
			<c:forEach items="${departmentsList}" var="department">
				<tr>
					<td><a
						href="<c:url value="/edit_department.html?id=${department.id}"/>">${department.name}</a>
					</td>
					<td><a class="btn btn-danger"
						href="<c:url value="/delete_department.html?id=${department.id}"/>">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<u:footer />