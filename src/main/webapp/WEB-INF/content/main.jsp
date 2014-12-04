<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${ctx}/css/ticket.css"/>
<script type="text/javascript">var ctx = '${ctx}';</script>
</head>
<body>
	<div class="head container">
		<div class="input-wrapper">
			<div class="input-group">
				 <span class="input-group-addon">出发地</span>
				 <div class="select-wrapper">
					<input type="text" id="from"/>
					<ul class="dropdown-menu" role="menu">
		              <li><a href="#">Action</a></li>
		              <li><a href="#">Another action</a></li>
		              <li><a href="#">Something else here</a></li>
		              <li class="divider"></li>
		              <li><a href="#">Separated link</a></li>
		            </ul>
				 </div>
			</div>
		</div>
		<div class="input-wrapper">
			<div class="input-group">
				 <span class="input-group-addon">目的地</span>
				 <div class="select-wrapper">
					<input type="text" id="to"/>
				 </div>
			</div>
		</div>
		<div class="btn-wrapper">
			<a href="javascript:;" class="btn btn-default" id="query">查询</a>
		</div>
	</div>
	<div class="container"></div>
</body>
</html>