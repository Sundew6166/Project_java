package SundewCondo.controllers;

import SundewCondo.models.Account;
import SundewCondo.models.AccountList;
import SundewCondo.services.AccountFormat;
import SundewCondo.services.FileDataSource;
import SundewCondo.services.Format;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignInController {

    @FXML PasswordField passwordField;
    @FXML Button programmerBtn, signInBtn, suggestBtn;
    @FXML TextField usernameTextField;

    private List<Account> accounts;
    private Account currentAccount;
    private AccountList accountList;

    @FXML public void initialize() {
        accounts = new ArrayList<>();
        accountList = new AccountList();

        FileDataSource fileDataSource = new FileDataSource();
        try {
            List<String> list = fileDataSource.read("accounts.csv");
            Format format = new AccountFormat();
            format.getFormat(list, accounts);
            accountList.setAccounts(accounts);
        } catch (IOException e) {
            System.out.println("You can't read file");
        }
    }

    @FXML public void handleSuggestBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/suggest.fxml"));
        stage.setScene(new Scene(loader.load(), 900, 800));
        stage.show();
    }

    @FXML public void handleSignInBtnOnAction(ActionEvent event) throws IOException {
        currentAccount = accountList.signInAccount(usernameTextField.getText().trim(), passwordField.getText().trim());
        if (currentAccount == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong Username or Password!!!");
            alert.setContentText("the username or password you've entered is incorrect. please check and try again");
            alert.show();
        } else {
            if (currentAccount.getStatus().equals("admin")) {
                connectToAdmin(event, "/" + currentAccount.getStatus() + ".fxml");
            } else {
                connectToStaff(event, "/" + currentAccount.getStatus() + ".fxml");
            }
        }

        FileDataSource fileDataSource = new FileDataSource();
        try {
            fileDataSource.write(accounts, "accounts.csv");
        } catch (IOException exception) {
            System.err.println("Can't write accounts.csv");
        }

        usernameTextField.clear();
        passwordField.clear();
    }

    public void connectToAdmin(ActionEvent event,String path) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stage.setScene(new Scene(loader.load(), 900, 800));

        AdminController adminController = loader.getController();
        adminController.setCurrentAccount(currentAccount);

        stage.show();
    }

    public void connectToStaff(ActionEvent event,String path) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stage.setScene(new Scene(loader.load(), 900, 800));

        StaffController staffController = loader.getController();
        staffController.setCurrentAccount(currentAccount);

        stage.show();
    }

    @FXML public void handleProgrammerBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/programmer.fxml"));
        stage.setScene(new Scene(loader.load(), 900, 800));
        stage.show();
    }
}
