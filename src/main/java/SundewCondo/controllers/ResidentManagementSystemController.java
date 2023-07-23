    package SundewCondo.controllers;

    import SundewCondo.models.*;
    import SundewCondo.services.*;

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
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    public class ResidentManagementSystemController {
        @FXML Button signOutBtn, homeBtn, addResidentBtn, addRoomBtn, searchRoomBtn;
        @FXML PasswordField currentPasswordTextField, newPasswordTextField, verifyPasswordTextField;
        @FXML TextField firstNameTextField, lastNameTextField, telTextField, emailTextField, buildingTextField, floorTextField,
                roomNumberTextField, roomTypeTextField, rmTypeTextField, rmBuildingTextField, rmFloorTextField, rmNumberTextField;
        @FXML TableView<Room> roomsTable;
        @FXML ImageView bedImage;
        @FXML Label buildingLabel, floorLabel, roomNumberLabel, residentLabel,searchRoomNumberTextField;

        private Account currentAccount;
        private List<Account> accounts;
        private AccountList accountList;
        private RoomList roomList;
        private List<Room> rooms;

        @FXML public void initialize() {
            accounts = new ArrayList<>();
            rooms = new ArrayList<>();
            accountList = new AccountList();
            roomList = new RoomList();

            FileDataSource fileDataSource = new FileDataSource();
            try {
                List<String> listAccount = fileDataSource.read("accounts.csv");
                Format formatAccount = new AccountFormat();
                formatAccount.getFormat(listAccount, accounts);
                accountList.setAccounts(accounts);

                List<String> listRoom = fileDataSource.read("rooms.csv");
                Format formatRoom = new RoomFormat();
                formatRoom.getFormat(listRoom, rooms);
                roomList.setRooms(rooms);
            } catch (IOException e) {
                System.err.println("You can't read file");
            }

            roomsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    showSelectedRoom(newValue);
                }
            });

            clearValue();
            showRoomData();
        }

        @FXML public void handleSignOutBtnOnAction(ActionEvent event) throws IOException {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signIn.fxml"));
            stage.setScene(new Scene(loader.load(), 900, 800));

            stage.show();
        }

        @FXML public void handleHomeBtnOnAction(ActionEvent event) throws IOException {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/staff.fxml"));
            stage.setScene(new Scene(loader.load(), 900, 800));

            stage.show();
        }

        @FXML public void handleChangePasswordBtnOnAction(ActionEvent event) {
            if (currentPasswordTextField.getText().isEmpty() || newPasswordTextField.getText().isEmpty() || verifyPasswordTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please fill your information completely.");
                alert.show();
            } else if (!currentAccount.comparePassword(currentPasswordTextField.getText().trim())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Wrong password!!!");
                alert.setContentText("Your Current password was incorrect.");
                alert.show();
            } else {
                if (newPasswordTextField.getText().equals(verifyPasswordTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Confirmation!!!");
                    alert.setContentText("Username : " + currentAccount.getUsername() + '\n' + "Name : " + currentAccount.getName());
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        accountList.changePassword(newPasswordTextField.getText(), currentAccount);

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

        @FXML public void handleAddResidentBtnOnAction(ActionEvent event) {
            if (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() || telTextField.getText().isEmpty() ||
                    buildingTextField.getText().isEmpty() || floorTextField.getText().isEmpty() || roomNumberTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please fill your information completely.");
                alert.show();
            } else {
                Room room = roomList.checkRoomInCondo(buildingTextField.getText().trim() + ' ' + floorTextField.getText().trim() + roomNumberTextField.getText().trim());
                if (room == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't find this room. please check and try again.");
                    alert.show();
                } else {
                    boolean check = roomList.addResidentInCondo(room, firstNameTextField.getText().trim() + ' ' + lastNameTextField.getText().trim());
                    if (check) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Confirmation!!!");
                        alert.setContentText("Name : " + firstNameTextField.getText().trim() + ' ' + lastNameTextField.getText().trim() + "\nTel : " +
                                telTextField.getText().trim() + "\nBuilding : " + room.getBuilding() + "\nFloor : " + room.getFloor() +
                                "\nRoom Number : " + room.getRoomNumber());

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {

                            FileDataSource fileDataSource = new FileDataSource();
                            try {
                                fileDataSource.write(rooms, "rooms.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Can't add resident. the room is full");
                        alert.show();
                    }
                }
            }
            clearValue();
        }

        @FXML public void handleAddRoomBtnOnAction(ActionEvent event) {
            if (rmTypeTextField.getText().isEmpty() || rmBuildingTextField.getText().isEmpty()
                    || rmFloorTextField.getText().isEmpty() || rmNumberTextField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill your information completely.");
                alert.show();
            } else {
                Room checkRoom = roomList.checkRoomInCondo(rmBuildingTextField.getText().trim() + ' ' + rmFloorTextField.getText().trim() + rmNumberTextField.getText().trim());
                if (checkRoom != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("This number room already has. please check and try again");
                    alert.show();
                } else {
                    boolean create = roomList.createRoom(rmBuildingTextField.getText().trim(), rmFloorTextField.getText().trim(), rmNumberTextField.getText().trim(), rmTypeTextField.getText().trim());
                    if (create) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Confirmation!!!");
                        alert.setContentText("Room Type : " + rmTypeTextField.getText().trim() + '\n' + "Building : " + rmBuildingTextField.getText().trim()
                                + '\n' + "Floor : " + rmFloorTextField.getText().trim() + '\n' + "Room Number : " + rmNumberTextField.getText().trim());
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            FileDataSource fileDataSource = new FileDataSource();
                            try {
                                fileDataSource.write(rooms, "rooms.csv");
                            } catch (IOException e) {
                                System.err.println("Can't write rooms.csv");
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Error!!!");
                        alert.setContentText("Can't create room number. please check and try again");
                        alert.show();
                    }
                }
            }
            clearValue();
        }

        public void clearValue() {
            firstNameTextField.clear();
            lastNameTextField.clear();
            telTextField.clear();
            buildingTextField.clear();
            floorTextField.clear();
            roomNumberTextField.clear();
            emailTextField.clear();

            rmBuildingTextField.clear();
            rmFloorTextField.clear();
            rmNumberTextField.clear();
            rmTypeTextField.clear();

            currentPasswordTextField.clear();
            newPasswordTextField.clear();
            verifyPasswordTextField.clear();


            bedImage.setImage(new Image(Paths.get("images/hotel-information-sign-icons-png-871360.png").toUri().toString()));
            buildingLabel.setText("...");
            floorLabel.setText("...");
            roomNumberLabel.setText("...");
            residentLabel.setText("...");

            ObservableList<Room> roomObservableList = FXCollections.observableArrayList(rooms);
            roomsTable.setItems(roomObservableList);
        }

        public void showRoomData() {
            ArrayList<StringConfiguration> configs = new ArrayList<>();
            configs.add(new StringConfiguration("title:Room", "field:roomName"));
            configs.add(new StringConfiguration("title:Room Type", "field:roomType"));

            for (StringConfiguration conf : configs) {
                TableColumn col = new TableColumn(conf.get("title"));
                col.setPrefWidth(200);
                col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
                roomsTable.getColumns().add(col);
            }
        }

        public void showSelectedRoom(Room room) {
            buildingLabel.setText(room.getBuilding());
            floorLabel.setText(room.getFloor());
            roomNumberLabel.setText(room.getRoomNumber());
            residentLabel.setText("" + room.getCountResident());
        }

        public void setCurrentAccount(Account currentAccount) {
            this.currentAccount = currentAccount;
        }
    }
