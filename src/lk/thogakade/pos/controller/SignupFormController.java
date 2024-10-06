package lk.thogakade.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.thogakade.pos.bo.BoFactory;
import lk.thogakade.pos.bo.custom.UserBo;
import lk.thogakade.pos.bo.custom.impl.CustomerBoImpl;
import lk.thogakade.pos.bo.custom.impl.UserBoImpl;
import lk.thogakade.pos.dto.UserDto;
import lk.thogakade.pos.enums.BoType;
import lk.thogakade.pos.util.PasswordManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtPassword;

    UserBo bo = BoFactory.getInstance().getBo(BoType.USER);
    public void registerOnAction(ActionEvent actionEvent){
        try{
            if (bo.saveUser(new UserDto(txtEmail.getText(),txtPassword.getText()))){ //INSERT/ UPDATE/ DELETE
                new Alert(Alert.AlertType.CONFIRMATION,"User Saved!").show();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again!").show();
            }
        }catch (ClassNotFoundException| SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }

    private void clearFields() {
        txtEmail.clear();
        txtPassword.clear();
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }
}
