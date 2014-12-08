<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
{
	<c:forEach items="${map}" var="entry">
	"${entry.key}": "&label=${fn:substring(date, 5, 10)}${entry.value}",
	</c:forEach>
	"heihei": "nothing"
}