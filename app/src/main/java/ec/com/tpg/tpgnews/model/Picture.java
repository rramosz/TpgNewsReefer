package ec.com.tpg.tpgnews.model;

/**
 * Created by us on 7/2/2018.
 */

public class Picture {
    private String picture;
    private String user_name;
    private String time;
    private String like_number="0";

    public Picture(String picture, String user_name, String time, String like_number) {
        this.picture = picture;
        this.user_name = user_name;
        this.time = time;
        this.like_number = like_number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLike_number() {
        return like_number;
    }

    public void setLike_number(String like_number) {
        this.like_number = like_number;
    }
}
