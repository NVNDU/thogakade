package lk.thogakade.pos.dao;

import lk.thogakade.pos.dto.CustomerDto;
import lk.thogakade.pos.dto.UserDto;
import lk.thogakade.pos.util.PasswordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    // User Management
    public static boolean createUser(String email,String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");
        String sql = "INSERT INTO user VALUES (?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2, PasswordManager.encryptPassword(password));
        return preparedStatement.executeUpdate() > 0;
    }

    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade","root","1234");
        String sql = "SELECT * FROM user WHERE email= ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,email);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    //Customer Management
    public static boolean createCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,contact);
        preparedStatement.setDouble(4,salary);

        return preparedStatement.executeUpdate() > 0;
    }
    public static boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");
        String sql = "UPDATE customer SET name = ?, contact = ?, salary = ? WHERE email = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,contact);
        preparedStatement.setDouble(3,salary);
        preparedStatement.setString(4,email);
        return preparedStatement.executeUpdate() > 0;
    }
    public static CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");
        String sql = "SELECT * FROM customer WHERE email = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,email);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }
    public static boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");
        String sql = "DELETE FROM customer WHERE email = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        return preparedStatement.executeUpdate() > 0;
    }
    public static List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");
        String sql = "SELECT * FROM customer";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<CustomerDto> dtos = new ArrayList<>();

        while (resultSet.next()){
            dtos.add (new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return dtos;
    }
    public static List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {
        searchText = "%"+searchText+"%";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thogakade",
                "root","1234");

        String sql = "SELECT * FROM customer WHERE email LIKE  ? || name LIKE ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,searchText);
        preparedStatement.setString(2,searchText);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<CustomerDto> dtos = new ArrayList<>();

        while (resultSet.next()){
            dtos.add (new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }
        return dtos;
    }

}
