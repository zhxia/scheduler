<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Job列表</title>
</head>
<body>
	<p>
		<a class="btn btn-primary" href="<c:out value="${addUrl}"/>">新增Job</a>
	</p>
	<div class="hero-unit">
		<table width="100%">
			<tr>
				<th>Job名称</th>
				<th>Job所在组</th>
				<th>运行状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${list}" var="row">
				<tr>
					<td><c:out value="${row.jobName}" /></td>
					<td align="center"><c:out value="${row.jobGroup}" /></td>
					<td align="center"><c:if test="${row.jobStatus==0}">待运行</c:if>
						<c:if test="${row.jobStatus==1}">运行中</c:if> <c:if
							test="${row.jobStatus==2}">已停止</c:if></td>
					<td align="center"><a
						href='edit.do?jobId=<c:out value="${row.id}" />'>编辑</a> <a
						href="op.do?jobId=<c:out value="${row.id}"/>&op=1">启动</a> <a
						href="op.do?jobId=<c:out value="${row.id}"/>&op=2">停止</a> <a
						href="op.do?jobId=<c:out value="${row.id}"/>&op=3">重启</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>