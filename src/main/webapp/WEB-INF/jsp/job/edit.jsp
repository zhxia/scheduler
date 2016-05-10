<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑脚本</title>
</head>
<body>
<form action="" method="post">
    <table class="table table-striped table-bordered" width="800" cellpadding="5">
    <tr><td align="right" width="100">job名称:</td><td><input name="jobName" type="text" readonly="readonly" value="<c:out value="${job.jobName}"/>" /></td></tr>
    <tr><td align="right">所在组:</td><td><input name="jobGroup" type="text" readonly="true" value="<c:out value="${job.jobGroup}"/>" /></td></tr>
    <tr><td align="right">优先级:</td><td>
    <select name="priority">
        <option value="10" <c:if test="${job.priority == '10'}"> <c:out value="selected="></c:out> </c:if> >高</option>
        <option value="5" <c:if test="${job.priority == '5'}"> <c:out value="selected="></c:out> </c:if> >中</option>
        <option value="1" <c:if test="${job.priority == '1'}"> <c:out value="selected="></c:out> </c:if>>低</option>
    </select></td></tr>
    <tr><td align="right">描述:</td><td><textarea name="description"><c:out value="${job.description}"/></textarea></td></tr>
    <tr><td align="right">脚本类型：</td><td><select name="jobCategory">
    <option value="1" <c:if test="${job.jobCategory == '1'}"> <c:out value="selected="></c:out> </c:if> >JAVA</option>
    <option value="2" <c:if test="${job.jobCategory == '2'}"> <c:out value="selected="></c:out> </c:if> >PHP</option>
    <option value="3" <c:if test="${job.jobCategory == '3'}"> <c:out value="selected="></c:out> </c:if> >SHELL</option>
    </select></td></tr>
     <tr><td align="right">执行命令:</td><td><input name="command" type="text" value="<c:out value="${job.command}"/>"/></td></tr>
    <tr><td align="right">时间表达式:</td><td><input name="cronExpression" type="text" value="<c:out value="${job.cronExpression}"/>" /></td></tr>
    <tr><td>&nbsp;</td><td><input class="btn btn-primary" type="submit" value="保存"/> &nbsp;&nbsp; <input class="btn" type="button" onclick="javascript:history.back(-1);" value="返回"/></td></tr>
    </table>
</form>
</body>
</html>