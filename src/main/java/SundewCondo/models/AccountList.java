package SundewCondo.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountList {
    private List<Account> accounts;
    private Account currentAccount;

    public AccountList() {
        accounts = new ArrayList<>();
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account signInAccount(String user, String pin) {
        for (Account e : accounts){
            if (e.getUsername().equals(user)){
                if (e.comparePassword(pin)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
                    Date date = new Date();
                    String[] data = formatter.format(date).split(",");

                    currentAccount = new Account(e.getUsername(), pin, e.getName(), e.getStatus(), e.getTel(),
                            e.getAddress(), e.getEmail(), data[0], data[1], e.getImage());

                    accounts.add(0,currentAccount);
                    accounts.remove(e);

                    return currentAccount;
                }
            }
        }
        return null;
    }

    public boolean checkUser(String user) {
        for (Account e: accounts) {
            if (e.getUsername().equals(user)){ return true; }
        }
        return false;
    }

    public void addAccount(String user, String name, String email, String tel, String address, String image) {
        if (email.isEmpty()){
            email = null;
        }
        accounts.add(accounts.size(), new Account(user, "0000", name, "staff", tel, address, email, "--/--/----", "--:--:--", image));
    }

    public void changePassword(String newPin, Account currentAccount) {
        currentAccount.setPassword(newPin);
        accounts.add(0, currentAccount);
        for(int i = 1 ; i < accounts.size(); i++){
            if(accounts.get(i).getUsername().equals(currentAccount.getUsername())){
                accounts.remove(accounts.get(i));
                break;
            }
        }
    }

}
