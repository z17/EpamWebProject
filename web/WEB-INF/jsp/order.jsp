<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><lang:message text="order.title"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1><lang:message text="order.header"/></h1>
        <c:set var="total" value="${0}"/>
        <c:if test="${currentOrder != null}">
            <c:forEach var="entry" items="${currentOrder}">
                <c:set var="item" value="${entry.key}" />
                <c:set var="total" value="${item.getPrice() * entry.value + total}"/>
                <div class="item" data-id="<c:out value="${item.getId()}"/>">
                    <h2><c:out value="${item.getName()}"/></h2>
                    <div class="price">
                        <lang:message text="item.price"/>: <span class="price-sum"><c:out value="${item.getPrice()}"/></span> р.
                    </div>
                    <div class="count"><lang:message text="item.count"/>: <span class="js-order-item-less order-item-less">-</span> <span class="js-order-item-count order-item-count"><c:out value="${entry.value}"/></span> <span class="js-order-item-more order-item-more">+</span></div>
                </div>
            </c:forEach>
            <c:if test="${sessionScope.user != null}">
                <form method="POST">
                    <p><lang:message text="order.sum"/>: <c:out value="${total}"/> р.</p>
                    <input type="submit" name="submit" value="<lang:message text="order.to-order"/>" />
                </form>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <div class="login-order-form">
                    <p><lang:message text="order.login-error"/>: </p>
                    <%@ include file="tmp/login.jsp" %>
                </div>
            </c:if>
        </c:if>
        <c:if test="${currentOrder == null}">
            <p><lang:message text="order.empty"/></p>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <h1><lang:message text="order.last"/></h1>
            <c:if test="${ordersList != null}">
                <c:forEach var="order" items="${ordersList}">
                    <div class="order" data-id="<c:out value="${order.getId()}"/>">
                        <h3><lang:message text="order.order"/> <c:out value="${order.getId()}"/></h3>
                        <p><lang:message text="order.date"/>:
                            <fmt:parseDate value="${order.getTime()}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                            <fmt:formatDate value="${parsedDate}" var="stdDatum" type="date" pattern="d.MM.yyyy" />
                            <c:out value="${stdDatum}" />
                        </p>
                        <p><lang:message text="order.price"/>: <c:out value="${order.getPrice()}"/> р.</p>
                        <p><lang:message text="order.status"/>: <lang:message text="${'order.status-'.concat(order.getStatus())}"/></p>
                        <p><a href="/order/<c:out value="${order.getId()}"/>"><lang:message text="order.more"/></a> </p>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${ordersList == null}">
                <p><lang:message text="order.empty-last"/></p>
            </c:if>
        </c:if>

    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>