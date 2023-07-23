package SundewCondo.controllers;

import SundewCondo.models.Account;
import SundewCondo.models.AccountList;
import SundewCondo.services.AccountFormat;
import SundewCondo.services.FileDataSource;
import SundewCondo.services.Format;
import SundewCondo.services.StringConfiguration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController {
    private List<Account> accounts;
    private List<Account> accountShow;
    private AccountList accountList;
    private Account currentAccount;
    private String imageName;
    private boolean imageStatus;

    @FXML Button signOutBtn, addBtn, addImageBtn;
    @FXML PasswordField currentPasswordField, newPasswordField, verifyPasswordField;
    @FXML TextField firstNameTextField, lastNameTextField, usernameTextField, emailTextField, telTextField, addressTextField;
    @FXML TableView<Account> accountsTable;
    @FXML ImageView imageStaff, imageAccount;
    @FXML Label addressLabel, telLabel, emailLabel;

    @FXML public void initialize() {
        accountShow = new ArrayList<>();
        accounts = new ArrayList<>();
        accountList = new AccountList();

        FileDataSource fileDataSource = new FileDataSource();
        try {
            List<String> list = fileDataSource.read("accounts.csv");
            Format format = new AccountFormat();
            format.getFormat(list, accounts);
            accountList.setAccounts(accounts);
        } catch (IOException e) {
            System.err.println("You can't read file");
        }

        accountsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAccount(newValue);
            }
        });
        clearValue();
        showAccountData();
    }

    @FXML public void handleSignOutBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/signIn.fxml"));
        stage.setScene(new Scene(loader.load(), 900, 800));
        stage.show();
    }

    @FXML public void handleAddAccountBtnOnAction(ActionEvent event) {
        if ( addressTextField.getText().isEmpty() || telTextField.getText().isEmpty() || firstNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() || !imageStatus ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill your information completely.");
            alert.show();
        } else {
            boolean check = accountList.checkUser(usernameTextField.getText().trim());
            if (check){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Wrong username!!!");
                alert.setContentText("The username that you've entered is incorrect. please check and try again");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation!!!");
                alert.setContentText("Name : " + firstNameTextField.getText() + ' ' + lastNameTextField.getText() + '\n' +
                        "Username : " + usernameTextField.getText() + '\n'  + "Tel : " + telTextField.getText() + '\n' +
                        "Address : " + addressTextField.getText());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    accountList.addAccount(usernameTextField.getText().trim(), firstNameTextField.getText().trim() + ' ' + lastNameTextField.getText().trim(),
                            emailTextField.getText().trim(), telTextField.getText().trim(), addressTextField.getText().trim(), imageName);

                    FileDataSource fileDataSource = new FileDataSource();
                    try {
                        fileDataSource.write(accounts, "accounts.csv");
                    } catch (IOException e) {
                        System.err.println("Can't write accounts.csv");
                    }
                }
            }
        }
        clearValue();
    }

    public void clearValue() {
        usernameTextField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        telTextField.clear();
        addressTextField.clear();
        emailTextField.clear();
        imageStatus = false;
        imageStaff.setImage(new Image(Paths.get("images/imageIcon.png").toUri().toString()));

        currentPasswordField.clear();
        newPasswordField.clear();
        verifyPasswordField.clear();

        imageAccount.setImage(new Image(Paths.get("images/accountIcon.png").toUri().toString()));
        addressLabel.setText("...");
        telLabel.setText("...");
        emailLabel.setText("...");

        accountShow.clear();
        for (int i = 0; i < accounts.size(); i ++) {
            if (accounts.get(i).getStatus().equals("admin")){
                continue;
            }
            accountShow.add(accounts.get(i));
        }
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList(accountShow);
        accountsTable.setItems(accountObservableList);
    }

    @FXML public void AddImage (MouseEvent event) {
        if (event.getTarget() == imageStaff) {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = chooser.showOpenDialog(imageStaff.getScene().getWindow());
            if (file != null) {
                try {
                    File destDir = new File("images");
                    destDir.mkdirs();
                    String[] fileSplit = file.getName().split("\\.");
                    String filename = LocalDate.now()+"_"+System.currentTimeMillis() + "." + fileSplit[fileSplit.length - 1];
                    Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);

                    Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                    imageName = imageName + filename;
                    imageName = "images" + File.separator + filename;
                    imageStaff.setImage(new Image(Paths.get(imageName).toUri().toString()));
                    imageStatus = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML public void handleChangePasswordBtnOnAction(ActionEvent event) {
        if(currentPasswordField.getText().isEmpty() || newPasswordField.getText().isEmpty() ||
                verifyPasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill your information completely.");
            alert.show();
        } else if (!currentAccount.comparePassword(currentPasswordField.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Wrong password!!!");
            alert.setContentText("Your Current password was incorrect.");
            alert.show();
        } else {
            if ( newPasswordField.getText().equals(verifyPasswordField.getText()) ){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation!!!");
                alert.setContentText("Username : " + currentAccount.getUsername() + '\n' + "Name : " + currentAccount.getName() );
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    accountList.changePassword(newPasswordField.getText(), currentAccount);

                    FileDataSource fileDataSource = new FileDataSource();
                    try {
                        fileDataSource.write(accounts, "accounts.csv");
                    } catch (IOException e) {
                        System.err.println("Can't write accounts.csv");
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Wrong password!!!");
                alert.setContentText("New Password and Verify Password do not match");
                alert.show();
            }
        }
        clearValue();
    }

    public void showAccountData() {
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Date", "field:date"));
        configs.add(new StringConfiguration("title:Time In", "field:time"));
        configs.add(new StringConfiguration("title:Username", "field:username"));
        configs.add(new StringConfiguration("title:Name", "field:name"));

        for (StringConfiguration conf: configs) {

            TableColumn col = new TableColumn(conf.get("title"));
            col.setPrefWidth(125);
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            accountsTable.getColumns().add(col);
        }
    }

    public void showSelectedAccount(Account account) {
        imageAccount.setImage(new Image(Paths.get(account.getImage()).toUri().toString()));
        addressLabel.setText(account.getAddress());
        telLabel.setText(account.getTel());
        if (account.getEmail() != null){
            emailLabel.setText(account.getEmail());
        }
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }
}
