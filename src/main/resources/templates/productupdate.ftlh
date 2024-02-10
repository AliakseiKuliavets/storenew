<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Product details</h4>

<#list images as img>
    <div>
        <img src="/images/${img.imageId}" height="300px"/><br><br>
    </div>
</#list>

<b>Product Name: </b> ${product.productName}<br>
<label>
    New Product Name:
    <input type="text" name="productName" id="productName"/>
</label>
<form id="updateForm" action="/api/product/update/${product.productId}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Update Name"/>
</form>

<b>Product description: </b>${product.productDescription}<br>
<form action="/api/product/update/${product.productId}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Изменить описание"/>
</form>

<b>Price: </b>${product.productPrice}<br>
<form action="/api/product/update/${product.productId}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Изменить цену"/>
</form>

<b>Category: </b>${product.productCategory}<br>
<form action="/api/product/update/${product.productId}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Изменить категорию"/>
</form>

<b>Brand: </b>${product.productBrand}<br>
<form action="/api/product/update/${product.productId}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Изменить brand"/>
</form>
<hr>
<#if user.userNickname??>
    <form action="/remove/${product.productId}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Удалить товар"/>
    </form>
</#if>
<script>
    document.getElementById('updateForm').addEventListener('submit', function (event) {
        let newName = document.getElementById('productName').value;
        this.action = "/api/product/update/${product.productId}and" + newName;
    });

</script>
</body>
</html>


// токен не забываем