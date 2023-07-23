package SundewCondo.models;

import SundewCondo.services.FileDataSource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostList {
    private List<Post> posts;

    public PostList() {
        this.posts = new ArrayList<>();
    }

    public void setPosts(List<Post> listPost) {
        posts = listPost;
    }

    public void addMail(String receiver, String sender, String recAddress, String sdAddress, String size, String image, String staff){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
        Date date = new Date();
        String[] data = formatter.format(date).split(",");

        posts.add(0, new Mail(data[0], data[1], receiver, sender, recAddress, sdAddress, size,
                "Mail", image, staff, null, null, false, null));
     }

    public void addDocument(String receiver, String sender, String recAddress, String sdAddress, String size, String image, String staff, String level){
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
         Date date = new Date();
         String[] data = formatter.format(date).split(",");

         posts.add(0, new Document(data[0], data[1], receiver, sender, recAddress, sdAddress,
                 size, "Document", image, staff,null, null, false, null, level));
         FileDataSource fileDataSource = new FileDataSource();
         try {
             fileDataSource.write(posts, "posts.csv");
         } catch (IOException e) {
             System.err.println("Can't write posts.csv");
         }
     }

    public void addParcel(String receiver, String sender, String recAddress, String sdAddress, String size, String image, String staff, String service, String tracking) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
         Date date = new Date();
         String[] data = formatter.format(date).split(",");

         posts.add(0, new Parcel(data[0], data[1], receiver, sender, recAddress, sdAddress,
                 size, "Parcel", image, staff,null, null, false, null,  service, tracking));
         FileDataSource fileDataSource = new FileDataSource();
         try {
             fileDataSource.write(posts, "posts.csv");
         } catch (IOException e) {
             System.err.println("Can't write posts.csv");
         }
     }

    public void removePost(Post post, Account currentAccount, String name) {
        for (Post e: posts){
            if (e.getDate().equals(post.getDate()) && e.getTime().equals(post.getTime())){
                posts.remove(e);
                break;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy|HH:mm:ss");
        Date date = new Date();
        String data = formatter.format(date);

        post.setStaffOut(currentAccount.getName());
        post.setStatus();
        post.setDateTimeOut(data);
        post.setTakeOut(name);
        posts.add(post);
    }

    public List<Post> postWant() {
        List<Post> postsWant = new ArrayList<>();
        for (Post e: posts){
            if (!e.getStatus()){
                postsWant.add(e);
            }
        }
        return postsWant;
    }

    public List<Post> getPostsByRoomNumber(String room){
        List<Post> post = new ArrayList<>();
        for (Post e: posts){
            if (isMatch(e, room)){
                post.add(e);
            }
        }
        return post;
    }

    public boolean isMatch(Post post, String room) {
        return post.getRecipientAddress().toLowerCase().contains(room.toLowerCase()) && !post.getStatus();
    }
}
