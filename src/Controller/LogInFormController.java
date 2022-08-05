package Controller;

import DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.peer.LabelPeer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane root;

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
    public void txtPasswordOnAction(ActionEvent actionEvent) {
        logIn();
    }
    public void btnLogInOnAction(ActionEvent actionEvent) {
        logIn();
    }
    public void logIn(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where userName = ? and password=?");
            preparedStatement.setObject(1,txtUserName.getText());
            preparedStatement.setObject(2,txtPassword.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean isExit = resultSet.next();

            if (isExit){
                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/HomeForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Home COVID-FINDER");
                stage.centerOnScreen();

            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Your Username Or Password Incorrect....");
                alert.showAndWait();
                txtUserName.requestFocus();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
