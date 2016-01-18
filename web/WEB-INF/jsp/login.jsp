<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Войти</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1>Авторизация</h1>
        <div class="login-page">
            <c:if test="${successLogin == true}">
                <p>Вы успешно авторизованы</p>
            </c:if>
            <c:if test="${successLogin == false}">
                <p>Ошибка логина или пароля</p>
            </c:if>
            <%@ include file="tmp/login.jsp" %>
        </div>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
</body>
</html>