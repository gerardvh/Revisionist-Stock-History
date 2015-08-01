<%-- 
    Document   : testing_page
    Created on : Jul 30, 2015, 2:41:05 PM
    Author     : gerardvh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Testing Page</h1>
        <h2>${error_message}</h2>
        
        <h2>Here is the User State</h2>
        ${user.userState}
        
    </body>
</html>
