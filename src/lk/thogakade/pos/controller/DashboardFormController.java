package lk.thogakade.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane context;

    public void customerManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CustomerForm");
    }

    public void productManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProductMaintainForm");
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
    }

    public void orderDetailsOnAction(ActionEvent actionEvent) {
    }

    public void incomeReportOnAction(ActionEvent actionEvent) {
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }


}
