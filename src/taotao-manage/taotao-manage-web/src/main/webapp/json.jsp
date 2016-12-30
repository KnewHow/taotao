<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String fname = request.getParameter("callback");
	if(fname!=null){
	    out.print(fname+"({\"abc\":123})");
	}else{
	    out.print("fun({\"abc\":123})");
	}
%>