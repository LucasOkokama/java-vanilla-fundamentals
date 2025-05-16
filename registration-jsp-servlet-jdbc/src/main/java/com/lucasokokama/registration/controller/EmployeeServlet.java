package com.lucasokokama.registration.controller;

import com.lucasokokama.registration.dao.EmployeeDao;
import com.lucasokokama.registration.model.Employee;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class EmployeeServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private EmployeeDao employeeDao = new EmployeeDao();

  public EmployeeServlet(){
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    response.getWriter().append("Served at: ").append(request.getContextPath());

    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employeeregister.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String address = request.getParameter("address");
    String contact = request.getParameter("contact");

    Employee employee = new Employee();
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setUsername(username);
    employee.setPassword(password);
    employee.setContact(contact);
    employee.setAddress(address);

    try {
      employeeDao.registerEmployee(employee);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/employeedetails.jsp");
    dispatcher.forward(request, response);
  }
}
