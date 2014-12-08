<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<chart manageresize="1" bgcolor="000000" bgalpha="100" caption="${data.station_train_code}(${data.from_station_name} ${data.start_time}-${data.to_station_name} ${data.arrive_time})余票走势"
	canvasborderthickness="1" canvasbordercolor="008040" canvasbgcolor="000000"
	decimals="0" numdivlines="9"
	numdisplaysets="60" divlinecolor="008040" vdivlinecolor="008040"
	divlinealpha="100" chartleftmargin="10" basefontcolor="00dd00"
	showrealtimevalue="0"
	refreshinterval="2" labeldisplay="rotate" slantlabels="1"
	tooltipbgcolor="000000" tooltipbordercolor="008040" basefontsize="11"
	showalternatehgridcolor="0" legendbgcolor="000000" legendbordercolor="008040"
	legendpadding="35" showlabels="1" showborder="0">
	<categories>
		
	</categories>
	<c:if test="${fn:contains(data.station_train_code, 'D') or fn:contains(data.station_train_code, 'G')}">
	<dataset seriesname="商务座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="特等座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="一等座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="二等座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	</c:if>
	<c:if test="${not (fn:contains(data.station_train_code, 'D') or fn:contains(data.station_train_code, 'G'))}">
	<dataset seriesname="高级软卧" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="软卧" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="硬卧" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="软座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="硬座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	</c:if>
	<dataset seriesname="无座" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
	<dataset seriesname="其他" showvalues="0"
		alpha="100" anchoralpha="0" linethickness="2">
		
	</dataset>
</chart>