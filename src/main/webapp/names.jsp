
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <ul>
        <%
            List<String> names = (List<String>) request.getAttribute("names");
            for(String name : names) {
        %>
        <li><%= name %></li>
        <%
            }
        %>
    </ul>
</div>
</body>
</html>
