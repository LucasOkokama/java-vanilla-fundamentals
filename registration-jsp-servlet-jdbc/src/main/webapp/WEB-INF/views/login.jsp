<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>Login</title>
  </head>
  <body>
    <h1>Login</h1>
    <% if (request.getAttribute("loginError") != null) { %>
      <p style="color:red;"><%= request.getAttribute("loginError") %></p>
    <% } %>
    <form action="<%= request.getContextPath() %>/login" method="post">
        Username: <input type="text" name="username" required />
        Password: <input type="password" name="password" required />
        <input type="submit" value="Login" />
    </form>
  </body>
</html>