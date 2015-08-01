<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Revisionist Stock History - Choose Stocks</title>
</head>
<body>
  <h1>Please Choose Some Stocks to Follow</h1>
    <form action="/JH7_gvanhalsema/" method="GET">
      <table>
        <tr>
          <td><select name="stock1">
            <c:forEach var="stock" items="${stockSymbols}" varStatus="loopStatus">
              <option   value="${stock.symbol}">${stock.symbol} | ${stock.name}</option>
            </c:forEach>
          </select></td>
          </tr>
        <tr>
          <td><select name="stock2">
            <c:forEach var="stock" items="${stockSymbols}" varStatus="loopStatus">
              <option  value="${stock.symbol}">${stock.symbol} | ${stock.name}</option>
            </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td><select name="stock3">
            <c:forEach var="stock" items="${stockSymbols}" varStatus="loopStatus">
              <option  value="${stock.symbol}">${stock.symbol} | ${stock.name}</option>
            </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td><select name="stock4">
            <c:forEach var="stock" items="${stockSymbols}" varStatus="loopStatus">
              <option  value="${stock.symbol}">${stock.symbol} | ${stock.name}</option>
            </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td><select name="stock5">
            <c:forEach var="stock" items="${stockSymbols}" varStatus="loopStatus">
              <option  value="${stock.symbol}">${stock.symbol} | ${stock.name}</option>
            </c:forEach>
          </select></td>
        </tr>
      </table>
      <p><input type="submit" name="action" value="Choose Stocks"></p>
    </form>
  <!-- Dropdown Box | Dropdown Box | Dropdown Box | Dropdown Box | Dropdown Box -->
    <!-- Select tags populated with possible (Symbols | Names) name of option = stock.id -->



</body>
</html>