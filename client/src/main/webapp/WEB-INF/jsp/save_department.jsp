<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u"%>

<c:url value="/save_department.html" var="saveUrl" />

<c:choose>
	<c:when test="${departmentForm.id == null}">
		<u:header active="departments" label="Add Department" />
	</c:when>
	<c:otherwise>
		<u:header active="departments" label="Edit Department" />
	</c:otherwise>
</c:choose>

<div class="container">
	<c:if test="${error != null}">
		<div class="alert alert-danger">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<form:form modelAttribute="departmentForm" action="${saveUrl}">
		<form:hidden path="id" />
		<div class="form-group">
			<label class="label-control">Name:</label>
			<form:input class="form-control" path="name" />
			<form:errors path="name" cssClass="text-danger" />
		</div>
		<div class="form-group pull-right">
			<form:button class="btn btn-primary">Save</form:button>
			<a class="btn btn-danger" href="<c:url value="/departments.html"/>">Cancel</a>
		</div>
	</form:form>
</div>
<u:footer />