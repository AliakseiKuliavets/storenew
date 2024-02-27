<!DOCTYPE html>
<html lang="en">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Product details</h4>

<#list images as img>
    <div>
        <img src="/api/images/${img.imageId}" height="300px"/><br><br>
    </div>
</#list>

<b>Product Name: </b> ${product.productName}<br>
<form id="updateName" action="/api/product/update/name/${product.productId}" method="post">
    <label>
        New Product Name:
        <input type="text" name="productName" id="productName" value="${product.productName}"/>
    </label>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Update Name"/>
</form>
<br>

<b>Product description: </b>${product.productDescription}<br>
<form id="productDescription" action="/api/product/update/description/${product.productId}" method="post">
    <label>
        New Product Description:
        <input type="text" name="productDescription" id="productDescription" value="${product.productDescription}"/>
    </label>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="change description"/>
</form>
<br>

<b>Price: </b>${product.productPrice}<br>
<form id="productPrice" action="/api/product/update/price/${product.productId}" method="post">
    <label>
        New Product Price:
        <input type="number" name="productPrice" id="productPrice" value="${product.productPrice}"/>
    </label>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="change the price"/>
</form>
<br>


<b>Category: </b>${product.productCategory}<br>
<form id="searchCategory" action="/api/product/update/category/${product.productId}" method="post">
    New Product Category:
    <label for="productCategory"></label>
    <select name="category" id="productCategory">
        <option value=""> ${product.productCategory} </option>
    </select>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Change category"/>
</form>
<br>

<b>Brand: </b>${product.productBrand}<br>
<form id="searchBrand" action="/api/product/update/brand/${product.productId}" method="post">
    New Product Brand:
    <label for="productBrand"></label>
    <select name="brand" id="productBrand">
        <option value=""> ${product.productBrand} </option>
    </select>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Change brand"/>
</form>
<br>

<hr>
<form action="/remove/${product.productId}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Remove product"/>
</form>
<script>

    $(document).ready(function () {
        $.ajax({
            url: '/api/enum/allCategory',
            method: 'GET',
            success: function (data) {
                data.forEach(function (category) {
                    $('#searchCategory #productCategory')
                        .append('<option value="' + category + '">' + category + '</option>');
                });
            },
            error: function (xhr, status, error) {
                console.error('Failed to fetch product categories: ' + status + ', ' + error);
            }
        });

        $.ajax({
            url: '/api/enum/allBrand',
            method: 'GET',
            success: function (data) {
                data.forEach(function (brand) {
                    $('#searchBrand #productBrand')
                        .append('<option value="' + brand + '">' + brand + '</option>');
                });
            },
            error: function (xhr, status, error) {
                console.error('Failed to fetch product brands: ' + status + ', ' + error);
            }
        });
    });

    document.getElementById('updateName').addEventListener('submit', function (event) {
        let newName = document.getElementById('productName').value;
        this.action = "/api/product/update/name/${product.productId}and" + newName;
    });

    document.getElementById('productDescription').addEventListener('submit', function (event) {
        let newDescription = document.getElementById('productDescription')
            .querySelector('input[name="productDescription"]').value;
        this.action = "/api/product/update/description/${product.productId}and" + newDescription;
    });

    document.getElementById('productPrice').addEventListener('submit', function (event) {
        let newPrice = document.getElementById('productPrice').querySelector('input[name="productPrice"]').value;
        this.action = "/api/product/update/price/${product.productId}and" + newPrice;
    });

    document.getElementById('searchCategory').addEventListener('submit', function (event) {
        let newCategory = document.getElementById('productCategory').value;
        this.action = "/api/product/update/category/${product.productId}and" + newCategory;
    });

    document.getElementById('searchBrand').addEventListener('submit', function (event) {
        let newBrand = document.getElementById('productBrand').value;
        this.action = "/api/product/update/brand/${product.productId}and" + newBrand;
    });
</script>
</body>
</html>
