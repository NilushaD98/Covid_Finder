package Controller;

import DB.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tm.VaccineDataTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchVaccineDataFormController {
    public TextField txtGetNIC;
    public TableView<VaccineDataTM> tblVaccineInformation;
    public Label lblName;
    public Label lblViewName;
    public Label lblViewGender;
    public Label lblViewAge;
    public Label lblViewContact;
    public Label lblViewAddress;

    public AnchorPane root;
    public Label lblGender;
    public Label lblAge;
    public Label lblContact;
    public Label lblAddress;

    public void initialize(){
        fieldsVisible(false);
        tblVaccineInformation.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("vaccine"));
        tblVaccineInformation.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("dose"));
        tblVaccineInformation.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblVaccineInformation.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("moh"));
    }

    public void fieldsVisible(boolean value){
        lblName.setVisible(value);
        lblViewName.setVisible(value);
        lblGender.setVisible(value);
        lblViewGender.setVisible(value);
        lblAge.setVisible(value);
        lblViewAge.setVisible(value);
        lblAddress.setVisible(value);
        lblViewAddress.setVisible(value);
        lblContact.setVisible(value);
        lblViewContact.setVisible(value);
        tblVaccineInformation.setVisible(value);
    }
    public void txtGetNICOnAction(ActionEvent actionEvent) {
            searchData();
    }

    public void btnSaveDataOnAction(ActionEvent actionEvent) {
        searchData();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/HomeForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void searchData(){

        fieldsVisible(true);
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from data where nic= ?");
            preparedStatement.setObject(1,txtGetNIC.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                ObservableList<VaccineDataTM> items = tblVaccineInformation.getItems();
                String vaccine = resultSet.getString(7);
                String dose = resultSet.getString(8);
                String date = resultSet.getString(9);
                String moh = resultSet.getString(10);
                VaccineDataTM vaccineDataTM = new VaccineDataTM(vaccine, dose, date, moh);
                items.add(vaccineDataTM);
            }
        }

             catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from data where nic= ?");
            preparedStatement.setObject(1,txtGetNIC.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ObservableList<VaccineDataTM> items = tblVaccineInformation.getItems();
                lblViewName.setText(resultSet.getString(1));
                lblViewGender.setText(resultSet.getString(3));
                lblViewAge.setText(resultSet.getString(4));
                lblViewContact.setText(resultSet.getString(5));
                lblViewAddress.setText(resultSet.getString(6));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
