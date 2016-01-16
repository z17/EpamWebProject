<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Главная страница</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/style/normalize.css" type="text/css">
    <link rel="stylesheet" href="/style/style.css" type="text/css">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <div class="logo">
            Ресторан
        </div>
        <div class="login-form">
            <div class="login-title">
                Форма входа
            </div>
            <form method="POST" action="/login">
                <input type="text" name="login" placeholder="Логин" />
                <input type="password" name="password" placeholder="Пароль" />
                <input type="submit" />
            </form>
        </div>
    </div>
    <div class="middle">
        <h1>Меню</h1>
        <div class="item">
            <h2>Баранина</h2>
            <div class="description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
            <div class="price">
                Цена: <span class="price-sum">500</span> р.
            </div>
            <div class="order js-order">Заказать</div>
        </div>
        <div class="item">
            <h2>Баранина</h2>
            <div class="description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
            <div class="price">
                Цена: <span class="price-sum">500</span> р.
            </div>
            <div class="order js-order">Заказать</div>
        </div>
        <div class="item">
            <h2>Баранина</h2>
            <div class="description">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
            <div class="price">
                Цена: <span class="price-sum">500</span> р.
            </div>
            <div class="order js-order">Заказать</div>
        </div>
    </div>
    <div class="footer">
        <a href="/logout">Выход</a>
    </div>
</div>
</body>
</html>