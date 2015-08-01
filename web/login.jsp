<!DOCTYPE html>
<html>
<head>
  <title>Revisionist Stock History - Login</title>
</head>
<body>
<!-- Need a form here for the name/password. Maybe we only show it conditionally if there is no user object? -->
<form action="/JH7_gvanhalsema/" method="POST">
  <table>
  <tr>
    <td>Username: </td><td><input type="text" name="username" value=""></td>
    <td>Password: </td><td><input type="password" name="password" value=""></td>
  </tr>
  <tr>
    <td><input type="submit" name="action" value="Login"></td>
    <td><input type="submit" name="action" value="Create Account"></td>
  </tr>
  </table>
</form>
<p>${login_error}</p>
  

</body>
</html>