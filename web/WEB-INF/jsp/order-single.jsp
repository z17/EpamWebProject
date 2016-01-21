<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Заказ № <c:out value="${order.getId()}"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <c:if test="${order != null}">
            <h1>Заказ №<c:out value="${order.getId()}"/></h1>
            <div class="order" data-id="<c:out value="${order.getId()}"/>">
                <p>Дата:
                    <fmt:parseDate value="${order.getTime()}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                    <fmt:formatDate value="${parsedDate}" var="stdDatum" type="date" pattern="d.MM.yyyy" />
                    <c:out value="${stdDatum}" />
                </p>
                <p>Цена: <c:out value="${order.getPrice()}"/> р.</p>
                <p>Статус: <c:out value="${order.getStatus()}"/></p>
                <c:if test="${displayActionForm}">
                    <form method="POST">
                        <c:choose>
                            <c:when test="${order.getStatus() == 'NEW'}">
                                <button type="submit" name="action" value="executed">Отправить на выполнение</button>
                                <button type="submit" name="action" value="delete">Удалить</button>
                            </c:when>
                            <c:when test="${order.getStatus() == 'EXECUTED'}">
                                <button type="submit" name="action" value="ready">Выставить счёт</button>
                            </c:when>
                            <c:when test="${order.getStatus() == 'READY'    }">
                                Ожидание оплаты счёта
                            </c:when>
                        </c:choose>
                    </form>
                </c:if>
                <c:if test="${bill != null}">
                    <div class="bill">
                        <h3>Выставлен счёт №<c:out value="${bill.getId()}"/></h3>
                        <p>На сумму <c:out value="${bill.getSum()}"/> р.</p>
                        <c:if test="${bill.isPaid()}" >
                            <p>Счёт оплачен</p>
                        </c:if>
                        <c:if test="${!bill.isPaid()}" >
                            <p>Счёт не оплачен</p>
                            <form method="POST">
                                <button type="submit" name="action" value="close">Оплатить</button>
                            </form>
                        </c:if>
                    </div>
                </c:if>
                <c:forEach var="entry" items="${order.getItems()}">
                    <c:set var="item" value="${entry.key}" />
                    <div class="item" data-id="<c:out value="${item.getId()}"/>">
                        <h2><c:out value="${item.getName()}"/></h2>
                        <div class="description">
                            <img src="<c:out value="${item.getImage()}"/>" class="item-image"/>
                            <c:out value="${item.getDescription()}"/></div>
                        <div class="price">
                            Цена: <span class="price-sum"><c:out value="${item.getPrice()}"/></span> р.
                        </div>
                        <div class="count">Количество: <c:out value="${entry.value}"/></div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${order == null}">
            <h1>Заказ удалён</h1>
        </c:if>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>