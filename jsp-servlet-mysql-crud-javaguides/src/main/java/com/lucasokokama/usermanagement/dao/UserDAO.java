package com.lucasokokama.usermanagement.dao;

import com.lucasokokama.usermanagement.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
  private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
  private String jdbcUsername = "root";
  private String jdbcPassword = "root";

  private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, country) VALUES (?,?,?);";
  private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE id = ?;";
  private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users;";
  private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?;";
  private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?;";

  protected Connection getConnetion() {
    Connection connection = null;
    try{
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    catch(ClassNotFoundException e){
      e.printStackTrace();
    }
    return connection;
  }

  public void insertUser(User user){
    try(
            Connection connection = getConnetion();
            PreparedStatement stmt = connection.prepareStatement(INSERT_USER_SQL);
            ){
      stmt.setString(1, user.getName());
      stmt.setString(2, user.getEmail());
      stmt.setString(3, user.getCountry());
      stmt.executeUpdate();
    }
    catch(SQLException e){
      e.printStackTrace();
    }
  }

  public boolean updateUser(User user){
    boolean rowUpdated = false;
    try(
            Connection connection = getConnetion();
            PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_SQL);
            ){
      stmt.setString(1, user.getName());
      stmt.setString(2, user.getEmail());
      stmt.setString(3, user.getCountry());
      stmt.setInt(4, user.getId());
      rowUpdated = stmt.executeUpdate() > 0;
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    return rowUpdated;
  }

  public User selectUserById(int id){
    User user = null;
    try(
            Connection connection = getConnetion();
            PreparedStatement stmt = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
            ){
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if(rs.next()){
        String name = rs.getString("name");
        String email = rs.getString("email");
        String country = rs.getString("country");
        user = new User(id, name, email, country);
      }
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    return user;
  }

  public List<User> selectAllUsers(){
    List<User> users = new ArrayList<>();
    try(
            Connection connection = getConnetion();
            PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_USERS_SQL);
    ){
      ResultSet rs = stmt.executeQuery();

      while(rs.next()){
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String country = rs.getString("country");
        users.add(new User(id, name, email, country));
      }
    }
    catch(SQLException e){
      e.printStackTrace();
    }
    return users;
  }

  public boolean deleteUser(int id){
    boolean rowDeleted = false;
    try(
            Connection connection = getConnetion();
            PreparedStatement stmt = connection.prepareStatement(DELETE_USER_SQL);
            ){
      stmt.setInt(1, id);
    }
    catch(SQLException e){
      e.printStackTrace();
    }

    return rowDeleted;
  }
}
