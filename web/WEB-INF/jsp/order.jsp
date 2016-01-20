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
        <h1>Корзина</h1>
        <c:set var="total" value="${0}"/>
        <c:if test="${currentOrder != null}">
            <c:forEach var="entry" items="${currentOrder}">
                <c:set var="item" value="${entry.key}" />
                <c:set var="total" value="${item.getPrice() * entry.value + total}"/>
                <div class="item" data-id="<c:out value="${item.getId()}"/>">
                    <h2><c:out value="${item.getName()}"/></h2>
                    <div class="price">
                        Цена: <span class="price-sum"><c:out value="${item.getPrice()}"/></span> р.
                    </div>
                    <div class="count">Количество: <c:out value="${entry.value}"/></div>
                </div>
            </c:forEach>
            <c:if test="${sessionScope.user != null}">
                <form method="POST">
                    <p>Сумма заказа: <c:out value="${total}"/> р.</p>
                    <input type="submit" name="submit" value="Заказать" />
                </form>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <div class="login-order-form">
                    <p>Для заказа нужно авторизоваться: </p>
                    <%@ include file="tmp/login.jsp" %>
                </div>
            </c:if>
        </c:if>
        <c:if test="${currentOrder == null}">
            <p>Ваша корзина пуста</p>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <h1>Предыдущие заказы</h1>
            <c:if test="${ordersList != null}">
                <c:forEach var="order" items="${ordersList}">
                    <div class="order" data-id="<c:out value="${order.getId()}"/>">
                        <h3>Заказ №<c:out value="${order.getId()}"/></h3>
                        <p>Дата: <c:out value="${order.getTime()}"/></p>
                        <p>Цена: <c:out value="${order.getPrice()}"/> р.</p>
                        <p>Статус: <c:out value="${order.getStatus()}"/></p>
                        <p><a href="/order/<c:out value="${order.getId()}"/>">Подробнее</a> </p>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${ordersList == null}">
                <p>Нет предыдущих заказов</p>
            </c:if>
        </c:if>

    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>