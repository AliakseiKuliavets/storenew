<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Подробная информация о товаре</h4>

<#list images as img>
    <img src="/images/${img.imageId}" height="60px"/><br><br>
</#list>

<b>Название товара: </b> ${product.productName}<br>
<b>Описание товара: </b>${product.productDescription}<br>
<b>Цена: </b>${product.productPrice}<br>
<b>Категория: </b>${product.productCategory}<br>
<b>Бренд: </b>${product.productBrand}<br>
<b>Автор: </b>${product.placedByUser.userNickname}<br>
<b>Дата добавления: </b><span th:text="${product.dateOfCreate}"></span><br>
<hr>
<form>
    <a href="/remove/${product.productId})}" methods="post"></a>
    <input type="submit" value="Удалить товар"/>
</form>
</body>
</html>
