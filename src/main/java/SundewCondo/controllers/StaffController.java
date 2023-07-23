package SundewCondo.controllers;


import SundewCondo.models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class StaffController {
    @FXML Button mailDocumentParcelBtn, residentBtn, signOutBtn;

    private Account currentAccount;

    @FXML public void handleMailDocumentParcelBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mailDocumentParcelManagementSystem.fxml"));
        stage.setScene(new Scene(loader.load(), 900, 800));
        MailDocumentParcelManagementSystemController mailDocumentParcelManagementSystemController  = loader.getController();
        mailDocumentParcelManagementSystemController.setCurrentAccount(currentAccount);
        stage.show();
    }

    @FXML public void handleResidentBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/residentManagementSystem.fxml"));
        stage.setScene(new Scene(loader.load(), 900, 800));

        ResidentManagementSystemController residentManagementSystemController = loader.getController();
        residentManagementSystemController.setCurrentAccount(currentAccount);

        stage.show();
    }

    @FXML public void handleSignOutBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signIn.fxml"));
        stage.setScene(new Scene(loader.load(), 900, 800));

        stage.show();
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

}
