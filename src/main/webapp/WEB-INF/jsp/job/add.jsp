<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增JOB</title>
</head>
<body>
<form action="" method="post">
    <table width="800" cellpadding=5>
    <tr><td align="right" width="100">job名称：</td><td><input name="jobName" type="text" /></td></tr>
    <tr><td align="right">所在组：</td><td><input name="jobGroup" type="text" /></td></tr>
     <tr><td align="right">描述：</td><td><textarea name="description"></textarea></td></tr>
    <tr><td align="right">脚本类型：</td><td>
    <select name="jobCategory">
    <option value="1">JAVA</option>
    <option value="2">PHP</option>
    <option value="3">SHELL</option>
    </select>
    </td></tr>
    <tr><td align="right">执行命令：</td><td><input class="input-block-level" name="command" type="text"/></td></tr>
    <tr><td align="right">时间表达式：</td><td><input name="cronExpression" type="text"/></td></tr>
    <tr><td align="right">优先级：</td><td>
    <select name="priority">
        <option value="10" >高</option>
        <option value="5" selected="">中</option>
        <option value="1">低</option>
    </select></td></tr>
    <tr><td align="right">相关负责人：</td><td><select multiple><option value="1">zhxia</option></select></td></tr>
    <tr><td>&nbsp;</td><td><input class="btn btn-primary" type="submit" value="保存"/>&nbsp;&nbsp; <input class="btn" type="button" onclick="javascript:history.back(-1);" value="返回"/></td></tr>
    </table>
</form>
</body>
</html>