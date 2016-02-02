<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><lang:message text="signup.title"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1><lang:message text="signup.header"/></h1>
        <c:if test="${sessionScope.user == null}">
            <c:forEach var="message" items="${messages}">
                <p><lang:message text="${'user.'.concat(message)}"/></p>
            </c:forEach>

        <form method="POST" class="signup">
            <input type="text" name="name" placeholder="<lang:message text="user.name"/>" />
            <input type="text" name="login" placeholder="<lang:message text="user.login"/>" />
            <input type="password" name="password" placeholder="<lang:message text="user.password" />" />
            <input type="submit" name="submit" />
        </form>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <p><lang:message text="signup.already"/></p>
        </c:if>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>