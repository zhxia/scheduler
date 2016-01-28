<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Job列表</title>
</head>
<body>
          <p><a class="btn btn-primary" href="<c:out value="${addUrl}"/>">新增Job</a></p>
          <div class="hero-unit">
          <table width="100%">
            <tr><th>Job名称</th><th>Job所在组</th><th>运行状态</th><th>操作</th></tr>
            <c:forEach items="${list}" var="row">
            <tr><td><c:out value="${row.jobName}"/></td><td align="center"><c:out value="${row.jobGroup}"/></td><td align="center"> -- </td><td align="center"><a href='edit.do?jobId=<c:out value="${row.id}" />'>编辑</a> <a href="op.do?jobId=<c:out value="${row.id}"/>&op=1">暂停</a></td></tr>
            </c:forEach>
            </table>
          </div>
        <!--
          <div class="row-fluid">
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div>
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div>
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div>
          </div>
           -->
</body>
</html>