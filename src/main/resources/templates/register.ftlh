<!DOCTYPE html>
<html lang="en">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Registration</h4>
<form action="/registration" method="post">

    Username:
    <label for="userFirstName"></label><input type="text" id="userFirstName" name="userFirstName"><br><br>
    User Last Name:
    <label for="userLastName"></label><input type="text" id="userLastName" name="userLastName"><br><br>
    User nickname:
    <label for="userNickname"></label><input type="text" id="userNickname" name="userNickname"><br><br>
    Email:
    <label for="userEmail"></label><input type="email" id="userEmail" name="userEmail"><br><br>
    Password:
    <label for="userPassword"></label><input type="password" id="userPassword" name="userPassword"><br><br>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>



