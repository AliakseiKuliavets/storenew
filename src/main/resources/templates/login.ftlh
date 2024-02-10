<!DOCTYPE html>
<html lang="en">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Authorization</h4>
<a href="/">Home</a>
<form action="/authentication/login" method="post">

       User nickname:
        <input type="text" name="userNickname">
    <br><br>

        Password:
        <input type="password" name="userPassword">
    <br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Sign in"/>
</form>
<a href="/register">Registration</a>
</body>
</html>

