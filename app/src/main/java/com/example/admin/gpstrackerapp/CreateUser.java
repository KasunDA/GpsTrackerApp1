package com.example.admin.gpstrackerapp;

/**
 * Created by Admin on 07-May-18.
 */

public class CreateUser {

public CreateUser()
{

}
    public String name;
    public String email;

    public CreateUser(String name, String email, String password, String code, String issharing, String lat, String lng, String imageurl,String userid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.issharing = issharing;
        this.lat = lat;
        this.lng = lng;
        this.imageurl = imageurl;
        this.userid=userid;

    }

    public String password;
    public String code;
    public String issharing;
    public String lat;
    public String lng;
    public String imageurl;
    public String userid;


}
