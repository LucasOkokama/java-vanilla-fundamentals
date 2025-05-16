package com.lucasokokama.registration.dao;

import com.lucasokokama.registration.model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDao {
  public int registerEmployee(Employee employee) throws ClassNotFoundException {
    String INSERT_USERS_SQL = "INSERT INTO employees" +
            "(id, first_name, last_name, username, password, address, contact) VALUES" +
            "(?, ?, ?, ?, ?, ?, ?);";

    int result = 0;

    Class.forName("com.mysql.jdbc.Driver");

    try (
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "root");
      PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)
    ) {
      preparedStatement.setInt(1, 1);
      preparedStatement.setString(2, employee.getFirstName());
      preparedStatement.setString(3, employee.getLastName());
      preparedStatement.setString(4, employee.getUsername());
      preparedStatement.setString(5, employee.getPassword());
      preparedStatement.setString(6, employee.getAddress());
      preparedStatement.setString(7, employee.getContact());

      System.out.println(preparedStatement);
      result = preparedStatement.executeUpdate();
    } catch (SQLException e) {
      printSQLException(e);
    }
    return result;
  }

  private void printSQLException(SQLException ex) {
    for (Throwable e: ex) {
      if (e instanceof SQLException) {
        e.printStackTrace(System.err);
        System.err.println("SQLState: " + ((SQLException) e).getSQLState());
        System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
        System.err.println("Message: " + e.getMessage());
        Throwable t = ex.getCause();
        while (t != null) {
          System.out.println("Cause: " + t);
          t = t.getCause();
        }
      }
    }
  }
}
