package SundewCondo.models;

public class Account {
    private String username;
    private String password;
    private String name;
    private String status;
    private String tel;
    private String address;
    private String email;
    private String date;
    private String time;
    private String image;

    public Account(String username, String password, String name, String status, String tel, String address, String email, String date, String time, String image) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.status = status;
        this.tel = tel;
        this.address = address;
        this.email = email;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean compareUsername(String user){
        return username.equals(user);
    }

    public boolean comparePassword(String pin) {
        return password.equals(pin);
    }

    @Override
    public String toString() {
        return username + ',' + password + ',' + name +',' + status + ',' + tel + ',' + address + ',' + email + ',' + date + ',' + time + ',' + image;

    }

}
