<!DOCTYPE html>
<html lang="en">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<#if user.userNickname??>
    <h3>Имя пользователя: <i>${user.userFirstName}</i></h3>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Sign out"/>
    </form>
<#else>
    <a href="/api/login">Sign in</a>
</#if>
<hr>
<h4>Products from all over the world</h4>
<form id="searchForm" action="/api" method="get">
    Search by ad title:
    <label><input type="text" name="name"></label>
    <label for="productCategorySearch"></label><select name="category" id="productCategorySearch">
        <option value="">-- Choose Category --</option>
    </select>
    <label for="productBrandSearch"></label><select name="brand" id="productBrandSearch">
        <option value="">-- Choose Brand --</option>
    </select>
    <input type="submit" value="Поиск"/>
</form>
<#list products as product>
    <div>
        <p><b>${product.productName}</b> ${product.productPrice} eur. |
            <a href="/api/product/${product.productId}">Details...</a>
        </p>
    </div>
<#else>
    <h3>Products not found</h3>
</#list>

<#if user.userNickname??>
    <hr>
    <h3>Create a new product</h3>
    <form id="createProductForm" action="/api/create" method="post" enctype="multipart/form-data">
        <label>
            Product Name:
            <input type="text" name="productName" id="productName"/>
        </label><br><br>
        <label>
            Ad Description:
            <input type="text" name="productDescription" id="productDescription"/>
        </label><br><br>
        <label>
            Price:
            <input type="number" name="productPrice" id="productPrice"/>
        </label><br><br>
        <label>
            Category:
            <select name="productCategory" id="productCategoryCreate"></select>
        </label><br><br>
        <label>
            Brand:
            <select name="productBrand" id="productBrandCreate"></select>
        </label><br><br>
        <label>
            Image 1:
            <input type="file" name="file1" id="file1"/>
        </label><br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Добавить товар"/>
    </form>
</#if>
<script>
    $(document).ready(function () {
        $('#searchForm').submit(function (event) {
            let category = $('#productCategorySearch').val();
            let brand = $('#productBrandSearch').val();

            $('input[name="category"]').val(category);
            $('input[name="brand"]').val(brand);
        });
    });

    $('#createProductForm').submit(function (event) {
        const formData = new FormData();
        formData.append('productDescription', $('#productDescription').val());
        formData.append('productPrice', $('#productPrice').val());
        formData.append('productCategory', $('#productCategoryCreate').val());
        formData.append('productBrand', $('#productBrandCreate').val());
        formData.append('userNickname', user["userNickname"]);
        formData.append('file1', $('#file1')[0].files[0]);

        $.ajax({
            url: '/api/start/create',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                // Обработка успешного ответа
            },
            error: function (xhr, status, error) {
                // Обработка ошибки
            }
        });
    });

    $(document).ready(function () {
        $.ajax({
            url: '/api/enum/allCategory',
            method: 'GET',
            success: function (data) {
                data.forEach(function (category) {
                    $('#searchForm #productCategorySearch')
                        .append('<option value="' + category + '">' + category + '</option>');
                    $('#createProductForm #productCategoryCreate')
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
                    $('#searchForm #productBrandSearch')
                        .append('<option value="' + brand + '">' + brand + '</option>');
                    $('#createProductForm #productBrandCreate')
                        .append('<option value="' + brand + '">' + brand + '</option>');
                });
            },
            error: function (xhr, status, error) {
                console.error('Failed to fetch product brands: ' + status + ', ' + error);
            }
        });
    });
</script>
</body>
</html>

