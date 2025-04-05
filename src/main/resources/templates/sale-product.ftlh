<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload Excel File</title>
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bulma@1.0.2/css/bulma.min.css"
    >
</head>
<body>
<h2>Upload Excel File</h2>
<form action="/upload" method="post" enctype="multipart/form-data">
    <label for="fileChooser">Choose file:</label>
    <input id="fileChooser" type="file" name="file" accept=".xlsx, .xls" required />
    <button type="submit">Upload</button>
</form>
<#if products?has_content>
    <button>
        <a href="chart-view">View as a chart</a>
    </button>
</#if>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th><abbr title="Final Price">FP</abbr></th>
            <th><abbr title="Date of sell">Date</abbr></th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th><abbr title="Final Price">FP</abbr></th>
            <th><abbr title="Date of sell">Date</abbr></th>
        </tr>
        </tfoot>
        <tbody>
        <#list products as product>
            <tr>
                <th>${product.id}</th>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.finalPrice}</td>
                <td>${product.date!"N/A"}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</body>
</html>