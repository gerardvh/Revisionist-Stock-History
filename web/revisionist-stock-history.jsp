<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Revisionist Stock History</title>
</head>
<body>
<h1>Your Stock List</h1>
<HR>
<h2>${error_message}</h2>
<!-- For-each loop that takes the ArrayList<Stock> and shows data in a table -->
<p>
<c:forEach var="stock" items="${user.stockList}" varStatus="loopStatus">
<div name="symbol"><h3>${stock.symbol} | ${stock.name}</h3></div>
<br/>

<table border="1" cellpadding="2">
    <div><tr><th>Shares</th><th>Price</th><th>Total Value</th><th>Actions</th></tr>
    <form action="update_stocks" method="GET">
        <tr>
            <td><div name="numShares">${stock.numShares}</div></td>
            <td><div name="price">${stock.priceFormatted}</div></td>
            <td><div name="value">${stock.valueFormatted}</div></td>
            
            <td><input type="submit" name="action" value="Buy">
                <input type="submit" name="action" value="Sell">
                <input type="hidden" name="stock_symbol" value="${stock.symbol}"/></td>
        </form>
        </tr>
</table></div>
<div><img src="http://chart.finance.yahoo.com/z?s=${stock.symbol}"></div>
<HR>
</c:forEach>
</p>
<form action="/JH7_gvanhalsema/" method="POST">
<!--    <input type="submit" name="action" value="Next Month">
    <input type="submit" name="action" value="Refresh State">-->
</form>

<h3>Net Worth: ${user.netWorthFormatted}</h3>

<!-- Date (Progress) and buttons to move on -->

<!-- Cash and Net Worth totals. -->

</body>
</html>
