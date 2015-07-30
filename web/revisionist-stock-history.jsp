<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Revisionist Stock History</title>
</head>
<body>
<h1>${user.name}</h1>
<h2>${user.date}</h2>
<!-- For-each loop that takes the ArrayList<Stock> and shows data in a table -->
<p>
<table border="1" cellpadding="2">
    <tr><th>Symbol</th><th>Name</th><th>Shares</th><th>Price</th><th>Total Value</th><th>Actions</th></tr>
<c:forEach var="stock" items="${user.stockList}" varStatus="loopStatus">
    <form action="/JH7_gvanhalsema/" method="POST">
        <tr>
            <!-- <td><input type="text" name="symbol" value="${stock.symbol}"></td> -->
            <!-- <td><input type="text" name="name" value="${stock.name}"></td> -->
            <!-- <td><input type="number" name="numShares" value="${stock.numShares}"></td> -->
            <!-- <td><input type="number" name="price" value="${stock.price}"></td> -->
            <!-- <td><input type="number" name="value" value="${stock.value}"></td> -->
            <td><div name="symbol">${stock.symbol}</div></td>
            <td><div name="name">${stock.name}</div></td>
            <td><div name="numShares">${stock.numShares}</div></td>
            <td><div name="price">${stock.priceFormatted}</div></td>
            <td><div name="value">${stock.valueFormatted}</div></td>
            
            <td><input type="submit" name="action" value="Buy">
                <input type="submit" name="action" value="Sell">
                <input type="hidden" name="id" value="${stock.id}"/></td>
        </form> 
        </tr>
</c:forEach>
</table>
<p/>
<form action="/JH7_gvanhalsema/" method="POST">
    <input type="submit" name="action" value="Next Month">
    <input type="submit" name="action" value="Populate Database">
</form>

<h3>Net Worth: ${user.netWorthFormatted}</h3>

<!-- Date (Progress) and buttons to move on -->

<!-- Cash and Net Worth totals. -->

</body>
</html>
