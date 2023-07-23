package SundewCondo.models;

public class Mail extends Post {

    public Mail(String date, String time, String receiverName, String senderName, String recipientAddress, String senderAddress, String size, String type, String image, String staffIn, String staffOut, String dateTimeOut, Boolean status, String tackOut) {
        super(date, time, receiverName, senderName, recipientAddress, senderAddress, size, type, image, staffIn, staffOut, dateTimeOut, status, tackOut);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
