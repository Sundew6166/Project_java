package SundewCondo.models;

public class Document extends Post {
    private String level;

    public Document(String date, String time, String receiverName, String senderName, String recipientAddress, String senderAddress, String size, String type, String image, String staffIn, String staffOut, String dateTimeOut, Boolean status, String tackOut, String level) {
        super(date, time, receiverName, senderName, recipientAddress, senderAddress, size, type, image, staffIn, staffOut, dateTimeOut, status, tackOut);
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return super.toString() + ',' + level;
    }
}
