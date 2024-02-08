<!DOCTYPE html>
<html lang="en">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Авторизация</h4>
<a href="/">Home</a>
<form action="/authentication/login" method="post">

        Ник нейм пользователя:
        <input type="text" name="userNickname">
    <br><br>

        Пароль:
        <input type="password" name="userPassword">
    <br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<a href="/register">Регистрация</a>
</body>
</html>

