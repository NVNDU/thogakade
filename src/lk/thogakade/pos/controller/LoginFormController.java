package lk.thogakade.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.thogakade.pos.dao.DatabaseAccessCode;
import lk.thogakade.pos.dto.UserDto;
import lk.thogakade.pos.util.PasswordManager;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    public TextField txtEmail;
    public TextField txtPassword;
    public AnchorPane context;


    public void signInOnAction(ActionEvent actionEvent) {
        try{
            UserDto ud  = DatabaseAccessCode.findUser(txtEmail.getText());
            if (ud!=null){
                if (PasswordManager.checkPassword(txtPassword.getText(),ud.getPassword())){
                    setUi("DashboardForm");
                }else {
                    new Alert(Alert.AlertType.WARNING,"Check ur Password!").show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"User Email Not Found!").show();
            }

        }catch (ClassNotFoundException | SQLException | IOException e){
            new Alert(Alert.AlertType.WARNING,e.toString()).show();
        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage= (Stage)context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }


}
