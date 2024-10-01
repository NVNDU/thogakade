package lk.thogakade.pos.dao;

import enums.DaoType;
import javafx.scene.control.Alert;
import lk.thogakade.pos.dao.custom.CustomerDao;
import lk.thogakade.pos.dao.custom.ProductDao;
import lk.thogakade.pos.dao.custom.UserDao;
import lk.thogakade.pos.dao.custom.impl.CustomerDaoImpl;
import lk.thogakade.pos.dao.custom.impl.ProductDaoImpl;
import lk.thogakade.pos.dao.custom.impl.UserDaoImpl;
import lk.thogakade.pos.db.DbConnection;
import lk.thogakade.pos.dto.CustomerDto;
import lk.thogakade.pos.dto.UserDto;
import lk.thogakade.pos.entity.Customer;
import lk.thogakade.pos.entity.Product;
import lk.thogakade.pos.entity.User;
import lk.thogakade.pos.util.PasswordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
//    CustomerDao customerDao = new CustomerDaoImpl();
//    ProductDao productDao = new ProductDaoImpl();
//    UserDao userDao = new UserDaoImpl();

    CustomerDao customerDao = (CustomerDao) DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    ProductDao productDao = (ProductDao) DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoType.USER);
    // User Management
    public boolean createUser(String email,String password) throws ClassNotFoundException, SQLException {
        return userDao.save(
                new User(email,password)
        );
    }
    public UserDto findUser(String email) throws ClassNotFoundException, SQLException {

        User user = userDao.find(email);
        if (user!=null){
            return new UserDto(
                    user.getEmail(),
                    user.getPassword()
            );
        }
        return null;
    }

    //Customer Management
    public boolean createCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
       return customerDao.save(
               new Customer(email,name,contact,salary)
       );
    }
    public boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        return customerDao.update(
                new Customer(email,name,contact,salary)
        );
    }
    public CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        Customer customer = customerDao.find(email);
        if (customer!=null){
            return new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
            );
        }
        return null;
    }
    public boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        return customerDao.delete(email);
    }
    public List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer customer:customerDao.findAll()) {
            dtos.add(new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
            ));
        }
        return dtos;
    }
    public List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {
        searchText = "%"+searchText+"%";

        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer customer:customerDao.searchCustomer(searchText)) {
            dtos.add(new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary()
            ));
        }
        return dtos;
    }

    //product management

    public int getLastProductId() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductId();
    }
    public boolean createProduct(int code, String description) throws SQLException, ClassNotFoundException {
        return productDao.save(
                new Product(code,description)
        );
    }
    public boolean updateProduct(int code,String description) throws SQLException, ClassNotFoundException {
        return productDao.update(
                new Product(code,description)
        );
    }
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        return productDao.delete(code);
    }

}
