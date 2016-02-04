<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><lang:message text="user-page.title"/> <c:out value="${user.getLogin()}"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1><lang:message text="user-page.header"/> <c:out value="${user.getLogin()}"/></h1>
        <p><b><lang:message text="user.name"/>:</b> <c:out value="${user.getName()}" /></p>
        <p><b><lang:message text="user.email"/>:</b> <c:out value="${user.getEmail()}" /></p>
        <p><b><lang:message text="user.phone"/>:</b> <c:out value="${user.getPhone()}" /></p>
        <p><b><lang:message text="user.address"/>:</b><br><c:out value="${user.getAddress()}" /></p>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>