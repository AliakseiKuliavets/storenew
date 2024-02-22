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
<b>Product description: </b>${product.productDescription}<br>
<b>Price: </b>${product.productPrice}<br>
<b>Category: </b>${product.productCategory}<br>
<b>Brand: </b>${product.productBrand}<br>
<b>Author: </b>${product.placedByUser.userNickname}<br>
<b>Date added: </b>${product.dateOfCreate}<br>
<hr>
<#if user.userNickname??>
    <a href="/api/product/update/${product.productId}">Изменить товар...</a>
</#if>
</body>
</html>
