package lk.thogakade.pos.dao;

import javafx.scene.control.Alert;
import lk.thogakade.pos.db.DbConnection;
import lk.thogakade.pos.dto.CustomerDto;
import lk.thogakade.pos.dto.UserDto;
import lk.thogakade.pos.util.PasswordManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {

    //Customer Management

    public static List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {
        searchText = "%"+searchText+"%";
        String sql = "SELECT * FROM customer WHERE email LIKE  ? || name LIKE ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
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

    //product management

    public static int getLastProductId() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
