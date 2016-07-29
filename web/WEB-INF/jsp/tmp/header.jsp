<%@ taglib prefix="lang" uri="/WEB-INF/tld/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header">
    <div class="logo">
        <lang:message text="site.logo" />
    </div>
    <div class="login-header-form">
        <%@ include file="login.jsp" %>
    </div>
    <div class="menu">
        <a href="/"><lang:message text="pages.main" /></a> |
        <a href="/order"><lang:message text="pages.basket" /> <span class="js-order-count"></span></a>
            <c:if test="${sessionScope.user.getGroup().getId() == 2}" >
            | <a href="/admin"><lang:message text="pages.admin" /></a>
        </c:if>
    </div>
</div>