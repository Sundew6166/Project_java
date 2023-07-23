package SundewCondo.models;

public class Parcel extends Post {
    private String service;
    private String trackingNumber;

    public Parcel(String date, String time, String receiverName, String senderName, String recipientAddress, String senderAddress, String size, String type, String image, String staffIn, String staffOut, String dateTimeOut, Boolean status, String tackOut, String service, String trackingNumber) {
        super(date, time, receiverName, senderName, recipientAddress, senderAddress, size, type, image, staffIn, staffOut, dateTimeOut, status, tackOut);
        this.service = service;
        this.trackingNumber = trackingNumber;
    }

    public String getService() {
        return service;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ',' + service + ',' + trackingNumber;
    }
}
