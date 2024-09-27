package lk.thogakade.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.thogakade.pos.dao.DatabaseAccessCode;
import lk.thogakade.pos.dto.CustomerDto;
import lk.thogakade.pos.view.tm.CustomerTm;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane context;
    public TableView<CustomerTm> table;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colSalary;
    public TableColumn colOperate;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public TextField txtSearchHere;
    public JFXButton btnSaveCustomer;
    private String searchText = "";

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));

        loadAllCustomers(searchText);

        table.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->{
                    if (newValue!=null){
                        setData(newValue);
                    }
                });
        txtSearchHere.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchText = newValue;
            try {
                loadAllCustomers(searchText);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private void setData(CustomerTm newValue) {
        txtEmail.setEditable(false);
        btnSaveCustomer.setText("Update Customer");
        txtEmail.setText(newValue.getEmail());
        txtName.setText(newValue.getName());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        txtContact.setText(newValue.getContact());
    }

    private void loadAllCustomers(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();
        int counter =1;
        Button btn = new Button("Delete");
        for (CustomerDto dto:searchText.length()>0?DatabaseAccessCode.searchCustomer(searchText):
                DatabaseAccessCode.findAllCustomer()) {
            CustomerTm tm = new CustomerTm(
                counter, dto.getName(), dto.getEmail(), dto.getContact(), dto.getSalary(),btn
            );
            observableList.add(tm);
            counter++;

            btn.setOnAction(e->{
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?", ButtonType.NO, ButtonType.YES);
                    Optional<ButtonType> selectedButtonType = alert.showAndWait();
                    if (selectedButtonType.equals(ButtonType.YES)){
                        if (DatabaseAccessCode.deleteCustomer(dto.getEmail())){
                            new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted!").show();
                            loadAllCustomers(searchText);
                        }else {
                            new Alert(Alert.AlertType.WARNING,("Try Again!")).show();
                        }
                    }

                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,ex.getMessage()).show();
                }
            });

        }
        table.setItems(observableList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void manageLoyaltyCardOnAction(ActionEvent actionEvent) {
    }

    public void addNewCustomerOnAction(ActionEvent actionEvent) {
        txtEmail.setEditable(true);
        btnSaveCustomer.setText("Save Customer");
        clearFields();
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) {
        try{
            if (btnSaveCustomer.getText().equals("Save Customer")){
                if (DatabaseAccessCode.createCustomer(
                        txtEmail.getText(),txtName.getText(),txtContact.getText(),Double.parseDouble(txtSalary.getText())
                )){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved!").show();
                    clearFields();
                    loadAllCustomers(searchText);
                }else{
                    new Alert(Alert.AlertType.WARNING,("Try Again!")).show();
                }
            }else {
                if (DatabaseAccessCode.updateCustomer(
                        txtEmail.getText(),txtName.getText(),txtContact.getText(),Double.parseDouble(txtSalary.getText())
                )){
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated!").show();
                    clearFields();
                    loadAllCustomers(searchText);
                    //------------------------
                    txtEmail.setEditable(true);
                    btnSaveCustomer.setText("Save Student");
                }else{
                    new Alert(Alert.AlertType.WARNING,("Try Again!")).show();
                }
            }

        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,String.format("Try Again %n%s",e.toString())).show();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }

    private void clearFields() {
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
    }
}
