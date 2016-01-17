<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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
        <h1>Корзина</h1>
        <c:forEach var="entry" items="${currentOrder}">
            <c:set var="item" value="${entry.key}" />
            <div class="item" data-id="<c:out value="${item.getId()}"/>">
                <h2><c:out value="${item.getName()}"/></h2>
                <div class="price">
                    Цена: <span class="price-sum"><c:out value="${item.getPrice()}"/></span> р.
                </div>
                <div class="count">Количество: <c:out value="${entry.value}"/></div>
            </div>
        </c:forEach>
        <h1>Предыдущие заказы</h1>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>