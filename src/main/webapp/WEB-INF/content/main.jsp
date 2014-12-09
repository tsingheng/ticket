<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>春运余票查询</title>
<link rel="stylesheet" href="${ctx}/css/ticket.css"/>
<script type="text/javascript">var ctx = '${ctx}';</script>
</head>
<body>
	<div class="head container">
		<div class="input-wrapper">
			<div class="input-group">
				 <span class="input-group-addon">出发地</span>
				 <div class="select-wrapper">
				 	<input type="hidden" name="from" id="from"/>
					<input type="text" data-id="from" class="station" last=""/>
					<ul class="dropdown-menu station-select" role="menu">
		              
		            </ul>
				 </div>
			</div>
		</div>
		<div class="input-wrapper">
			<div class="input-group">
				 <span class="input-group-addon">目的地</span>
				 <div class="select-wrapper">
				 	<input type="hidden" name="to" id="to"/>
					<input type="text" data-id="to" class="station" last=""/>
					<ul class="dropdown-menu station-select" role="menu">
		              
		            </ul>
				 </div>
			</div>
		</div>
		<div class="btn-wrapper">
			<a href="javascript:;" class="btn btn-default" id="query">查询</a>
			<a href="javascript:;" class="btn btn-default" id="chart">趋势图</a>
			<!-- <a href="javascript:;" class="btn btn-default" id="stop">停止</a> -->
		</div>
	</div>
	<div class="container table-list">
		
	</div>
	<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/ticket.js"></script>
	<script type="text/javascript" src="${ctx}/js/station_name.js"></script>
	<script type="text/javascript" src="${ctx}/js/fusioncharts.js"></script>
</body>
</html>