<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Заказать</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1>Заказ №<c:out value="${order.getId()}"/></h1>
        <div class="order" data-id="<c:out value="${order.getId()}"/>">
            <p>Дата: <c:out value="${order.getTime()}"/></p>
            <p>Цена: <c:out value="${order.getPrice()}"/></p>
            <p>Статус: <c:out value="${order.getStatus()}"/></p>
        </div>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>