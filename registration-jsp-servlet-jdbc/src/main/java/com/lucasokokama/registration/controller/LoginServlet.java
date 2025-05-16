package com.lucasokokama.registration.controller;

import com.lucasokokama.registration.dao.EmployeeDao;
import com.lucasokokama.registration.model.Login;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private EmployeeDao employeeDao;

  @Override
  public void init(ServletConfig config) throws ServletException{
    super.init(config);
    employeeDao = new EmployeeDao();
  }

  protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    Login login = new Login(username, password);

    try {
      if(employeeDao.validateLogin(login)){
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login-success.jsp");
        dispatcher.forward(request, response);
      }
      else{
        request.setAttribute("loginError", "Usuário ou senha inválidos");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(request, response);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
