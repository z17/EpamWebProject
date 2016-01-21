<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><lang:message text="order.single.title"/> <c:out value="${order.getId()}"/></title>
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
            <h1><lang:message text="order.order"/> <c:out value="${order.getId()}"/></h1>
            <div class="order" data-id="<c:out value="${order.getId()}"/>">
                <p><lang:message text="order.date"/>:
                    <fmt:parseDate value="${order.getTime()}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                    <fmt:formatDate value="${parsedDate}" var="stdDatum" type="date" pattern="d.MM.yyyy" />
                    <c:out value="${stdDatum}" />
                </p>
                <p><lang:message text="order.price"/>: <c:out value="${order.getPrice()}"/> р.</p>
                <p><lang:message text="order.status"/>: <lang:message text="${'order.status-'.concat(order.getStatus())}"/></p>
                <c:if test="${displayActionForm}">
                    <form method="POST">
                        <c:choose>
                            <c:when test="${order.getStatus() == 'NEW'}">
                                <button type="submit" name="action" value="executed"><lang:message text="order-single.send" /></button>
                                <button type="submit" name="action" value="delete"><lang:message text="order-single.delete" /></button>
                            </c:when>
                            <c:when test="${order.getStatus() == 'EXECUTED'}">
                                <button type="submit" name="action" value="ready"><lang:message text="order-single.make-bill" /></button>
                            </c:when>
                            <c:when test="${order.getStatus() == 'READY' }">
                                <lang:message text="order-single.waiting" />
                            </c:when>
                        </c:choose>
                    </form>
                </c:if>
                <c:if test="${bill != null}">
                    <div class="bill">
                        <h3><lang:message text="bill.name" /> <c:out value="${bill.getId()}"/></h3>
                        <p><lang:message text="bill.sum" /> <c:out value="${bill.getSum()}"/> р.</p>
                        <c:if test="${bill.isPaid()}" >
                            <p><lang:message text="bill.paid"/></p>
                        </c:if>
                        <c:if test="${!bill.isPaid()}" >
                            <p><lang:message text="bill.not-paid" /></p>
                            <form method="POST">
                                <button type="submit" name="action" value="close"><lang:message text="bill.pay"/></button>
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
                            <lang:message text="item.price"/>: <span class="price-sum"><c:out value="${item.getPrice()}"/></span> р.
                        </div>
                        <div class="count"><lang:message text="item.count"/>: <c:out value="${entry.value}"/></div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${order == null}">
            <h1><lang:message text="order-single.deleted" /></h1>
        </c:if>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>