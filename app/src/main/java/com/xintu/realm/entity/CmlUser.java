package com.xintu.realm.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by fanjianfeng on 2018/5/3.
 */
public class CmlUser extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String name;

    private String url;

    private String pic;

    private int age;

    private String demo;

//    private int count;
//
//    private int price;
//
//    //    @Required
//    private String nickname;
//
//    private boolean isShow;
//
//    @Required
//    private Long ins;

    public int getAge () {
        return age;
    }

    public void setAge ( int age ) {
        this.age = age;
    }

    public String getDemo () {
        return demo == null ? "" : demo;
    }

    public void setDemo ( String demo ) {
        this.demo = demo;
    }

//    @Override
//    public String toString () {
//        return "CmlUser{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                ", pic='" + pic + '\'' +
//                ", age=" + age +
//                ", demo='" + demo + '\'' +
//                ", count=" + count +
//                ", price=" + price +
//                ", nickname='" + nickname + '\'' +
//                ", isShow=" + isShow +
//                ", ins=" + ins +
//                '}';
//    }
//
//    public int getPrice () {
//        return price;
//    }
//
//    public void setPrice ( int price ) {
//        this.price = price;
//    }
//
//    public String getNickname () {
//        return nickname == null ? "" : nickname;
//    }
//
//    public void setNickname ( String nickname ) {
//        this.nickname = nickname;
//    }
//
//    public boolean isShow () {
//        return isShow;
//    }
//
//    public void setShow ( boolean show ) {
//        isShow = show;
//    }
//
//    public Long getIns () {
//        return ins;
//    }
//
//    public void setIns ( Long ins ) {
//        this.ins = ins;
//    }
//
//    public int getCount () {
//        return count;
//    }
//
//    public void setCount ( int count ) {
//        this.count = count;
//    }


    @Override
    public String toString () {
        return "CmlUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                ", age=" + age +
                ", demo='" + demo + '\'' +
                '}';
    }

    public int getId () {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getName () {
        return name == null ? "" : name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getUrl () {
        return url == null ? "" : url;
    }

    public void setUrl ( String url ) {
        this.url = url;
    }

    public String getPic () {
        return pic == null ? "" : pic;
    }

    public void setPic ( String pic ) {
        this.pic = pic;
    }


}
