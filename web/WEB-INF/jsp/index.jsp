<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <lang:message text="main.title" />
    </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1><lang:message text="main.header" /></h1>
        <c:forEach var="item" items="${menu}">
            <div class="item" data-id="<c:out value="${item.getId()}"/>">
                <h2><c:out value="${item.getName()}"/></h2>
                <div class="description">
                    <img src="<c:out value="${item.getImage()}"/>" class="item-image"/>
                    <c:out value="${item.getDescription()}"/></div>
                <div class="price">
                    <lang:message text="item.price" />: <span class="price-sum"><c:out value="${item.getPrice()}"/></span> р.
                </div>
                <div class="order-item">
                    <span class="js-order order-button"><lang:message text="item.order" /></span>
                    <span class="js-order-item-less order-item-less">-</span> <span class="js-order-item-count order-item-count">0</span> <span class="js-order-item-more order-item-more">+</span>
                </div>
            </div>
        </c:forEach>
        <c:if test="${menu == null || menu.size() == 0}">
            <p>Не найдено</p>
        </c:if>

    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
<script src="/js/script.js"></script>
</body>
</html>