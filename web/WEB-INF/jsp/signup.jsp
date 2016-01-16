<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">
</head>
<body>
<div class="wrapper">
    <%@ include file="tmp/header.jsp" %>
    <div class="middle">
        <h1>Регистрация</h1>
        <form method="POST" class="signup">
            <input type="text" name="name" placeholder="Имя" />
            <input type="text" name="login" placeholder="Логин" />
            <input type="password" name="password" placeholder="Пароль" />
            <input type="submit" name="submit" />
        </form>
    </div>
    <%@ include file="tmp/footer.jsp" %>
</div>
</body>
</html>