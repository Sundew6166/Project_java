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

public class MailDocumentParcelManagementSystemController {
    @FXML Button signOutBtn, homeBtn, addMailBtn, addDocumentBtn, addParcelBtn, receiveBtn;
    @FXML TextField firstSenderMailTextField, lastSenderMailTextField, firstReceiverMailTextField, lastReceiverMailTextField, sizeMailTextField, recipientAddressMailTextField, senderAddressMailTextField, takeOutTextField,
    firstSenderDocumentTextField, lastSenderDocumentTextField, firstReceiverDocumentTextField, lastReceiverDocumentTextField, recipientAddressDocumentTextField, sizeDocumentTextField, levelDocumentTextField, senderAddressDocumentTextField,
    firstSenderParcelTextField, lastSenderParcelTextField, firstReceiverParcelTextField, lastReceiverParcelTextField, sizeParcelTextField, recipientAddressParcelTextField , senderAddressParcelTextField, serviceTextField, trackingTextField, searchRoomNumberTextField ;
    @FXML ImageView imageMail, imageDocument, imageParcel, imagePost;
    @FXML TableView<Post> postsTable;
    @FXML Label receiverLabel, sizeLabel, staffLabel, senderAddressLabel;

    private Post selectedPost;
    private Account currentAccount;
    private List<Post> posts;
    private RoomList roomList;
    private PostList postList;
    private String imageName;
    private boolean imageStatus;

    public void initialize() {
        List<Room> rooms = new ArrayList<>();
        posts = new ArrayList<>();
        roomList = new RoomList();
        postList = new PostList();

        FileDataSource fileDataSource = new FileDataSource();
        try {
            List<String> listRoom = fileDataSource.read("rooms.csv");
            Format formatRoom = new RoomFormat();
            formatRoom.getFormat(listRoom, rooms);
            roomList.setRooms(rooms);

            List<String> listPost = fileDataSource.read("posts.csv");
            Format formatPost = new PostFormat();
            formatPost.getFormat(listPost, posts);
            postList.setPosts(posts);
        } catch (IOException e) {
            System.err.println("You can't read file");
        }

        postsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedAccount(newValue);
            }
        });

        clearValue();
        showMailDocumentParcelData();
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

    @FXML public void handleAddMailBtnOnAction() {
        if (firstSenderMailTextField.getText().isEmpty() || lastSenderMailTextField.getText().isEmpty() || firstReceiverMailTextField.getText().isEmpty() ||
                lastReceiverMailTextField.getText().isEmpty() || recipientAddressMailTextField.getText().isEmpty() || senderAddressMailTextField.getText().isEmpty() ||
                sizeMailTextField.getText().isEmpty() || !imageStatus) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill your information completely.");
            alert.show();
        } else {
            Room room = roomList.checkRoomInCondo(recipientAddressMailTextField.getText().trim());
            if (room != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation!!!");
                alert.setContentText("Sender : " + firstSenderMailTextField.getText().trim() + ' ' + lastSenderMailTextField.getText().trim() + '\n' +
                        "Receiver : " + firstReceiverMailTextField.getText().trim() + ' ' + lastReceiverMailTextField.getText().trim() + '\n' +
                        "Sander address : " + senderAddressMailTextField.getText().trim() + '\n' +
                        "Recipient address : " + recipientAddressMailTextField.getText().trim() + '\n' +
                        "Type : Mail\nSize : " + sizeMailTextField.getText().trim());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    postList.addMail(firstReceiverMailTextField.getText().trim() + ' ' + lastReceiverMailTextField.getText().trim(),
                            firstSenderMailTextField.getText().trim() + ' ' + lastSenderMailTextField.getText().trim(),
                            recipientAddressMailTextField.getText().trim(), senderAddressMailTextField.getText().trim(),
                            sizeMailTextField.getText().trim(), imageName, currentAccount.getName());

                    FileDataSource fileDataSource = new FileDataSource();
                    try {
                        fileDataSource.write(posts, "posts.csv");
                    } catch (IOException e) {
                        System.err.println("Can't write posts.csv");
                    }

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error!!!");
                alert.setContentText("Can't find this room. please check and try again");
                alert.show();
            }
        }
        clearValue();
    }

    @FXML public void handleAddDocumentBtnOnAction() {
        if (firstSenderDocumentTextField.getText().isEmpty() || lastSenderDocumentTextField.getText().isEmpty() || firstReceiverDocumentTextField.getText().isEmpty() ||
                lastReceiverDocumentTextField.getText().isEmpty() || senderAddressDocumentTextField.getText().isEmpty() ||
                sizeDocumentTextField.getText().isEmpty() || recipientAddressDocumentTextField.getText().isEmpty() || !imageStatus) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill your information completely.");
            alert.show();
        } else {
            Room room = roomList.checkRoomInCondo(recipientAddressDocumentTextField.getText().trim());
            if (room != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation!!!");
                alert.setContentText("Sender : " + firstSenderDocumentTextField.getText().trim() + ' ' + lastSenderDocumentTextField.getText().trim() + '\n' +
                        "Receiver : " + firstReceiverDocumentTextField.getText().trim() + ' ' + lastReceiverDocumentTextField.getText().trim() + '\n' +
                        "Sander address : " + senderAddressDocumentTextField.getText().trim() + '\n' +
                        "Recipient address : " + recipientAddressDocumentTextField.getText().trim() + '\n' +
                        "Type : Document\nSize : " + sizeDocumentTextField.getText().trim() + '\n' + "Level : " + levelDocumentTextField.getText().trim());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    postList.addDocument(firstReceiverDocumentTextField.getText().trim() + ' ' + lastReceiverDocumentTextField.getText().trim(),
                            firstSenderDocumentTextField.getText().trim() + ' ' + lastSenderDocumentTextField.getText().trim(),
                            recipientAddressDocumentTextField.getText().trim(), senderAddressDocumentTextField.getText().trim(),
                            sizeDocumentTextField.getText().trim(),imageName, currentAccount.getName(), levelDocumentTextField.getText().trim());

                    FileDataSource fileDataSource = new FileDataSource();
                    try {
                        fileDataSource.write(posts, "posts.csv");
                    } catch (IOException e) {
                        System.err.println("Can't write posts.csv");
                    }

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error!!!");
                alert.setContentText("Can't find this room. please check and try again");
                alert.show();
            }
        }
        clearValue();
    }

    @FXML public void handleAddParcelBtnOnAction() {
        if (firstSenderParcelTextField.getText().isEmpty() || lastSenderParcelTextField.getText().isEmpty() || firstReceiverParcelTextField.getText().isEmpty() ||
                lastReceiverParcelTextField.getText().isEmpty() || recipientAddressParcelTextField.getText().isEmpty() || senderAddressParcelTextField.getText().isEmpty() ||
                sizeParcelTextField.getText().isEmpty() || trackingTextField.getText().isEmpty() || serviceTextField.getText().isEmpty() || !imageStatus) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please fill your information completely.");
            alert.show();
        } else {
            Room room = roomList.checkRoomInCondo(recipientAddressParcelTextField.getText().trim());
            if (room != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation!!!");
                alert.setContentText("Sender : " + firstSenderParcelTextField.getText().trim() + ' ' + lastSenderParcelTextField.getText().trim() + '\n' +
                        "Receiver : " + firstReceiverParcelTextField.getText().trim() + ' ' + lastReceiverParcelTextField.getText().trim() + '\n' +
                        "Sander address : " + senderAddressParcelTextField.getText().trim() + '\n' +
                        "Recipient address : " + recipientAddressParcelTextField.getText().trim() + '\n' +
                        "Type : Parcel\nSize : " + sizeParcelTextField.getText().trim() + "\nService : " + serviceTextField.getText().trim() + "\nTracking Number : " + trackingTextField.getText().trim());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    postList.addParcel(firstReceiverParcelTextField.getText().trim() + ' ' + lastReceiverParcelTextField.getText().trim(), firstSenderParcelTextField.getText().trim() + ' ' + lastSenderParcelTextField.getText().trim(), recipientAddressParcelTextField.getText().trim(), senderAddressParcelTextField.getText().trim(), sizeParcelTextField.getText().trim(), imageName, currentAccount.getName(), serviceTextField.getText().trim(), trackingTextField.getText().trim());

                    FileDataSource fileDataSource = new FileDataSource();
                    try {
                        fileDataSource.write(posts, "posts.csv");
                    } catch (IOException e) {
                        System.err.println("Can't write posts.csv");
                    }

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error!!!");
                alert.setContentText("Can't find this room. please check and try again");
                alert.show();
            }
        }
        clearValue();
    }

    public void clearValue() {
        firstSenderMailTextField.clear();
        lastSenderMailTextField.clear();
        firstReceiverMailTextField.clear();
        lastReceiverMailTextField.clear();
        recipientAddressMailTextField.clear();
        senderAddressMailTextField.clear();
        sizeMailTextField.clear();
        imageStatus = false;
        imageMail.setImage(new Image(Paths.get("images/imageIcon.png").toUri().toString()));

        firstSenderDocumentTextField.clear();
        lastSenderDocumentTextField.clear();
        firstReceiverDocumentTextField.clear();
        lastReceiverDocumentTextField.clear();
        recipientAddressDocumentTextField.clear();
        senderAddressDocumentTextField.clear();
        sizeDocumentTextField.clear();
        levelDocumentTextField.clear();
        imageStatus = false;
        imageDocument.setImage(new Image(Paths.get("images/imageIcon.png").toUri().toString()));

        firstSenderParcelTextField.clear();
        lastSenderParcelTextField.clear();
        firstReceiverParcelTextField.clear();
        lastReceiverParcelTextField.clear();
        recipientAddressParcelTextField.clear();
        senderAddressParcelTextField.clear();
        sizeParcelTextField.clear();
        trackingTextField.clear();
        serviceTextField.clear();
        imageStatus = false;
        imageParcel.setImage(new Image(Paths.get("images/imageIcon.png").toUri().toString()));

        imagePost.setImage(new Image(Paths.get("images/postIcon.png").toUri().toString()));

        receiverLabel.setText("...");
        staffLabel.setText("...");
        sizeLabel.setText("...");
        senderAddressLabel.setText("...");

        receiveBtn.setDisable(true);
        takeOutTextField.clear();

        ObservableList<Post> postsObservableList = FXCollections.observableArrayList(postList.postWant());
        postsTable.setItems(postsObservableList);

        searchRoomNumberTextField.clear();
    }

    public void showSelectedAccount(Post post) {
        selectedPost = post;
        imagePost.setImage(new Image(Paths.get(post.getImage()).toUri().toString()));
        staffLabel.setText(post.getStaffName());
        sizeLabel.setText(post.getSize());
        receiverLabel.setText(post.getRecipientAddress());
        senderAddressLabel.setText(post.getSenderAddress());
    }

    @FXML public void handleReceive(MouseEvent event) {
        if (selectedPost != null) {
            receiveBtn.setDisable(false);
        }
        else {
            receiveBtn.setDisable(true);
        }
    }

    @FXML public void receiveBtnOnAction(MouseEvent event) {
        if (takeOutTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please enter tack out name of receiver.");
            alert.show();
            clearValue();
            return;
        }
        postList.removePost(selectedPost, currentAccount, takeOutTextField.getText().trim());
        receiveBtn.setDisable(true);

        FileDataSource fileDataSource = new FileDataSource();
        try {
            fileDataSource.write(posts, "posts.csv");
        } catch (IOException e) {
            System.err.println("Can't write posts.csv");
        }
        clearValue();
    }

    public void showMailDocumentParcelData() {
        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Date In", "field:date"));
        configs.add(new StringConfiguration("title:Time In", "field:time"));
        configs.add(new StringConfiguration("title:Receiver", "field:receiverName"));
        configs.add(new StringConfiguration("title:Sender", "field:senderName"));
        configs.add(new StringConfiguration("title:Type", "field:type"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setPrefWidth(100);
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            postsTable.getColumns().add(col);
        }
    }

    @FXML public void AddImage (MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        if (event.getTarget() == imageView) {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = chooser.showOpenDialog(imageView.getScene().getWindow());
            if (file != null) {
                try {
                    File destDir = new File("images");
                    destDir.mkdirs();
                    String[] fileSplit = file.getName().split("\\.");
                    String filename = LocalDate.now()+"_"+System.currentTimeMillis() + "." + fileSplit[fileSplit.length - 1];
                    Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);

                    Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                    imageName = "images" + File.separator + filename;
                    imageView.setImage(new Image(Paths.get(imageName).toUri().toString()));
                    imageStatus = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    @FXML public void handleSearchRoomBtnOnAction(ActionEvent event) {
        ObservableList<Post> postsObservableList = FXCollections.observableArrayList(postList.getPostsByRoomNumber(searchRoomNumberTextField.getText().trim()));
        postsTable.setItems(postsObservableList);
    }
}