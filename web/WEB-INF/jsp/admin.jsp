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
        <c:if test="${sessionScope.user != null}">
            <h1>Список активных заказов</h1>
            <c:if test="${ordersList != null}">
                <c:forEach var="order" items="${ordersList}">
                    <div class="order" data-id="<c:out value="${order.getId()}"/>">
                        <p>Цена: <c:out value="${order.getPrice()}"/></p>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${ordersList == null}">
                <p>Нет заказов</p>
            </c:if>
        </c:if>

        <c:if test="${sessionScope.user == null}">
            <p>Доступ запрещён</p>
        </c:if>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
</body>
</html>