package com.lucasokokama.usermanagement.web;

import com.lucasokokama.usermanagement.dao.UserDAO;
import com.lucasokokama.usermanagement.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
  private UserDAO userDAO;

  public UserServlet() {
    this.userDAO = new UserDAO();
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    String endpoint = request.getServletPath();

    switch(endpoint){
      case "/new":
        showNewForm(request, response);
        break;

      case "/insert":
        try {
          insertUser(request, response);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;

      case "/delete":
        try {
          deleteUser(request, response);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;

      case("/edit"):
        try {
          showEditForm(request, response);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;

      case("/update"):
        try {
          updateUser(request, response);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;

      default:
        try {
          listUser(request, response);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
        break;
    }
  }

  private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    dispatcher.forward(request, response);
  }

  private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String country = request.getParameter("country");

    User newUser = new User(name, email, country);
    userDAO.insertUser(newUser);

    response.sendRedirect("list");
  }

  private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
    int id = Integer.parseInt(request.getParameter("id"));

    userDAO.deleteUser(id);

    response.sendRedirect("list");
  }

  private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
    int id = Integer.parseInt(request.getParameter("id"));

    User existingUser = userDAO.selectUserById(id);
    request.setAttribute("user", existingUser);

    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    dispatcher.forward(request, response);
  }

  private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String country = request.getParameter("country");

    User updatedUser = new User(id, name, email, country);
    userDAO.updateUser(updatedUser);

    response.sendRedirect("list");
  }

  private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
    List<User> listUser = userDAO.selectAllUsers();

    request.setAttribute("listUser", listUser);

    RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
    dispatcher.forward(request, response);
  }
}