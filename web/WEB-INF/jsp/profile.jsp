<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><lang:message text="profile.title"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1><lang:message text="profile.header"/> <c:out value="${sessionScope.user.getLogin()}" /></h1>
        <c:forEach var="message" items="${messages}">
            <p><lang:message text="${'user.'.concat(message)}"/></p>
        </c:forEach>
        <h2><lang:message text="profile.change-info"/></h2>
        <form method="POST" class="profile">
            <input type="text" name="name" placeholder="<lang:message text="user.name"/>" value="<c:out value="${sessionScope.user.getName()}" />" />
            <input type="text" name="email" placeholder="<lang:message text="user.email"/>" value="<c:out value="${sessionScope.user.getEmail()}" />" />
            <input type="text" name="phone" placeholder="<lang:message text="user.phone"/>" value="<c:out value="${sessionScope.user.getPhone()}" />" />
            <textarea type="text" name="address" placeholder="<lang:message text="user.address"/>"><c:out value="${sessionScope.user.getAddress()}" /></textarea>
            <input type="submit" name="submit-info" />
        </form>
        <h2><lang:message text="profile.change-password"/></h2>
        <form method="POST" class="profile">
            <input type="password" name="password" placeholder="<lang:message text="user.password" />" />
            <input type="password" name="new-password" placeholder="<lang:message text="user.new-password" />" />
            <input type="password" name="confirm-password" placeholder="<lang:message text="user.confirm-password" />" />
            <input type="submit" name="submit-pass" />
        </form>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>