package SundewCondo.models;

public class Post {
    private String date;
    private String time;
    private String receiverName; // คนรับ
    private String senderName; // คนส่ง
    private String recipientAddress; //เลขห้อง
    private String senderAddress;
    private String size; // ขนาด
    private String type; //ประเภทของ
    private String image;
    private String staffIn; // staff ที่รับ
    private String staffOut;
    private String dateTimeOut;
    private Boolean status; // สถานะ มารับ true หรือ ไม่ false
    private String takeOut; // ลูกบ้านมารับของ

    public Post(String date, String time, String receiverName, String senderName, String recipientAddress, String senderAddress, String size, String type, String image, String staffIn, String staffOut, String dateTimeOut, Boolean status, String tackOut) {
        this.date = date;
        this.time = time;
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.recipientAddress = recipientAddress;
        this.senderAddress = senderAddress;
        this.size = size;
        this.type = type;
        this.image = image;
        this.staffIn = staffIn;
        this.staffOut = staffOut;
        this.dateTimeOut = dateTimeOut;
        this.status = status;
        this.takeOut = tackOut;
    }

    public String getStaffIn() {
        return staffIn;
    }

    public String getStaffOut() {
        return staffOut;
    }

    public String getDateTimeOut() {
        return dateTimeOut;
    }

    public String getTakeOut() {
        return takeOut;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSize() {
        return size;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getStaffName() {
        return staffIn;
    }

    public String getType() {
        return type;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public void setDateTimeOut(String dateTimeOut) {
        this.dateTimeOut = dateTimeOut;
    }

    public void setStaffOut(String staffOut) {
        this.staffOut = staffOut;
    }

    public void setStatus() {
        this.status = true;
    }

    public void setTakeOut(String takeOut) {
        this.takeOut = takeOut;
    }

    @Override
    public String toString() {
        return date + ',' + time + ',' + receiverName + ',' + senderName + ',' + recipientAddress + ',' + senderAddress + ',' + size + ',' + type + ',' + image + ',' + staffIn  + ',' +
                 staffOut  + ',' +  dateTimeOut  + ',' + status + ',' + takeOut;
    }
}
