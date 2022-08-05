package Controller;

import DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.SplitReturn;
import tm.VaccineDataTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddNewVaccineDataFormController {

    public TextField txtName;
    public TextField txtNIC;
    public TextField txtAge;
    public TextField txtContact;
    public TextField txtAddress;
    public ComboBox<String> txtVaccine;
    public ComboBox<String> txtGender;
    public ComboBox<String> txtDose;
    public DatePicker txtDate;
    public TextField txtMOHArea;
    public AnchorPane root;
    public TextField txtGetNIC1;
    public Label lblName;
    public Label lblGender;
    public Label lblAge;
    public Label lblNIC;
    public Label lblContact;
    public Label lblAddress;
    public Label lblVaccine;
    public Label lblDose;
    public Label lblDate;
    public Label lblMOH;
    public TableView<VaccineDataTM> tblVaccineInformation;

    public void initialize(){
        fieldVisible(false);
        tblVaccineInformation.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("vaccine"));
        tblVaccineInformation.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("dose"));
        tblVaccineInformation.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblVaccineInformation.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("moh"));
        txtGender.setItems(FXCollections.observableArrayList("Male","Female"));
        txtVaccine.setItems((FXCollections.observableArrayList("Sinapharm","Pfizer","Sputnic","Modarna")));
        txtDose.setItems(FXCollections.observableArrayList("0","1","2","3"));
    }
    public void btnSaveDataOnAction(ActionEvent actionEvent) {
        addData();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/HomeForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        txtNIC.requestFocus();
    }

    public void txtNICOnAction(ActionEvent actionEvent) {
        txtGender.requestFocus();
    }

    public void txtAgeOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void txtContactOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void txtAddressOnActon(ActionEvent actionEvent) {
        txtVaccine.requestFocus();
    }

    public void txtMOHAreaOnAction(ActionEvent actionEvent) {
        addData();
    }

    public void getComboBoxValues(){
        System.out.println(txtDose.getValue());
    }

    public void addData(){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into data values (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setObject(1,txtName.getText());
            preparedStatement.setObject(2,txtNIC.getText());
            preparedStatement.setObject(3,txtGender.getValue());
            preparedStatement.setObject(4,txtAge.getText());
            preparedStatement.setObject(5,txtContact.getText());
            preparedStatement.setObject(6,txtAddress.getText());
            preparedStatement.setObject(7,txtVaccine.getValue());
            preparedStatement.setObject(8,txtDose.getValue());
            preparedStatement.setObject(9,txtDate.getValue());
            preparedStatement.setObject(10,txtMOHArea.getText());
            int i = preparedStatement.executeUpdate();

            if(i>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Information Saved");
                alert.showAndWait();
                txtName.requestFocus();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Information Saved");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtNameOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void txtGetNICOnAction(ActionEvent actionEvent) {
        isExitData();

    }

    public void btnSOKOnAction(ActionEvent actionEvent) {
        isExitData();
    }

    public void isExitData(){

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from data where nic=?");
            preparedStatement.setObject(1,txtGetNIC1.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<VaccineDataTM> items = tblVaccineInformation.getItems();
            items.clear();
            while (resultSet.next()){
                String vaccine = resultSet.getString(7);
                String dose = resultSet.getString(8);
                String date = resultSet.getString(9);
                String moh = resultSet.getString(10);
                VaccineDataTM vaccineDataTM = new VaccineDataTM(vaccine, dose, date, moh);
                items.add(vaccineDataTM);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from data where nic= ?");
            preparedStatement.setObject(1,txtGetNIC1.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ObservableList<VaccineDataTM> items = tblVaccineInformation.getItems();

                txtName.setText(resultSet.getString(1));
                txtNIC.setText(resultSet.getString(2));
                txtGender.setValue(resultSet.getString(3));
                txtAge.setText(resultSet.getString(4));
                txtContact.setText(resultSet.getString(5));
                txtAddress.setText(resultSet.getString(6));
                fieldVisible(true);
                fieldDisable(true);

            }
            else{
                fieldDisable(false);
                fieldVisible(true);
                txtName.requestFocus();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fieldDisable(boolean value){
        txtName.setDisable(value);
        txtNIC.setDisable(value);
        txtGender.setDisable(value);
        txtAge.setDisable(value);
        txtContact.setDisable(value);
        txtAddress.setDisable(value);
    }

    public void fieldVisible(boolean value){
        txtName.setVisible(value);
        txtNIC.setVisible(value);
        txtGender.setVisible(value);
        txtContact.setVisible(value);
        txtAddress.setVisible(value);
        txtAge.setVisible(value);
        txtVaccine.setVisible(value);
        txtDose.setVisible(value);
        txtDate.setVisible(value);
        txtMOHArea.setVisible(value);

        lblName.setVisible(value);
        lblAddress.setVisible(value);
        lblAge.setVisible(value);
        lblContact.setVisible(value);
        lblDate.setVisible(value);
        lblDose.setVisible(value);
        lblGender.setVisible(value);
        lblMOH.setVisible(value);
        lblNIC.setVisible(value);
        lblVaccine.setVisible(value);


    }
}
