<!DOCTYPE html>
<html lang="en">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Регистрация</h4>
<form action="/registration" method="post">

    Имя пользователя:
    <label for="userFirstName"></label><input type="text" id="userFirstName" name="userFirstName"><br><br>
    Фамилия пользователя:
    <label for="userLastName"></label><input type="text" id="userLastName" name="userLastName"><br><br>
    Ник пользователя:
    <label for="userNickname"></label><input type="text" id="userNickname" name="userNickname"><br><br>
    Эл. почта:
    <label for="userEmail"></label><input type="email" id="userEmail" name="userEmail"><br><br>
    Пароль:
    <label for="userPassword"></label><input type="password" id="userPassword" name="userPassword"><br><br>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зарегистрироваться"/>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>

<#--<script>-->
<#--    $(document).ready(function () {-->
<#--        $('#registerButton').click(function () {-->
<#--            registerUser();-->
<#--        });-->
<#--    });-->

<#--    function registerUser() {-->
<#--        // Собираем данные из полей формы-->
<#--        var formData = {-->
<#--            userFirstName: $('#userFirstName').val(),-->
<#--            userLastName: $('#userLastName').val(),-->
<#--            userNickname: $('#userNickname').val(),-->
<#--            userEmail: $('#userEmail').val(),-->
<#--            userPassword: $('#userPassword').val()-->
<#--        };-->

<#--        // Отправляем AJAX запрос-->
<#--        $.ajax({-->
<#--            type: "POST",-->
<#--            url: "/api/user/registration",-->
<#--            contentType: "application/json",-->
<#--            data: JSON.stringify(formData),-->
<#--            success: function () {-->
<#--                alert('Пользователь успешно зарегистрирован!');-->
<#--            },-->
<#--            error: function (xhr, status, error) {-->
<#--                alert('Произошла ошибка при регистрации пользователя!');-->
<#--                console.log('Error:', error);-->
<#--            }-->
<#--        });-->
<#--    }-->
<#--</script>-->
</body>
</html>



