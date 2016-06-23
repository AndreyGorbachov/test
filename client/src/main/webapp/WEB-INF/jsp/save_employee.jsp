<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>

<c:choose>
	<c:when test="${employeeForm.id == null}">
		<u:header active="employees" label="Add Employee" />
	</c:when>
	<c:otherwise>
		<u:header active="employees" label="Edit Employee" />
	</c:otherwise>
</c:choose>

<c:url value="/save_employee.html" var="saveUrl" />

<div class="container">
	<c:if test="${error != null}">
		<div class="alert alert-danger">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<form:form modelAttribute="employeeForm" action="${saveUrl}">
		<form:hidden path="id" />
		<div class="form-group">
			<label class="label-control">Name:</label>
			<form:input class="form-control" path="name" />
			<form:errors path="name" cssClass="text-danger" />
		</div>
		<div class="form-group">
			<label class="label-control">Date of Birth:</label>
			<form:input class="form-control" path="dob" />
			<form:errors path="dob" cssClass="text-danger" />
		</div>
		<div class="form-group">
			<label class="label-control">Salary:</label>
			<form:input class="form-control" path="salary" />
			<form:errors path="salary" cssClass="text-danger" />
		</div>
		<div class="form-group">
			<label class="label-control">Department:</label>
			<form:select class="form-control" path="departmentId">
				<form:option value="" />
				<form:options items="${departmentsList}" itemValue="id"
					itemLabel="name" />
			</form:select>
			<form:errors path="departmentId" cssClass="text-danger" />
		</div>
		<div class="form-group pull-right">
			<form:button class="btn btn-primary">Save</form:button>
			<a class="btn btn-danger" href="<c:url value="/employees.html" />">Cancel</a>
		</div>
	</form:form>
</div>
<u:footer />